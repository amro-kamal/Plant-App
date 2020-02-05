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

import org.tensorflow.lite.examples.classification.utils.Disease;

import java.io.IOException;


public class ResultFragment extends Fragment {

    private ImageView leafImg;
    private TextView diseaseIdentifiedTxt;
    private TextView diseaseTypeTxt;
    private ImageView plantImg;
    private TextView categoryTxt;
    private TextView confidenceTxtView;
    private TextView summaryTxt;
    private TextView symptomsTxt;
    private TextView treatmentTxt;
    private Disease disease;
    private String leafImgStr;


    public ResultFragment(Disease d ,String img) {
        disease  = d;
        leafImgStr = img;
    }

    public static ResultFragment newInstance(Disease disease , String leafImg){
        return new ResultFragment(disease ,leafImg);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.classification_result_frag, container ,false);
        leafImg = v.findViewById(R.id.leafImg);
        plantImg = v.findViewById(R.id.plantImage);
        diseaseIdentifiedTxt = v.findViewById(R.id.diseaseIdentifiedTxt);
        diseaseTypeTxt = v.findViewById(R.id.diseaseTypeTxt);
        categoryTxt = v.findViewById(R.id.categoryTxt);
        confidenceTxtView= v.findViewById(R.id.model_confidence);
        summaryTxt= v.findViewById(R.id.summaryTxt);
        treatmentTxt= v.findViewById(R.id.treatmentTxt);
        symptomsTxt= v.findViewById(R.id.symptomsTxt);

        Uri uri = Uri.parse(leafImgStr);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.broken_image);
        }
        leafImg.setImageBitmap(bitmap);

        String plant = "Tomato"; //TODO:
        String confidence = "confidence 60%";
        diseaseIdentifiedTxt.setText("Disease identified in "+ plant +"Plant.");
        diseaseTypeTxt.setText(disease.getTitle());
        categoryTxt.setText(disease.getCategory());
        confidenceTxtView.setText(confidence);
        summaryTxt.setText(disease.getSummary());
        symptomsTxt.setText(disease.getSymptoms());
        treatmentTxt.setText(disease.getTreatment());

        Glide.with(getActivity())  //2
                //.load(h.getPlantImgUrl()) //3
                .load(NetworkingLab.TEMP_URL + disease.getImageUrl()) //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder) //5
                .error(R.drawable.broken_image) //6
                .fallback(R.drawable.broken_image) //7
                .into(plantImg); //8



        return v;
    }




}
