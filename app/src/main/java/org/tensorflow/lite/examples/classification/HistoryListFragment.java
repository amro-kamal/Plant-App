package org.tensorflow.lite.examples.classification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.classification.recyclerview.HistoryItemsAdaptor;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryListFragment extends Fragment {

    RecyclerView historyRecycleView;
    List<HistoryItem> historyItems ;
    private HistoryItemsAdaptor hAdaptor;

    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_list_fragment, container, false);
        historyRecycleView = v.findViewById(R.id.historyRecycleView);
        TextView txtView = v.findViewById(R.id.hTxt);
        historyRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        addFakeItems();
        updateUI();
        if(historyItems.size()==0){
            historyRecycleView.setVisibility(View.INVISIBLE);
            txtView.setVisibility(View.INVISIBLE);
        }else{
            historyRecycleView.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.VISIBLE);
        }


        return v;
    }


    private void updateUI() {
        if (hAdaptor == null) {
            hAdaptor = new HistoryItemsAdaptor(getActivity() ,historyItems );
            historyRecycleView.setAdapter(hAdaptor);
        } else {
            hAdaptor.setHistoryItems(historyItems);
            hAdaptor.notifyDataSetChanged();
        }
    }
    private void fetchHistory(){
        final String url = "http://localhost:4000/api/history";
        historyItems = new ArrayList<>();

    }
    private void addFakeItems(){
        historyItems = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.lion);
        for(int i = 0; i<20;i++){


            HistoryItem item = new HistoryItem("Leaf spot" , "Mon 11/8/20 8:48PM" ,bitmap);
            historyItems.add(item);
        }
    }


}
