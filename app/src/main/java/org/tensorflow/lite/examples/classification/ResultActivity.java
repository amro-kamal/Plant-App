package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.utils.Disease;

public class ResultActivity extends AppCompatActivity {
    private Fragment fragment;
    private String TAG = "volleyyyyyyy";
    private String leafImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classification_result_act);

        String diseaseId = getIntent().getStringExtra("diseaseId");
        leafImg = getIntent().getStringExtra("leafImg");
        getDiseaseInfo(diseaseId);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = ResultLoadingFragment.newInstance();
        transaction
                .add(R.id.resultFragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();







    }

    private void replaceFragment(Disease disease){
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        fragment = ResultFragment.newInstance(disease , leafImg);
        transaction2.replace(R.id.resultFragmentContainer, fragment);
        transaction2.commit();
    }

    public void getDiseaseInfo(String id){

        String  REQUEST_TAG = "com.resultactivity.volleyStringRequest.getDisease";
        final String url = NetworkingLab.END_POINT + "disease?id="+id;

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        Log.d(TAG, res.toString());
                        try {
                            Disease d = new Disease();
                            d.setId(res.getString("disease_id"));
                            d.setTitle(res.getString("title"));
                            d.setCategory(res.getString("category"));
                            d.setHosts(res.getString("hosts"));
                            d.setSummary(res.getString("summary"));
                            d.setSymptoms(res.getString("symptoms"));
                            d.setTreatment(res.getString("treatment"));
                            d.setImageUrl(res.getString("imageUrl"));
                            replaceFragment(d );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                //goBack to previous act
            }
        });

        // Adding JsonObject request to request queue
        NetworkingLab.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }


}
