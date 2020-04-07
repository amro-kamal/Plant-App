package org.tensorflow.lite.examples.classification;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.Util;


import com.nightonke.boommenu.BoomMenuButton;

import org.tensorflow.lite.examples.classification.utils.MyPreferences;

public class modelSelectionActivity extends AppCompatActivity {
    BoomMenuButton bmb ;
    RelativeLayout selectBtn;
    TextView wellcome_tv;
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_selection);

        selectBtn=(RelativeLayout) findViewById(R.id.selectBtn);
        wellcome_tv=(TextView) findViewById(R.id.wellcome);
        bmb = (BoomMenuButton) findViewById(R.id.bmb1);
        // Use OnBoomListenerAdapter to listen part of methods

        initializeBmb1();




    }

    public void selectBtnClicked(View v){
        bmb.boom();
        selectBtn.setVisibility(v.INVISIBLE);
        wellcome_tv.setVisibility(v.INVISIBLE);

    }
    // Use OnBoomListener to listen all methods




    HamButton.Builder getHamButtonBuilder(int imgRes,String text, String subText) {
        String dark_green="#13ad69";
            return new HamButton.Builder()
                    .normalImageRes(imgRes)
                    .normalText(text)
                    .textSize(18)
                    .subNormalText(subText)
                    .buttonCornerRadius(30)
                    .normalColor(Color.parseColor(dark_green))
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
//                            Toast.makeText(modelSelectionActivity.this, " boom-button No." + index +" is clicked!",Toast.LENGTH_LONG).show();
                            //save the selected model as an app preference
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt(MyPreferences.MODEL_ID, index); // Storing model_id
                            editor.commit();
//                            Log.d("kkkk","model id is saved to pref ="+index);

//                            Toast.makeText(modelSelectionActivity.this, " model id was saved to app preferences",Toast.LENGTH_LONG).show();

                            intent=new Intent(modelSelectionActivity.this,MainActivity.class);
//                            intent.putExtra("Model ID",index);
                            startActivity(intent);

                        }
                    });

        }


    TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder(int imgRes,String text) {
            return new TextOutsideCircleButton.Builder()
                    .normalImageRes(imgRes)
                    .normalText(text)
                    .textSize(18)
                    .buttonCornerRadius(30)
                    .normalColor(R.color.green_transparent)
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(modelSelectionActivity.this, " boom-button No." + index +" is clicked!",Toast.LENGTH_LONG).show();
                        }
                    });
        }

    private void initializeBmb1() {
//        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setButtonEnum(ButtonEnum.Ham);

//        TextOutsideCircleButton.Builder circleBtn1=getTextOutsideCircleButtonBuilder(R.drawable.ic_launcher,"Tomato");
//            bmb.addBuilder(circleBtn1);
//        TextOutsideCircleButton.Builder circleBtn2=getTextOutsideCircleButtonBuilder(R.drawable.ic_launcher,"Botato");
//        bmb.addBuilder(circleBtn2);
//
//        TextOutsideCircleButton.Builder circleBtn3=getTextOutsideCircleButtonBuilder(R.drawable.ic_launcher,"Pipper");
//        bmb.addBuilder(circleBtn3);
//
//        TextOutsideCircleButton.Builder circleBtn4=getTextOutsideCircleButtonBuilder(R.drawable.ic_launcher,"Tomato");
//        bmb.addBuilder(circleBtn4);

        HamButton.Builder humBtn1=getHamButtonBuilder(R.drawable.tomato,"Tomatoes","");
        bmb.addBuilder(humBtn1);
        HamButton.Builder humBtn2=getHamButtonBuilder(R.drawable.potato,"Potatoes","");
        bmb.addBuilder(humBtn2);
        HamButton.Builder humBtn3=getHamButtonBuilder(R.drawable.tomato,"Tomatoes","");
        bmb.addBuilder(humBtn3);

        HamButton.Builder humBtn4=getHamButtonBuilder(R.drawable.potato,"Potatoes","");
        bmb.addBuilder(humBtn4);


    }

  }









