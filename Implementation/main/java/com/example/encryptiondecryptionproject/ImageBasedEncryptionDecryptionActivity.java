package com.example.encryptiondecryptionproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ImageBasedEncryptionDecryptionActivity extends AppCompatActivity {

    // initialize variable
    ImageView image;
    TextView text;
    Button btn_encrypt, btn_decrypt;
    String image_string;

    private static final int SECURE_KEY_LENGTH = 16;
    private static final String IV_STRING = "16-Bytes--String";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_based_encryption_decryption);

        // assign variable
        image = findViewById(R.id.imageView);
        text = findViewById(R.id.textView_image);
        btn_encrypt = findViewById(R.id.btn_encrypt_image);
        btn_decrypt = findViewById(R.id.btn_decrypt_image);

        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ImageBasedEncryptionDecryptionActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    // when permission is not granted, request permission
                    ActivityCompat.requestPermissions(ImageBasedEncryptionDecryptionActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else{
                    // when permission is granted, create method
                    chooseImage();
                }
            }
        });

        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // decode base64 string
                byte[] bytes = Base64.decode(image_string, Base64.DEFAULT);
                // create and intialize bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
            }
        });
    }

    private void chooseImage() {
        // clear previous data
        text.setText("");
        image.setImageBitmap(null);

        Intent image_intent = new Intent(Intent.ACTION_GET_CONTENT);
        image_intent.setType("image/*");
        startActivityForResult(Intent.createChooser(image_intent, "Select Image"), 100);
    }

    public static byte[] getAESKey(String key) throws UnsupportedEncodingException {
        byte[] keyBytes;
        keyBytes = key.getBytes("UTF-8");
        byte[] keyBytes16 = new byte[SECURE_KEY_LENGTH];
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, SECURE_KEY_LENGTH));
        return keyBytes16;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // permission is granted
            chooseImage();
        } else{
            // permission is denied
            Toast.makeText(getApplicationContext(), "Permission is denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null){
            // result is OK
            // create and initialize URI
            Uri uri = data.getData();
            try {
                // create and initialize bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //initialize byte array output stream
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                // compress format
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                //bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);

                // initialize byte array
                byte[] bytes = byteArrayOutputStream.toByteArray();
                //get based 64 encoded string and set encoded text
                image_string = Base64.encodeToString(bytes, Base64.DEFAULT);
                text.setText(image_string);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static byte[] encrypt(String content, String secureKey) {
        if (content == null) {
            return null;
        }
        try {
            // Get key data
            byte[] rawKeyData = getAESKey(secureKey);
            // Create a KeySpec object from the original key data
            SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
            // Cipher object actually completes the encryption operation
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Initialize the Cipher object with a key
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] encryptByte = cipher.doFinal(content.getBytes());
            return  encryptByte;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (NoSuchPaddingException e){
            e.printStackTrace();
        }catch (InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }catch (InvalidKeyException e){
            e.printStackTrace();
        }catch (IllegalBlockSizeException e){
            e.printStackTrace();
        }catch (BadPaddingException e){
            e.printStackTrace();
        }
        return null;

    }
    public static String decrypt(byte[] content, String secureKey) {
        if (content == null) {
            return null;
        }
        try {
            // Get key data
            byte[] rawKeyData = getAESKey(secureKey); // secureKey.getBytes();
            // Create a KeySpec object from the original key data
            SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
            // Cipher object actually completes the decryption operation
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // Initialize the Cipher object with a key
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            return  new String(cipher.doFinal(content),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // AES encrypted data can be converted to Base64 encoding to display
    public static String encode(byte[] data) {
        return  Base64.encodeToString(data,Base64.NO_WRAP);
    }

    // Corresponding to the Base64 decoding before AES decryption
    public static byte[] decode(String content) {
        return Base64.decode(content,Base64.NO_WRAP);
    }

}