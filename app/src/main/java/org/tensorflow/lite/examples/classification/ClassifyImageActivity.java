package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aseem.versatileprogressbar.ProgBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.tflite.Classifier;
import org.tensorflow.lite.examples.classification.utils.DataPart;
import org.tensorflow.lite.examples.classification.utils.Disease;
import org.tensorflow.lite.examples.classification.utils.VolleyMultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClassifyImageActivity extends AppCompatActivity {
    private Button classfyBtn;
    private ImageView image;
    private ProgBar progBar;
    private String TAG = "volleyyyyyyyImageClassifier";


    private Bitmap rgbFrameBitmap = null;
    private Uri uriToSend= null;

    private static final Logger LOGGER = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classify_image_activity);
//        setHandler();

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


    private void classifyImage(final Bitmap bitmap){
        final String url = NetworkingLab.END_POINT + "classify";
        String  REQUEST_TAG = "com.resultactivity.volleyStringRequest.classifyImage";
        classfyBtn.setVisibility(View.INVISIBLE);
        progBar.setVisibility(View.VISIBLE);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject res = new JSONObject(resultResponse);

                    JSONObject resultObj = res.getJSONObject("result");
                    String diseaseId= resultObj.getString("diseaseId");
                    String confidence =resultObj.getString("confidence");

                    String imagePath = res.getString("imagePath");
                    String relPath = res.getString("relativePath");
                    Log.d(TAG , "result is "+res);

                    // go to Result Activity
                    Intent in = new Intent(getBaseContext() , ResultActivity.class);
                    //in.putExtra("leafImg" ,imagePath);
                    //TODO: in futre use imagePath for leafImg
                    in.putExtra("leafImg" ,relPath);
                    in.putExtra("diseaseId" , diseaseId);
                    in.putExtra("confidence" , confidence);
                    startActivity(in);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG , "error: "+error);
                classfyBtn.setVisibility(View.VISIBLE);
                progBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext() , "Connection failed! Try Again." , Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
               // params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                //TODO: change to name "plant-name_leaf"
                params.put("imageData", new DataPart("leaf_image.jpg", getFileDataFromDrawable(bitmap), "image/jpeg"));

                return params;
            }
        };




        NetworkingLab.getInstance(getApplicationContext()).addToRequestQueue(volleyMultipartRequest,REQUEST_TAG);

    }

    @Override
    protected void onResume() {
        super.onResume();
        classfyBtn.setVisibility(View.VISIBLE);
        progBar.setVisibility(View.INVISIBLE);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }







}

