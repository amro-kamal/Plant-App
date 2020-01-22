package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    Button classfyBtn=(Button) findViewById(R.id.classifyBtn);
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent=getIntent();
        image=(ImageView) findViewById(R.id.image_container);
        ///set imageView

        classfyBtn=(Button) findViewById(R.id.classifyBtn);
        classfyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send the selected image to classifierActivity
                ClassifyImage();
            }
        });


    }
    public void ClassifyImage(){

    }
}
