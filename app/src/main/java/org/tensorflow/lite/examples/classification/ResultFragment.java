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


public class ResultFragment extends Fragment {

    private ImageView leafImg;
    private TextView diseaseIdentifiedTxt;
    private TextView diseaseTypeTxt;
    private ImageView diseaseImg;
    private ImageView diseaseImg2;
    private ImageView diseaseImg3;
    private TextView categoryTxt;
    private TextView confidenceTxtView;
    private TextView summaryTxt;
    private TextView symptomsTxt;
    private TextView treatmentTxt;
    private Disease disease;
    private String leafImgUrl;
    private String confidence;

    public ResultFragment(Disease d ,String img , String conf) {
        disease  = d;
        leafImgUrl = img;
        confidence = conf;
    }

    public static ResultFragment newInstance(Disease disease , String leafImg , String confidence){
        return new ResultFragment(disease ,leafImg , confidence);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.classification_result_frag, container ,false);
        leafImg = v.findViewById(R.id.leafImg);
        diseaseImg = v.findViewById(R.id.diseaseImage);
        diseaseImg2 = v.findViewById(R.id.leafImg2);
        diseaseImg3 = v.findViewById(R.id.leafImg3);
        diseaseIdentifiedTxt = v.findViewById(R.id.diseaseIdentifiedTxt);
        diseaseTypeTxt = v.findViewById(R.id.diseaseTypeTxt);
        categoryTxt = v.findViewById(R.id.categoryTxt);
        confidenceTxtView= v.findViewById(R.id.model_confidence);
        summaryTxt= v.findViewById(R.id.summaryTxt);
        treatmentTxt= v.findViewById(R.id.treatmentTxt);
        symptomsTxt= v.findViewById(R.id.symptomsTxt);




        confidence = confidence.substring(0,6);
        float conf = 100 * Float.parseFloat(confidence);
        String plant = ModelSingleton.getInstance(getActivity()).getCurrentModel();

        diseaseIdentifiedTxt.setText("Disease identified in "+ disease.getCategory() +" Plant.");
        diseaseTypeTxt.setText(disease.getTitle());
        categoryTxt.setText(disease.getCategory());
        confidenceTxtView.setText("confidence "+ conf+ "%");
        summaryTxt.setText(disease.getSummary());
        symptomsTxt.setText(disease.getSymptoms());
        treatmentTxt.setText(disease.getTreatment());


        String diseaseImgUrl = disease.getImageUrl1();

        Glide.with(getActivity())  //2
                .load(diseaseImgUrl) //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(leafImg); //8

//        String diseaseImgUrl = NetworkingLab.TEMP_URL+disease.getImageUrl2();
        String diseaseImgUrl2 = disease.getImageUrl2();
         Log.d("kkk","leaf url 2 "+diseaseImgUrl2);
        Glide.with(getActivity())  //2
                .load(diseaseImgUrl2)//3
                .centerCrop() //4
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(diseaseImg2); //8

        String diseaseImgUrl3 = disease.getImageUrl3();
        Log.d("kkk","leaf url 3 "+diseaseImgUrl3);

        Glide.with(getActivity())  //2
                .load(diseaseImgUrl3)//3
                .centerCrop() //4
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(diseaseImg3); //8



        return v;
    }




}
