package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private ImageView leafImg;
    private TextView diseaseIdentifiedTxt;
    private TextView diseaseTypeTxt;
    private ImageView plantImg;
    private TextView categoryTxt;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.classification_result_act);

        leafImg = findViewById(R.id.leafImg);
        plantImg = findViewById(R.id.plantImage);
        diseaseIdentifiedTxt = findViewById(R.id.diseaseIdentifiedTxt);
        diseaseTypeTxt = findViewById(R.id.diseaseTypeTxt);
        categoryTxt = findViewById(R.id.categoryTxt);

        //get the data from the previous activity
        //set vals

    }
}
