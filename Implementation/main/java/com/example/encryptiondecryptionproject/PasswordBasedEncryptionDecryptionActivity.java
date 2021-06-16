package com.example.encryptiondecryptionproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordBasedEncryptionDecryptionActivity extends AppCompatActivity {

    // initialize variable
    EditText input_the_text, input_the_password;
    TextView output_text;
    Button btn_encrypt, btn_decrypt;
    String output;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_based_encryption_decryption);

        // assign variable
        input_the_text = findViewById(R.id.text_editText_password_based);
        input_the_password = findViewById(R.id.text_editText_password_based);
        output_text = findViewById(R.id.encryption_textView_password_based);
        btn_encrypt = findViewById(R.id.btn_image_main2);
        btn_decrypt = findViewById(R.id.btn_text_main2);

        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    output = encryption(input_the_text.getText().toString(), input_the_password.getText().toString());
                    output_text.setText(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    output = decryption(output, input_the_password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output_text.setText(output);
            }
        });

    }

    private String encryption(String input, String password) throws Exception {
        SecretKeySpec keySpec = generateKeySpec(password);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptValue = cipher.doFinal(input.getBytes());
        String encryptedValue = Base64.encodeToString(encryptValue, Base64.DEFAULT);
        return encryptedValue;
    }

    private String decryption(String output, String password) throws Exception{
        SecretKeySpec keySpec = generateKeySpec(password);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedValue = Base64.decode(output, Base64.DEFAULT);
        byte[] decriptValue = cipher.doFinal(decodedValue);
        String decriptedValue = new String(decriptValue);
        return decriptedValue;
    }

    private SecretKeySpec generateKeySpec(String password) throws Exception{
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] keySpec = messageDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keySpec, "AES");
        return secretKeySpec;
    }
}