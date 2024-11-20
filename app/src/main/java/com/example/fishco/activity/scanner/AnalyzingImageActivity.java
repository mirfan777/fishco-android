package com.example.fishco.activity.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;

import com.example.fishco.R;
import com.example.fishco.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.ByteOrder;

public class AnalyzingImageActivity extends AppCompatActivity {

    private int imageSize = 224; // Expected input size for the model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzing_image);

        ImageView scanImage = findViewById(R.id.image);

        // Retrieve the byte array from the Intent and convert it back to a Bitmap
        byte[] imageBytes = getIntent().getByteArrayExtra("IMAGE_BITMAP");

        if (imageBytes != null) {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            scanImage.setImageBitmap(imageBitmap);
            processImage(imageBitmap);
        } else {
            navigateToFailedScan("No image provided");
        }
    }

    private void processImage(Bitmap image) {
        try {
            String classifiedSpecies = classifyFishSpecies(image);

            // Pass the classification and image to the next activity
            Intent intent = new Intent(this, DetailScannerActivity.class);
            intent.putExtra("FISH_SPECIES", classifiedSpecies);

            // Serialize and pass the Bitmap
            intent.putExtra("IMAGE_BITMAP", bitmapToByteArray(image));
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Processing error", e);
            navigateToFailedScan("Processing failed");
        }
    }


    private String classifyFishSpecies(Bitmap image) throws IOException {
        Model model = Model.newInstance(getApplicationContext());

        // Preprocess the Bitmap to match the model's input requirements
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, true);

        // Allocate the correct buffer size (1 * 224 * 224 * 3 * 4 bytes)
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(imageSize * imageSize * 3 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[imageSize * imageSize];
        scaledImage.getPixels(intValues, 0, scaledImage.getWidth(), 0, 0, scaledImage.getWidth(), scaledImage.getHeight());

        for (int i = 0; i < imageSize; i++) {
            for (int j = 0; j < imageSize; j++) {
                int pixelValue = intValues[i * imageSize + j];
                // Normalize pixel values to [0, 1] range
                byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) / 255.0f);   // Red
                byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) / 255.0f);    // Green
                byteBuffer.putFloat((pixelValue & 0xFF) / 255.0f);           // Blue
            }
        }

        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);
        inputFeature0.loadBuffer(byteBuffer);

        // Perform inference
        Model.Outputs outputs = model.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

        // Get the classification result
        float[] confidences = outputFeature0.getFloatArray();
        String[] classes = {"betta", "carassius"}; // Update with your model's classes

        int maxPos = 0;
        float maxConfidence = 0;
        for (int i = 0; i < confidences.length; i++) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i];
                maxPos = i;
            }
        }

        // Release model resources
        model.close();

        return classes[maxPos];
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    private void navigateToFailedScan(String reason) {
        Intent intent = new Intent(this, NoFishDetectedActivity.class);
        intent.putExtra("FAILURE_REASON", reason);
        startActivity(intent);
        finish();
    }
}
