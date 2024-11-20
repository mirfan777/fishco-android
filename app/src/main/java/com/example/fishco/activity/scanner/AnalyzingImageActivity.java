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

import com.example.fishco.R;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Map;

public class AnalyzingImageActivity extends AppCompatActivity {

    private Interpreter fishDetectionTflite;
    private Interpreter speciesTflite;
    private List<String> speciesLabels;
    private ImageView imageView;
    private TextView loadingTextView;

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

        String imagePath = getIntent().getStringExtra("IMAGE_PATH");

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

        loadModels(imagePath);
    }

    private void loadModels(String imagePath) {
        try {
            MappedByteBuffer detectionModelBuffer = FileUtil.loadMappedFile(this, "machine/model.tflite");
            fishDetectionTflite = new Interpreter(detectionModelBuffer);

            // Load fish species classification model
            MappedByteBuffer speciesModelBuffer = FileUtil.loadMappedFile(this, "machine/model.tflite");
            speciesTflite = new Interpreter(speciesModelBuffer);

            // Load species labels
            speciesLabels = FileUtil.loadLabels(this, "machine/labels.txt");

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
            // Image processor for resizing and normalization
            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                    .add(new NormalizeOp(0, 255))
                    .build();

            // Load and process image
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            TensorImage tensorImage = new TensorImage(fishDetectionTflite.getInputTensor(0).dataType());
            tensorImage.load(bitmap);
            tensorImage = imageProcessor.process(tensorImage);

            // Prepare output for binary classification
            float[][] outputProbabilityBuffer = new float[1][2]; // 0: not fish, 1: fish
            fishDetectionTflite.run(tensorImage.getBuffer(), outputProbabilityBuffer);

            // Check fish probability
            return outputProbabilityBuffer[0][1] > 0.7; // 70% confidence threshold
        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Fish detection error", e);
            return false;
        }
    }

    private String classifyFishSpecies(String imagePath) {
        try {
            // Preprocess the image
            ImageProcessor imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                    .add(new NormalizeOp(0, 255))
                    .build();

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            TensorImage tensorImage = new TensorImage(speciesTflite.getInputTensor(0).dataType());
            tensorImage.load(bitmap);
            tensorImage = imageProcessor.process(tensorImage);

            // Prepare the output buffer
            float[][] outputProbabilityBuffer = new float[1][speciesLabels.size()];
            speciesTflite.run(tensorImage.getBuffer(), outputProbabilityBuffer);

            // Convert to TensorBuffer
            TensorBuffer outputTensorBuffer = TensorBuffer.createFixedSize(
                    speciesTflite.getOutputTensor(0).shape(),
                    speciesTflite.getOutputTensor(0).dataType());
            outputTensorBuffer.loadArray(outputProbabilityBuffer[0]);

            // Create TensorLabel from TensorBuffer
            TensorLabel probabilities = new TensorLabel(speciesLabels, outputTensorBuffer);
            Map<String, Float> labeledProbability = probabilities.getMapWithFloatValue();

            // Find highest probability label
            String highestProbabilityLabel = "";
            float highestProbability = -1f;
            for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
                if (entry.getValue() > highestProbability) {
                    highestProbability = entry.getValue();
                    highestProbabilityLabel = entry.getKey();
                }
            }

            return highestProbabilityLabel;

        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Fish species classification error", e);
            navigateToFailedScan("Classification failed");
            return null;
        }
    }


    private void navigateToFailedScan(String reason) {
        Intent intent = new Intent(this, FailedScanActivity.class);
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