package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
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


public class ResultNoDiseaseFragment extends Fragment {

    private ImageView leafImg;

    private TextView diseaseIdentifiedTxt;
    private TextView confidenceTxtView;
    private TextView commentTxt;

    private ImageView diseaseImg;
    private ImageView gifImg;

    private Disease disease;
    private String leafImgUrl;


    private String confidence;
    private String plantName;
    
    private boolean isHealthy;

    public ResultNoDiseaseFragment(Disease d , String img , String conf, String plantname, boolean h) {
        disease  = d;
        leafImgUrl = img;
        confidence = conf;
        plantName = plantname;
        isHealthy =h;
    }

    public static ResultNoDiseaseFragment newInstance(Disease disease , String leafImg , String confidence, String plantName, boolean isHealthy){
        return new ResultNoDiseaseFragment(disease ,leafImg , confidence, plantName, isHealthy);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.no_disease_plant_result_frag, container ,false);
        leafImg = v.findViewById(R.id.leafImg);
        diseaseImg = v.findViewById(R.id.diseaseImage);
        diseaseIdentifiedTxt = v.findViewById(R.id.diseaseIdentifiedTxt);
        confidenceTxtView= v.findViewById(R.id.model_confidence);
        gifImg= v.findViewById(R.id.gifImg);
        commentTxt= v.findViewById(R.id.commentTxt);

        confidence = confidence.substring(0,6);
        float conf = 100 * Float.parseFloat(confidence);
        diseaseIdentifiedTxt.setText("Your "+ plantName +" Plant is healthy.");
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
                .load("https://wf-live.enniscdn.net/wp-content/uploads/2019/05/20115802/Plant_V1.gif") //3
                .centerCrop() //4
                .placeholder(R.drawable.placeholder) //5
                .fallback(R.drawable.broken_image)
                .into(gifImg); //8


        if(!isHealthy){
            //override all previous views
            commentTxt.setText("No plant leaves detected.");
            diseaseImg.setVisibility(View.INVISIBLE);
            diseaseIdentifiedTxt.setVisibility(View.GONE);
            confidenceTxtView.setVisibility(View.GONE);
            Glide.with(getActivity())  //2
                    .load("https://media3.giphy.com/media/6265Sk4zLshv2688OZ/giphy.gif") //3
                    .centerCrop() //4
                    .placeholder(R.drawable.placeholder) //5
                    .fallback(R.drawable.broken_image)
                    .into(gifImg); //8
        }



        return v;
    }




}
