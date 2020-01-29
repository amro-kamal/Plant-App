package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.tflite.Classifier;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassifyImageActivity extends AppCompatActivity {
    private Button classfyBtn;
    private ImageView image;

    private Classifier.Model clfModel = Classifier.Model.QUANTIZED;
    private int numThreads = 1;
    private int sensorOrientation = 0;
    private long lastProcessingTimeMs;
    private Classifier classifier;
    private Bitmap rgbFrameBitmap = null;


    private Handler handler;
    private HandlerThread handlerThread;
    private Uri uriToSend= null;


    private static final Logger LOGGER = new Logger();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classify_image_activity);
//        setHandler();

        image= findViewById(R.id.image_container);
        classfyBtn=findViewById(R.id.classifyBtn);
        Bundle extras = getIntent().getExtras();
        String imgPath = extras.getString("ImagePath");

        Bitmap bitmap = null;
        if(imgPath!=null){
            bitmap = BitmapFactory.decodeFile(imgPath);
            File f = new File(imgPath);
            uriToSend = Uri.fromFile(f);
        }else{
            try {
                Uri uri = Uri.parse(extras.getString("imageUri"));
                uriToSend = uri;
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
        image.setImageBitmap(bitmap);

        //set rgbFrameBitmap from Intent
        rgbFrameBitmap = bitmap;
        createClassifier();


        classfyBtn.setOnClickListener(v -> {
            //send the selected image to classifierActivity
            ClassifyImage();

        });


    }


    private void createClassifier() {
        if (rgbFrameBitmap == null) {
            // Defer creation until we're getting camera frames.
            return;
        }
        final Classifier.Device device = Classifier.Device.CPU;
        final Classifier.Model model = clfModel;
//        runInBackground(() -> recreateClassifier(model, device, numThreads));
        recreateClassifier(model, device, numThreads);

    }

    @Override
    public synchronized void onPause() {
        LOGGER.d("onPause " + this);

        handlerThread.quitSafely();
        try {
            handlerThread.join();
            handlerThread = null;
            handler = null;
        } catch (final InterruptedException e) {
            LOGGER.e(e, "Exception!");
        }

        super.onPause();
    }



//    protected void ClassifyImage() {
//        // get image in the shape of  bitmap
//        runInBackground(
//                new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (classifier != null) {
//
//                            final long startTime = SystemClock.uptimeMillis();
//                            final List<Classifier.Recognition> results =
//                                    classifier.recognizeImage(rgbFrameBitmap, sensorOrientation);
//                            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
//                            LOGGER.v("Detect: %s", results);
//
//                            runOnUiThread(
//                                    new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            //showResultsInBottomSheet(results);
//                                            Log.d("aaaaa","results of classifcation: "+results);
//                                            if(uriToSend!=null){
//                                                Intent in  = new Intent(getApplicationContext(), ResultActivity.class);
//                                                in.putExtra("ImageUri" ,uriToSend.toString() );
//                                                //pass results
//                                                startActivity(in);
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void recreateClassifier(Classifier.Model model, Classifier.Device device, int numThreads) {
        if (classifier != null) {
            LOGGER.d("Closing classifier.");
            classifier.close();
            classifier = null;
        }
        if (device == Classifier.Device.GPU && model == Classifier.Model.QUANTIZED) {
            LOGGER.d("Not creating classifier: GPU doesn't support quantized models.");
            runOnUiThread(
                    () -> {
                        Toast.makeText(this, "GPU does not yet supported quantized models.", Toast.LENGTH_LONG)
                                .show();
                    });
            return;
        }
        try {
            LOGGER.d(
                    "Creating classifier (model=%s, device=%s, numThreads=%d)", model, device, numThreads);
            classifier = Classifier.create(this, model, device, numThreads);
        } catch (IOException e) {
            LOGGER.e(e, "Failed to create classifier.");
        }


    }




    protected void ClassifyImage() {
        // get image in the shape of  bitmap


                        if (classifier != null) {

                            final long startTime = SystemClock.uptimeMillis();
                            final List<Classifier.Recognition> results =
                                    classifier.recognizeImage(rgbFrameBitmap, sensorOrientation);
                            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
                            LOGGER.v("Detect: %s", results);

                            Log.d("aaaaa","results of classifcation: "+results);

                            SendResultToResultActivity(results);





                    }

    }

    public void  SendResultToResultActivity(List<Classifier.Recognition> results) {
        Intent intent = new Intent(ClassifyImageActivity.this, ResultActivity.class);

        if (results != null && results.size() >= 3) {
            Classifier.Recognition recognition = results.get(0);
            if (recognition != null) {
                if (recognition.getTitle() != null) {
                    intent.putExtra("Disease title", recognition.getTitle());
                    Log.d("ClassifyImageSend","Put result,title");

                }
                if (recognition.getConfidence() != null) {
                    Log.d("ClassifyImageSend","Put result,confidence");

                    intent.putExtra("confidence", String.format("%.2f", (100 * recognition.getConfidence())) + "%");
                }

            }
        }

        startActivity(intent);

    }



}

