package org.tensorflow.lite.examples.classification.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.tensorflow.lite.examples.classification.NetworkingLab;
import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.ResultActivity;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;

import java.io.File;

public class HistoryItemHolder extends RecyclerView.ViewHolder{

    private TextView titleTxt ,dateTxt;
    private ImageView img;
    private Button btn;
    private Context context;
    private String diseaseId;
    private String confidence;
    private final String VOLLEY_TAG = "volleyyyyyy";
    private String imgUrl;

    public HistoryItemHolder(@NonNull View itemView , Context c) {
        super(itemView);
        context = c;
        titleTxt = itemView.findViewById(R.id.historyItemTitle);
        dateTxt = itemView.findViewById(R.id.historyItemDate);
        img = itemView.findViewById(R.id.historyItemImg);
        btn = itemView.findViewById(R.id.historyItemBtn);
        btn.setOnClickListener(v -> {

            Intent in = new Intent(context , ResultActivity.class);
            in.putExtra("diseaseId",diseaseId );
            in.putExtra("confidence",confidence );
            //send Image
            in.putExtra("leafImg" , imgUrl );
            context.startActivity(in);
        });

    }

    public void bindItem(HistoryItem h){
        diseaseId = h.getDiseaseId();
        confidence = h.getConfidence();
        titleTxt.setText(h.getTitle());
        dateTxt.setText(h.getDate());

        //TODO: change to Path reutrned from server and remove TEMP_URL
        //imgUrl = h.getPlantImgUrl();
        imgUrl = "uploads/"+h.getImgName();

//        Glide.with(context)  //2
//                .load(NetworkingLab.TEMP_URL+imgUrl) //3
//                .centerCrop() //4
//                .placeholder(R.drawable.placeholder) //5
//                .error(R.drawable.broken_image) //6
//                .fallback(R.drawable.broken_image) //7
//                .into(img); //8

        img.setImageBitmap(h.getHistoryImage());

    }



}
