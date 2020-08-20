package org.tensorflow.lite.examples.classification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.lite.examples.classification.utils.Disease;

import java.io.IOException;


public class ResultHealthyFragment extends Fragment {

    private ImageView leafImg;

    private TextView diseaseIdentifiedTxt;
    private TextView confidenceTxtView;

    private TextView diseaseTypeTxt;
    private ImageView diseaseImg;
    private ImageView healthyGif;
    private ImageView diseaseImg2;

    private Disease disease;
    private String leafImgUrl;


    private String confidence;

    public ResultHealthyFragment(Disease d ,String img , String conf) {
        disease  = d;
        leafImgUrl = img;
        confidence = conf;
    }

    public static ResultHealthyFragment newInstance(Disease disease , String leafImg , String confidence){
        return new ResultHealthyFragment(disease ,leafImg , confidence);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.classification_result_frag, container ,false);
        leafImg = v.findViewById(R.id.leafImg);
        diseaseImg = v.findViewById(R.id.diseaseImage);
        diseaseImg2 = v.findViewById(R.id.diseaseImage2);
        diseaseIdentifiedTxt = v.findViewById(R.id.diseaseIdentifiedTxt);
        diseaseTypeTxt = v.findViewById(R.id.diseaseTypeTxt);
        confidenceTxtView= v.findViewById(R.id.model_confidence);
        healthyGif= v.findViewById(R.id.healthyImg);




        confidence = confidence.substring(0,6);
        float conf = 100 * Float.parseFloat(confidence);
        String plant = ModelSingleton.getInstance(getActivity()).getCurrentModel();

        diseaseIdentifiedTxt.setText("Disease identified in "+ plant +" Plant.");
        diseaseTypeTxt.setText(disease.getTitle());
        confidenceTxtView.setText("confidence "+ conf+ "%");

        Glide.with(getActivity())  //2
                .load(NetworkingLab.TEMP_URL+leafImgUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(leafImg); //8

        String diseaseImgUrl = NetworkingLab.TEMP_URL+disease.getImageUrl();
        Glide.with(getActivity())  //2
                .load(diseaseImgUrl)//3
                .centerCrop() //4
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(diseaseImg); //8

        Glide.with(getActivity())  //2
                .load(diseaseImgUrl)//3
                .centerCrop() //4
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(diseaseImg2); //8

        Glide.with(getActivity())
                .load("https://wf-live.enniscdn.net/wp-content/uploads/2019/05/20115802/Plant_V1.gif")
                .placeholder(R.drawable.placeholder) //5
                .into(healthyGif);



        return v;
    }




}
