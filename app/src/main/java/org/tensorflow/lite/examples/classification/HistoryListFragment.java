package org.tensorflow.lite.examples.classification;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.recyclerview.HistoryItemsAdaptor;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

import java.util.ArrayList;
import java.util.List;

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

        updateUI();
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
        if (hAdaptor == null) {
            hAdaptor = new HistoryItemsAdaptor(getActivity() ,historyItems );
            historyRecycleView.setAdapter(hAdaptor);
        } else {
            hAdaptor.notifyDataSetChanged();
        }
    }
    private void fetchHistory(){
        String  REQUEST_TAG = "com.plantsapp.volleyJsonRequest.fetchhistory";

        final String url = NetworkingLab.END_POINT + "history";
        historyItems = new ArrayList<>();
        //Do something while loading

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray resArray) {
                        Log.d(VOLLEY_TAG, resArray.toString());
                        try {
                            for(int i= 0; i<resArray.length() ;i++ ){
                                JSONObject itemJsonObj = resArray.getJSONObject(i);
                                HistoryItem item = new HistoryItem();
                                item.setTitle(itemJsonObj.getString("title"));
                                item.setDate(itemJsonObj.getString("date"));
                                item.setDiseaseId(itemJsonObj.getString("disease_id"));
                                JSONObject picObj = itemJsonObj.getJSONObject("pic");
                                item.setPlantImgUrl(picObj.getString("imageData"));

                                historyItems.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(VOLLEY_TAG , "list size is "+ historyItems.size());
                        updateUI();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(VOLLEY_TAG, "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        NetworkingLab.getInstance(getActivity()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }





}
