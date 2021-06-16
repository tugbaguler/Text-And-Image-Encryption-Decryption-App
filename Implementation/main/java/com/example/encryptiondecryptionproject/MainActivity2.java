package com.example.encryptiondecryptionproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity2 extends AppCompatActivity {
    ConstraintLayout imageEncrypt, textEncrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageEncrypt = findViewById(R.id.img_encrypt);
        textEncrypt = findViewById(R.id.text_encrypt);

        textEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent password_based_encryption = new Intent(MainActivity2.this, PasswordBasedEncryptionDecryptionActivity.class);
                startActivity(password_based_encryption);
            }
        });

        imageEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image_based_encryption = new Intent(MainActivity2.this, ImageBasedEncryptionDecryptionActivity.class);
                startActivity(image_based_encryption);
            }
        });

    }
}