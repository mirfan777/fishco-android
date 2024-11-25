package com.example.fishco.activity.started;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;  // Import AppCompatActivity
import com.example.fishco.R;

public class GetStartedDetailActivity extends AppCompatActivity {  // Extend AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_detail);

        findViewById(R.id.back_button).setOnClickListener(v -> finish());
        // Use the correct layout
    }
}
