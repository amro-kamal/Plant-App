package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aseem.versatileprogressbar.ProgBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.utils.DataPart;
import org.tensorflow.lite.examples.classification.utils.VolleyMultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class ClassifyImageActivity extends AppCompatActivity {
    private Button classfyBtn;
    private ImageView image;
    private ProgBar progBar;

    private Bitmap rgbFrameBitmap = null;
    private Uri uriToSend= null;

    private static final Logger LOGGER = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classify_image_activity);

        image= findViewById(R.id.image_container);
        classfyBtn=findViewById(R.id.classifyBtn);
        progBar = findViewById(R.id.progBar);
        Bitmap bitmap = null;
        Bundle extras = getIntent().getExtras();
        uriToSend = Uri.parse(extras.getString("imageUri"));
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.lion);
        image.setImageBitmap(bitmap);

        //set rgbFrameBitmap from Intent
        rgbFrameBitmap = bitmap;

        classfyBtn.setOnClickListener(v -> {
            //send the selected image to classifierActivity
            classifyImage(rgbFrameBitmap);

        });

    }


    public abstract void classifyImage(final Bitmap bitmap);

    protected void startProgAnim(){
        classfyBtn.setVisibility(View.INVISIBLE);
        progBar.setVisibility(View.VISIBLE);
    }
    protected void stopProgAnim(){
        classfyBtn.setVisibility(View.VISIBLE);
        progBar.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        stopProgAnim();
    }


}

