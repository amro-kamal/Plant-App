package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import org.tensorflow.lite.examples.classification.utils.MyPreferences;
import org.tensorflow.lite.examples.classification.utils.VolleyMultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class ClassifyImageActivity extends AppCompatActivity {
    private static Button classfyBtn;
    private static ImageView image;
    private static ProgBar progBar;
    private static TextView progTV;

    private Bitmap rgbFrameBitmap = null;
    private Uri uriToSend= null;

    private static final Logger LOGGER = new Logger();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.classify_image_activity);
    public  void Set_layout(Context context){

        image= findViewById(R.id.image_container);
        classfyBtn=findViewById(R.id.classifyBtn);
        progBar = findViewById(R.id.progBar);
        progTV=findViewById(R.id.prog_tv);
        String txt= ModelSingleton.getIsOnline()?"Getting The Result...":"Downloading The Model...";
        progTV.setText(txt);

        Bitmap bitmap = null;
        Bundle extras = getIntent().getExtras();
        uriToSend = Uri.parse(extras.getString("imageUri"));
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
         //bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.lion);
        image.setImageBitmap(bitmap);

        //set rgbFrameBitmap from Intent
        rgbFrameBitmap = bitmap;

        classfyBtn.setOnClickListener(v -> {
            //send the selected image to classifierActivity
            classifyImage(rgbFrameBitmap);

        });

    }


    public abstract void classifyImage(final Bitmap bitmap);

    protected static void startProgAnim(){
        Log.d("kkk", "startProgAnim");
        classfyBtn.setVisibility(View.INVISIBLE);
        progBar.setVisibility(View.VISIBLE);
        progTV.setVisibility(View.VISIBLE);

    }
    protected static void stopProgAnim(){
        Log.d("kkk", "stopProgAnim");
        classfyBtn.setVisibility(View.VISIBLE);
        progBar.setVisibility(View.INVISIBLE);
        progTV.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("kkk", "onResume");
        stopProgAnim();
    }


}

