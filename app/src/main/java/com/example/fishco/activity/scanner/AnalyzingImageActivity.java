package com.example.fishco.activity.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.fishco.R;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzingImageActivity extends AppCompatActivity {

    private Interpreter fishDetectionTflite;
    private Interpreter speciesTflite;
    private List<String> speciesLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analyzing_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView scanImage = findViewById(R.id.image);
        String imagePath = getIntent().getStringExtra("IMAGE_PATH");

        if (imagePath != null) {
            Glide.with(this)
                    .load(imagePath)
                    .into(scanImage);
        }

        loadModels(imagePath);
    }

    private void loadModels(String imagePath) {
        try {
            MappedByteBuffer detectionModelBuffer =  FileUtil.loadMappedFile(this, "models/model.tflite");
            fishDetectionTflite = new Interpreter(detectionModelBuffer);

            // Load fish species classification model
            MappedByteBuffer speciesModelBuffer = FileUtil.loadMappedFile(this, "models/model.tflite");
            speciesTflite = new Interpreter(speciesModelBuffer);

            // Load species labels
            speciesLabels = FileUtil.loadLabels(this, "models/labels.txt");

            // Process image
            processImage(imagePath);

        } catch (IOException e) {
            Log.e("AnalyzingImageActivity", "Error loading models", e);
            navigateToFailedScan("Model loading failed");
        }
    }

    private void processImage(String imagePath) {
        try {
            // Check if it's a fish
            if (!isFish(imagePath)) {
                navigateToFailedScan("No fish detected");
                return;
            }

            // Classify fish species
            String classifiedSpecies = classifyFishSpecies(imagePath);

            // Navigate to detail activity
            Intent intent = new Intent(this, DetailScannerActivity.class);
            intent.putExtra("FISH_SPECIES", classifiedSpecies);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Processing error", e);
            navigateToFailedScan("Processing failed");
        }
    }

    private boolean isFish(String imagePath) {
        try {
            // Load and scale the bitmap
            Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);

            // Calculate the correct input size based on the model's expected bytes
            // 150528 bytes = height * width * channels (assuming float32 and RGB)
            // 150528 / (3 * 4) = 12544 (total pixels)
            // sqrt(12544) ≈ 112, so the model expects 112x112 input
            int modelInputSize = 112;  // Changed from 224 to match model's expectations

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(
                    originalBitmap,
                    modelInputSize,
                    modelInputSize,
                    true
            );

            // Create TensorImage with the correct data type
            TensorImage tensorImage = new TensorImage(fishDetectionTflite.getInputTensor(0).dataType());
            tensorImage.load(scaledBitmap);

            // Create ImageProcessor for preprocessing
            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(modelInputSize, modelInputSize, ResizeOp.ResizeMethod.BILINEAR))
                    .add(new NormalizeOp(0.0f, 255.0f))
                    .build();

            tensorImage = imageProcessor.process(tensorImage);

            // Add debug logging
            logTensorImageInfo("FishDetection", tensorImage);
            logModelInfo("FishDetection", fishDetectionTflite);

            // Prepare output buffer
            float[][] outputProbabilityBuffer = new float[1][2]; // [not fish, fish]

            // Run inference
            Map<Integer, Object> outputs = new HashMap<>();
            outputs.put(0, outputProbabilityBuffer);

            Object[] inputs = new Object[]{tensorImage.getBuffer()};
            Map<Integer, Object> outputMap = new HashMap<>();
            outputMap.put(0, outputProbabilityBuffer);

            fishDetectionTflite.runForMultipleInputsOutputs(inputs, outputMap);

            // Clean up
            scaledBitmap.recycle();

            // Check fish probability (threshold at 70%)
            return outputProbabilityBuffer[0][1] > 0.7f;

        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Fish detection error", e);
            e.printStackTrace();
            navigateToFailedScan("No Fish Detected");
            return false;
        }
    }

    private String classifyFishSpecies(String imagePath) {
        try {
            // Load and scale the bitmap
            Bitmap originalBitmap = BitmapFactory.decodeFile(imagePath);
            int modelInputSize = 224;
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(
                    originalBitmap,
                    modelInputSize,
                    modelInputSize,
                    true
            );

            // Create and process TensorImage
            TensorImage tensorImage = new TensorImage(speciesTflite.getInputTensor(0).dataType());
            tensorImage.load(scaledBitmap);

            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new NormalizeOp(0.0f, 255.0f))
                    .build();

            tensorImage = imageProcessor.process(tensorImage);

            // Prepare output buffer
            float[][] outputProbabilityBuffer = new float[1][speciesLabels.size()];

            // Run inference directly with TensorBuffer
            speciesTflite.run(tensorImage.getBuffer(), outputProbabilityBuffer);

            // Clean up
            scaledBitmap.recycle();

            // Find highest probability label
            String highestProbabilityLabel = "";
            float highestProbability = -1f;

            for (int i = 0; i < speciesLabels.size(); i++) {
                if (outputProbabilityBuffer[0][i] > highestProbability) {
                    highestProbability = outputProbabilityBuffer[0][i];
                    highestProbabilityLabel = speciesLabels.get(i);
                }
            }

            return highestProbabilityLabel;

        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Fish species classification error", e);
            e.printStackTrace();
            navigateToFailedScan("Classification failed");
            return null;
        }
    }

    // Add these helper methods for debugging
    private void logModelInfo(String tag, Interpreter interpreter) {
        try {
            Tensor inputTensor = interpreter.getInputTensor(0);
            Log.d(tag, "Input tensor shape: " + Arrays.toString(inputTensor.shape()));
            Log.d(tag, "Input tensor dataType: " + inputTensor.dataType());
            Log.d(tag, "Input tensor bytes: " + inputTensor.numBytes());

            Tensor outputTensor = interpreter.getOutputTensor(0);
            Log.d(tag, "Output tensor shape: " + Arrays.toString(outputTensor.shape()));
            Log.d(tag, "Output tensor dataType: " + outputTensor.dataType());
            Log.d(tag, "Output tensor bytes: " + outputTensor.numBytes());
        } catch (Exception e) {
            Log.e(tag, "Error logging model info", e);
        }
    }

    private void logTensorImageInfo(String tag, TensorImage tensorImage) {
        try {
            Log.d(tag, "TensorImage shape: " + Arrays.toString(tensorImage.getTensorBuffer().getShape()));
            Log.d(tag, "TensorImage dataType: " + tensorImage.getTensorBuffer().getDataType());
            Log.d(tag, "TensorImage bytes: " + tensorImage.getBuffer().capacity());
        } catch (Exception e) {
            Log.e(tag, "Error logging tensor image info", e);
        }
    }


    private void navigateToFailedScan(String reason) {
        Intent intent = new Intent(this, NoFishDetectedActivity.class);
        intent.putExtra("FAILURE_REASON", reason);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fishDetectionTflite != null) {
            fishDetectionTflite.close();
        }
        if (speciesTflite != null) {
            speciesTflite.close();
        }
    }
}