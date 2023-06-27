package com.example.ap2_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ap2_4.api.API;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    String profilePic;
    ImageView imageInput;

    private String encodeImage(Bitmap bm) {
        return "data:image/jpeg;base64," + Converters.fromBitmap(bm);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.submit).setOnClickListener(this::register);

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {

                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(uri);

                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageInput.setImageBitmap(selectedImage);
                        profilePic = encodeImage(selectedImage);
                        Log.d("try bitch","Bitmap Encoded?!");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri +"\ndata\n" + profilePic );
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });
        imageInput = (ImageView) findViewById(R.id.image_input_id);
        imageInput.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

    }

    private void register(View v) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirm_password)).getText().toString();
        String displayName = ((EditText) findViewById(R.id.display_name)).getText().toString();
        String profilePic = this.profilePic;
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (profilePic == null) {
            Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }
        API.getInstance().register(
                username, password, displayName, profilePic,
                new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()) {
                            if (response.code() == 409) {
                                Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String token = response.body();
                        Intent intent = new Intent(RegisterActivity.this, ChatsActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}