package org.tensorflow.lite.examples.classification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.tflite.Classifier;

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


    private static final Logger LOGGER = new Logger();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classify_image_activity);
        setHandler();

        image= findViewById(R.id.image_container);
        classfyBtn=findViewById(R.id.classifyBtn);

        Bitmap lion = BitmapFactory.decodeResource(getResources(),
                R.drawable.lion);
        image.setImageBitmap(lion);

        //set rgbFrameBitmap from Intent
        rgbFrameBitmap = lion;
        createClassifier();


        classfyBtn.setOnClickListener(v -> {
            //send the selected image to classifierActivity
            ClassifyImage();
        });


    }

    protected synchronized void runInBackground(final Runnable r) {
        if (handler != null) {
            handler.post(r);
        }
    }
    private void createClassifier() {
        if (rgbFrameBitmap == null) {
            // Defer creation until we're getting camera frames.
            return;
        }
        final Classifier.Device device = Classifier.Device.CPU;
        final Classifier.Model model = clfModel;
        runInBackground(() -> recreateClassifier(model, device, numThreads));

    }

    private synchronized void setHandler(){
        handlerThread = new HandlerThread("inference");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }
    @Override
    public synchronized void onResume() {
        LOGGER.d("onResume " + this);
        super.onResume();
        setHandler();

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



    protected void ClassifyImage() {
        // get image in the shape of  bitmap
        runInBackground(
                new Runnable() {
                    @Override
                    public void run() {

                        if (classifier != null) {

                            final long startTime = SystemClock.uptimeMillis();
                            final List<Classifier.Recognition> results =
                                    classifier.recognizeImage(rgbFrameBitmap, sensorOrientation);
                            lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
                            LOGGER.v("Detect: %s", results);

                            runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            //showResultsInBottomSheet(results);

                                        }
                                    });
                        }
                    }
                });
    }

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
}

