package com.example.encryptiondecryptionproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SystemClock.sleep(3000);
        Intent choose_password_or_image_encryption = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(choose_password_or_image_encryption);
        finish();
    }
}