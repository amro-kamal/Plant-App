package org.tensorflow.lite.examples.classification.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

public class HistoryItemHolder extends RecyclerView.ViewHolder{

    private TextView titleTxt ,dateTxt;
    private ImageView img;
    private Context context;
    private final String VOLLEY_TAG = "volleyyyyyy";

    public HistoryItemHolder(@NonNull View itemView , Context c) {
        super(itemView);
        titleTxt = itemView.findViewById(R.id.historyItemTitle);
        dateTxt = itemView.findViewById(R.id.historyItemDate);
        img = itemView.findViewById(R.id.historyItemImg);
        context = c;
    }

    public void bindItem(HistoryItem h){
        titleTxt.setText(h.getTitle());
        dateTxt.setText(h.getDate());
        //img.setImageBitmap(h.getPlantImg());
        String tempUrl = "http://192.168.43.16:4000/uploads/1580391218673black_rot.jpg";
        //TODO:: change images url when server goes online
        Glide.with(context)  //2
                //.load(h.getPlantImgUrl()) //3
                .load(tempUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(img); //8
    }


}
