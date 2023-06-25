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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    String profilePic;

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();

        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        //data:[<media type>][;charset=<character set>][;base64],<data>
        String formatedImage = "data:image/jpeg;base64,"+encImage;

        return formatedImage;
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
        Button clickButton = (Button) findViewById(R.id.image_button_id);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
    }

    private void register(View v) {
        try {
            URL url = new URL("http://localhost:5000/Users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            String jsonInputString = String.format(
                    "{" +
                            "\"username\": \"%s\"," +
                            "\"password\": \"%s\"," +
                            "\"profilePic\": \"%s\"," +
                            "\"displayName\": \"%s\"" +
                            "}", ((EditText) findViewById(R.id.username)).getText(),
                    ((EditText) findViewById(R.id.password)).getText(),
                    profilePic,
                    ((EditText) findViewById(R.id.display_name)).getText());

            conn.getOutputStream().write(jsonInputString.getBytes());
            if (conn.getResponseCode() == 200) {
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            } else if (conn.getResponseCode() == 409) {
                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error: " + conn.getResponseCode(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}