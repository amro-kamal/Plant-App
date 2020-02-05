package org.tensorflow.lite.examples.classification.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

import java.util.List;

public class HistoryItemsAdaptor extends RecyclerView.Adapter<HistoryItemHolder> {
    private List<HistoryItem> hItems;
    private Context context;

    public HistoryItemsAdaptor(Context c, List<HistoryItem> items ){
        hItems = items;
        context = c;
    }

    public void setHistoryItems(List<HistoryItem> items){
        hItems = items;
    }


    @NonNull
    @Override
    public HistoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=  LayoutInflater.from(context)
                .inflate(R.layout.histroy_item_layout, parent, false);

        return new HistoryItemHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemHolder holder, int position) {
        HistoryItem item = hItems.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return hItems.size();
    }
}
