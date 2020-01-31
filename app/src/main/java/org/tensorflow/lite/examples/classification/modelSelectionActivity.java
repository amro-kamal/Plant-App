package org.tensorflow.lite.examples.classification;



import android.content.Intent;
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
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onBackgroundClick() {
                super.onBoomWillHide();
                selectBtn.setVisibility(View.VISIBLE);
                wellcome_tv.setVisibility(View.VISIBLE);
            }
        });
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
                            intent=new Intent(modelSelectionActivity.this,MainActivity.class);
                            intent.putExtra("Model ID",index);
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

        HamButton.Builder humBtn1=getHamButtonBuilder(R.drawable.ic_launcher,"Tomatos","");
        bmb.addBuilder(humBtn1);
        HamButton.Builder humBtn2=getHamButtonBuilder(R.drawable.ic_launcher,"Botatos","");
        bmb.addBuilder(humBtn2);
        HamButton.Builder humBtn3=getHamButtonBuilder(R.drawable.ic_launcher,"Pipper","");
        bmb.addBuilder(humBtn3);

        HamButton.Builder humBtn4=getHamButtonBuilder(R.drawable.ic_launcher,"Tomato","");
        bmb.addBuilder(humBtn4);

//        float w_0_5 = bmb.getHamWidth() / 2;
//        float h_0_5 = bmb.getHamHeight() / 2;
//
//        float hm_0_5 = bmb.getPieceHorizontalMargin() / 2;
//        float vm_0_5 = bmb.getPieceVerticalMargin() / 2;

//        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(+6), Util.dp2px(-6)));
//        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(+2), Util.dp2px(+2)));
//        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(-2), Util.dp2px(-2)));
////        bmb.getCustomPiecePlacePositions().add(new PointF(0, 0));
//        bmb.getCustomPiecePlacePositions().add(new PointF(Util.dp2px(-6), Util.dp2px(+6)));

//        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(-80), Util.dp2px(-80)));
//        bmb.getCustomButtonPlacePositions().add(new PointF(0, 0));
//        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(+80), Util.dp2px(+80)));
//        bmb.getCustomButtonPlacePositions().add(new PointF(Util.dp2px(+100), Util.dp2px(+100)));


//        bmb.getCustomPiecePlacePositions().add(new PointF(-w_0_5 - hm_0_5, -h_0_5 - vm_0_5));
//        bmb.getCustomPiecePlacePositions().add(new PointF(+w_0_5 + hm_0_5, -h_0_5 - vm_0_5));
//        bmb.getCustomPiecePlacePositions().add(new PointF(-w_0_5 - hm_0_5, +h_0_5 + vm_0_5));
//        bmb.getCustomPiecePlacePositions().add(new PointF(+w_0_5 + hm_0_5, +h_0_5 + vm_0_5));



    }
//
//    public class BuilderManager {
//
//        private  int[] imageResources = new int[]{
////                R.drawable.bat,
////                R.drawable.bear,
////                R.drawable.bee,
////                R.drawable.butterfly,
////                R.drawable.cat,
////                R.drawable.deer,
////                R.drawable.dolphin,
////                R.drawable.eagle,
////                R.drawable.horse,
////                R.drawable.elephant,
////                R.drawable.owl,
////                R.drawable.peacock,
////                R.drawable.pig,
////                R.drawable.rat,
////                R.drawable.snake,
////                R.drawable.squirrel
//        };
//
//        private  int imageResourceIndex = 0;
//
//         int getImageResource() {
//            if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
//            return imageResources[imageResourceIndex++];
//        }
//
//         SimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
//            return new SimpleCircleButton.Builder()
//                    .normalImageRes(getImageResource());
//        }
//
//         SimpleCircleButton.Builder getSquareSimpleCircleButtonBuilder() {
//            return new SimpleCircleButton.Builder()
//                    .isRound(false)
//                    .shadowCornerRadius(Util.dp2px(20))
//                    .buttonCornerRadius(Util.dp2px(20))
//                    .normalImageRes(getImageResource());
//        }
//
//         TextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
//            return new TextInsideCircleButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_inside_circle_button_text_normal);
//        }
//
//         TextInsideCircleButton.Builder getSquareTextInsideCircleButtonBuilder() {
//            return new TextInsideCircleButton.Builder()
//                    .isRound(false)
//                    .shadowCornerRadius(Util.dp2px(10))
//                    .buttonCornerRadius(Util.dp2px(10))
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_inside_circle_button_text_normal);
//        }
//
//         TextInsideCircleButton.Builder getTextInsideCircleButtonBuilderWithDifferentPieceColor() {
//            return new TextInsideCircleButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_inside_circle_button_text_normal)
//                    .pieceColor(Color.WHITE);
//        }
//
//         TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder() {
//            return new TextOutsideCircleButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_outside_circle_button_text_normal);
//        }
//
//         TextOutsideCircleButton.Builder getSquareTextOutsideCircleButtonBuilder() {
//            return new TextOutsideCircleButton.Builder()
//                    .isRound(false)
//                    .shadowCornerRadius(Util.dp2px(15))
//                    .buttonCornerRadius(Util.dp2px(15))
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_outside_circle_button_text_normal);
//        }
//
//         TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderWithDifferentPieceColor() {
//            return new TextOutsideCircleButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_outside_circle_button_text_normal)
//                    .pieceColor(Color.WHITE);
//        }
//
//         HamButton.Builder getHamButtonBuilder() {
//            return new HamButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_ham_button_text_normal)
//                    .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
//        }
//
//         HamButton.Builder getHamButtonBuilder(String text, String subText) {
//            return new HamButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalText(text)
//                    .subNormalText(subText);
//        }
//
//         HamButton.Builder getPieceCornerRadiusHamButtonBuilder() {
//            return new HamButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_ham_button_text_normal)
//                    .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
//        }
//
//         HamButton.Builder getHamButtonBuilderWithDifferentPieceColor() {
//            return new HamButton.Builder()
//                    .normalImageRes(getImageResource())
//                    .normalTextRes(R.string.text_ham_button_text_normal)
//                    .subNormalTextRes(R.string.text_ham_button_sub_text_normal)
//                    .pieceColor(Color.WHITE);
//        }
//
//         List<String> getCircleButtonData(ArrayList<Pair> piecesAndButtons) {
//            List<String> data = new ArrayList<>();
//            for (int p = 0; p < PiecePlaceEnum.values().length - 1; p++) {
//                for (int b = 0; b < ButtonPlaceEnum.values().length - 1; b++) {
//                    PiecePlaceEnum piecePlaceEnum = PiecePlaceEnum.getEnum(p);
//                    ButtonPlaceEnum buttonPlaceEnum = ButtonPlaceEnum.getEnum(b);
//                    if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
//                            || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
//                            || buttonPlaceEnum == ButtonPlaceEnum.Vertical) {
//                        piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
//                        data.add(piecePlaceEnum + " " + buttonPlaceEnum);
//                        if (piecePlaceEnum == PiecePlaceEnum.HAM_1
//                                || piecePlaceEnum == PiecePlaceEnum.HAM_2
//                                || piecePlaceEnum == PiecePlaceEnum.HAM_3
//                                || piecePlaceEnum == PiecePlaceEnum.HAM_4
//                                || piecePlaceEnum == PiecePlaceEnum.HAM_5
//                                || piecePlaceEnum == PiecePlaceEnum.HAM_6
//                                || piecePlaceEnum == PiecePlaceEnum.Share
//                                || piecePlaceEnum == PiecePlaceEnum.Custom
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_1
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_2
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_3
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_4
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_5
//                                || buttonPlaceEnum == ButtonPlaceEnum.HAM_6
//                                || buttonPlaceEnum == ButtonPlaceEnum.Custom) {
//                            piecesAndButtons.remove(piecesAndButtons.size() - 1);
//                            data.remove(data.size() - 1);
//                        }
//                    }
//                }
//            }
//            return data;
//        }
//
//         List<String> getHamButtonData(ArrayList<Pair> piecesAndButtons) {
//            List<String> data = new ArrayList<>();
//            for (int p = 0; p < PiecePlaceEnum.values().length - 1; p++) {
//                for (int b = 0; b < ButtonPlaceEnum.values().length - 1; b++) {
//                    PiecePlaceEnum piecePlaceEnum = PiecePlaceEnum.getEnum(p);
//                    ButtonPlaceEnum buttonPlaceEnum = ButtonPlaceEnum.getEnum(b);
//                    if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
//                            || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
//                            || buttonPlaceEnum == ButtonPlaceEnum.Vertical) {
//                        piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
//                        data.add(piecePlaceEnum + " " + buttonPlaceEnum);
//                        if (piecePlaceEnum.getValue() < PiecePlaceEnum.HAM_1.getValue()
//                                || piecePlaceEnum == PiecePlaceEnum.Share
//                                || piecePlaceEnum == PiecePlaceEnum.Custom
//                                || buttonPlaceEnum.getValue() < ButtonPlaceEnum.HAM_1.getValue()) {
//                            piecesAndButtons.remove(piecesAndButtons.size() - 1);
//                            data.remove(data.size() - 1);
//                        }
//                    }
//                }
//            }
//            return data;
//        }
//
//        private  BuilderManager ourInstance = new BuilderManager();
//
//        public  BuilderManager getInstance() {
//            return ourInstance;
//        }
//
//        private BuilderManager() {
//        }
//    }

}
