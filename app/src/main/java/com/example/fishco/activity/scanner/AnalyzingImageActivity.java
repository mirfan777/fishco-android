package com.example.fishco.activity.scanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageProxy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fishco.R;
import com.example.fishco.activity.encyclopedia.FishDetailActivity;
import com.example.fishco.activity.encyclopedia.FishListActivity;
import com.example.fishco.adapter.FishCustomAdapter;
import com.example.fishco.http.RetrofitClient;
import com.example.fishco.ml.Model;
import com.example.fishco.model.Fish;
import com.example.fishco.service.AuthService;
import com.example.fishco.service.FishService;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyzingImageActivity extends AppCompatActivity {

    private int imageSize = 224;
    SharedPreferences sharedPreferences;
    FishService fishService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzing_image);

        ImageView scanImage = findViewById(R.id.image);

        byte[] imageBytes = getIntent().getByteArrayExtra("IMAGE_BITMAP");

        if (imageBytes != null) {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            scanImage.setImageBitmap(imageBitmap);
            processImage(imageBitmap);
        } else {
            navigateToFailedScan("No image provided");
        }
    }

    private void fetchFish(String token,String genus,Bitmap image) {
        fishService = RetrofitClient.getClient(this).create(FishService.class);

        Call<List<Fish>> callFish = fishService.getFish(token, genus);

        callFish.enqueue(new Callback<List<Fish>>() {
            @Override
            public void onResponse(Call<List<Fish>> call, Response<List<Fish>> response) {
                Log.d("yasin" , response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    List<Fish> fish = response.body();
                    Fish classifiedfish = fish.get(0);

                    Intent intent = new Intent(AnalyzingImageActivity.this, FishDetailActivity.class);
                    intent.putExtra("FISH_CLASSIFIED", genus);
                    intent.putExtra("IMAGE_BITMAP", bitmapToByteArray(image));
                    intent.putExtra("ID", classifiedfish.getId());
                    intent.putExtra("NAME", classifiedfish.getName());
                    intent.putExtra("KINGDOM", classifiedfish.getKingdom());
                    intent.putExtra("PHYLUM", classifiedfish.getPhylum());
                    intent.putExtra("CLASS", classifiedfish.getClass());
                    intent.putExtra("ORDER", classifiedfish.getOrder());
                    intent.putExtra("FAMILY", classifiedfish.getFamily());
                    intent.putExtra("GENUS", classifiedfish.getGenus());
                    intent.putExtra("SPECIES", classifiedfish.getSpecies());
                    intent.putExtra("COLOUR", classifiedfish.getColour());
                    intent.putExtra("FOOD_TYPE", classifiedfish.getFoodType());
                    intent.putExtra("FOOD", classifiedfish.getFood());
                    intent.putExtra("MIN_TEMPERATURE", classifiedfish.getMinTemperature());
                    intent.putExtra("MAX_TEMPERATURE", classifiedfish.getMaxTemperature());
                    intent.putExtra("MIN_PH", classifiedfish.getMinPh());
                    intent.putExtra("MAX_PH", classifiedfish.getMaxPh());
                    intent.putExtra("HABITAT", classifiedfish.getHabitat());
                    intent.putExtra("OVERVIEW", classifiedfish.getOverview());
                    intent.putExtra("THUMBNAIL", classifiedfish.getThumbnail());
                    intent.putExtra("THUMBNAIL_URL", classifiedfish.getUrlThumbnail());
                    intent.putExtra("AVERAGE_SIZE", classifiedfish.getAverageSize());
                    intent.putExtra("CREATED_AT", classifiedfish.getCreatedAt());
                    intent.putExtra("UPDATED_AT", classifiedfish.getUpdatedAt());
                    startActivity(intent);
                    finish();

                }else{
                    navigateToFailedScan("GAGAL MANGGIL RESPONSE");
                }
            }

            @Override
            public void onFailure(Call<List<Fish>> call, Throwable throwable) {
                navigateToFailedScan("GAGAL MANGGIL RESPONSE");
            }
        });
    }

    private void processImage(Bitmap image) {
        try {
            sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

            String classifiedSpecies = classifyFishSpecies(image);

            Log.d("classified" , classifiedSpecies);

            fetchFish(sharedPreferences.getString("token" , "no token"), classifiedSpecies , image);
        } catch (Exception e) {
            Log.e("AnalyzingImageActivity", "Processing error", e);
            navigateToFailedScan("Processing failed");
        }
    }


    private String classifyFishSpecies(Bitmap image) throws IOException {
        Model model = Model.newInstance(getApplicationContext());

        Bitmap scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, true);

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
        String[] classes = {"betta", "carassius"};

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
