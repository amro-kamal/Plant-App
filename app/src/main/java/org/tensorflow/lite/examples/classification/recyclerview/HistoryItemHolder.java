package org.tensorflow.lite.examples.classification.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

public class HistoryItemHolder extends RecyclerView.ViewHolder{

    private TextView titleTxt ,dateTxt;
    private ImageView img;

    public HistoryItemHolder(@NonNull View itemView) {
        super(itemView);
        titleTxt = itemView.findViewById(R.id.historyItemTitle);
        dateTxt = itemView.findViewById(R.id.historyItemDate);
        img = itemView.findViewById(R.id.historyItemImg);
    }

    public void bindItem(HistoryItem h){
        titleTxt.setText(h.getTitle());
        dateTxt.setText(h.getDate());
        img.setImageBitmap(h.getPlantImg());
    }

}
