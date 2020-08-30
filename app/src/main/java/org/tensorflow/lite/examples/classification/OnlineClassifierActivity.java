package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.utils.DataPart;
import org.tensorflow.lite.examples.classification.utils.MyPreferences;
import org.tensorflow.lite.examples.classification.utils.VolleyMultipartRequest;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class OnlineClassifierActivity extends ClassifyImageActivity {

    private String TAG = "volleyyyyyyyImageClassifier";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_image_activity);

        Set_layout(this);
        Log.d("kkkk", "ONlineclassifier set leyout... done");
    }

    @Override
    public void classifyImage(final Bitmap bitmap){
        Log.d(TAG , "online classifier ,Classify image ");

        final String url = NetworkingLab.END_POINT + "classify";
        String  REQUEST_TAG = "com.resultactivity.volleyStringRequest.classifyImage";
        startProgAnim();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject res = new JSONObject(resultResponse);

                    JSONObject resultObj = res.getJSONObject("result");
                    Log.d("kkk", "onResponse: resultObj"+res);
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
                    Log.d(TAG , "online classifier ,catch error: "+e);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kkk" , "online classifier ,Connection error: "+error);
                stopProgAnim();
                Toast.makeText(getBaseContext() , "online classifier ,Connection failed! Try Again." , Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                String currentModel = ModelSingleton.getInstance(getApplicationContext()).getCurrentModel();
                params.put("model", currentModel);
                String user_email = MyPreferences.USER_EMAIL;
                params.put("user_email", user_email);
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

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}

