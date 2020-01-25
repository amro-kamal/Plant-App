package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.classification.tflite.Classifier;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ImageView leafImg;
    private TextView diseaseIdentifiedTxt;
    private TextView diseaseTypeTxt;
    private ImageView plantImg;
    private TextView categoryTxt;
    private TextView recognitionValueTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classification_result_act);

        leafImg = findViewById(R.id.leafImg);
        plantImg = findViewById(R.id.plantImage);
        diseaseIdentifiedTxt = findViewById(R.id.diseaseIdentifiedTxt);
        diseaseTypeTxt = findViewById(R.id.diseaseTypeTxt);
        categoryTxt = findViewById(R.id.categoryTxt);
        recognitionValueTextView=(TextView) findViewById(R.id.model_confidence);

        Intent intent =getIntent();

        String DiseaseTitle=  intent.getStringExtra("Disease title");
        String confidence=  intent.getStringExtra("confidence");
        diseaseTypeTxt.setText("Disease Type is"+DiseaseTitle);
        recognitionValueTextView.setText(confidence);


        //get the data from the previous activity
        //set vals

    }


}
