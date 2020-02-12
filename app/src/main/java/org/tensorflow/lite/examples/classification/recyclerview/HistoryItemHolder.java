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
    private final String VOLLEY_TAG = "volleyyyyyy";
    private String tempUrl = NetworkingLab.TEMP_URL+"uploads/1580391218673black_rot.jpg";

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
            //send Image
            File f = new File(tempUrl);
            Uri uriToSend = Uri.fromFile(f);
            in.putExtra("leafImg" ,uriToSend.toString() );
            context.startActivity(in);
        });

    }

    public void bindItem(HistoryItem h){
        diseaseId = h.getDiseaseId();
        titleTxt.setText(h.getTitle());
        dateTxt.setText(h.getDate());

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
