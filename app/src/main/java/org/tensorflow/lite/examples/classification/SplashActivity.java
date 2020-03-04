package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.tensorflow.lite.examples.classification.utils.MyPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoIV=findViewById(R.id.logo);
        ImageView textLogoIV=findViewById(R.id.textLogo);
        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        logoIV.startAnimation(anim);
        textLogoIV.startAnimation(anim);

        Thread timer =new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2000);


                    boolean isFirstTime = MyPreferences.isFirstSelection(SplashActivity.this);
                    if (isFirstTime) {
                        Intent intent=new Intent(getApplicationContext(), modelSelectionActivity.class);
                        startActivity(intent);                    }
                    else{
                      //  Intent intent=new Intent(getApplicationContext(), LogInActivity.class);
                       // startActivity(intent);
                    }


                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        };
        timer.start();
    }
}
