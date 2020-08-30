package org.tensorflow.lite.examples.classification;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.recyclerview.HistoryItemsAdaptor;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;
import org.tensorflow.lite.examples.classification.utils.MyPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryListFragment extends Fragment {

    RecyclerView historyRecycleView;
    List<HistoryItem> historyItems ;
    private HistoryItemsAdaptor hAdaptor;
    private final String VOLLEY_TAG = "volleyyyyyy";

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        fetchHistory();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_list_fragment, container, false);
        historyRecycleView = v.findViewById(R.id.historyRecycleView);
        TextView txtView = v.findViewById(R.id.hTxt);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

//        updateUI();
        /*if(historyItems.size()==0){
            historyRecycleView.setVisibility(View.INVISIBLE);
            txtView.setVisibility(View.INVISIBLE);
        }else{
            historyRecycleView.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.VISIBLE);
        }*/


        return v;
    }




    private void updateUI() {
        Log.d("kkk","update history UI  ...");

        if (hAdaptor == null) {
            Log.d("kkk","update history UI ,historyAdapter == null ,historyItems= "+historyItems);
            hAdaptor = new HistoryItemsAdaptor(getActivity() ,historyItems );
            historyRecycleView.setAdapter(hAdaptor);
        } else {
            hAdaptor.notifyDataSetChanged();
        }
    }

    private void fetchHistory(){
        Log.d("kkk","fetch history  ...");

        String  REQUEST_TAG = "com.plantsapp.volleyJsonRequest.fetchhistory";

        final String url = NetworkingLab.END_POINT + "history";

        
        historyItems = new ArrayList<>();
        //Do something while loading

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", MyPreferences.USER_EMAIL);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Log.d("kkk","fetch history ,onresponse ");

                            JSONArray resArray= response.getJSONArray("history");
                            Log.d("kkk","history array :" +resArray.toString());

                            for(int i= 0; i<resArray.length() ;i++ ){
                                JSONObject itemJsonObj = resArray.getJSONObject(i);
                                HistoryItem item = new HistoryItem();
                                item.setTitle(itemJsonObj.getString("title"));
                                item.setDate(itemJsonObj.getString("date"));
                                item.setConfidence(itemJsonObj.getString("confidence"));
                                item.setDiseaseId(itemJsonObj.getString("disease_id"));
                                JSONObject picObj = itemJsonObj.getJSONObject("pic");
                                byte[] decodedBytes = Base64.decode(picObj.getString("img"), Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes , 0, decodedBytes.length);
                                item.setHisoryImage(bitmap);
//                                item.setPlantImgUrl(picObj.getString("imageData"));
//                                item.setImgName(picObj.getString("imageName"));

                                historyItems.add(item);
                            }
                            Log.d("kkk" , "history list size is "+ historyItems.size());
                            updateUI();
                        } catch (JSONException e) {
//                            e.printStackTrace();
                            Log.d("kkk", "history catch: " + e);

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kkk", "fetch history onError: " + error.getMessage());
            }
        });
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=utf-8");
////                params.put("user_email", MyPreferences.USER_EMAIL);
//                return params;
//            }
//        };
        // Adding JsonObject request to request queue
        NetworkingLab.getInstance(getActivity()).addToRequestQueue(jsonObjReq, REQUEST_TAG);
    }



//    private void fetchHistory(){
//        String  REQUEST_TAG = "com.plantsapp.volleyJsonRequest.fetchhistory";
//
//        final String url = NetworkingLab.END_POINT + "history";
//        historyItems = new ArrayList<>();
//        //Do something while loading
//
//        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray resArray) {
//                        Log.d(VOLLEY_TAG, resArray.toString());
//                        try {
//                            for(int i= 0; i<resArray.length() ;i++ ){
//                                JSONObject itemJsonObj = resArray.getJSONObject(i);
//                                HistoryItem item = new HistoryItem();
//                                item.setTitle(itemJsonObj.getString("title"));
//                                item.setDate(itemJsonObj.getString("date"));
//                                item.setConfidence(itemJsonObj.getString("confidence"));
//                                item.setDiseaseId(itemJsonObj.getString("diseaseId"));
//                                JSONObject picObj = itemJsonObj.getJSONObject("pic");
//                                item.setPlantImgUrl(picObj.getString("imageData"));
//                                item.setImgName(picObj.getString("imageName"));
//
//                                historyItems.add(item);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d(VOLLEY_TAG , "list size is "+ historyItems.size());
//                        updateUI();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(VOLLEY_TAG, "Error: " + error.getMessage());
//            }
//        });
//
//        // Adding JsonObject request to request queue
//        NetworkingLab.getInstance(getActivity()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
//    }





}
