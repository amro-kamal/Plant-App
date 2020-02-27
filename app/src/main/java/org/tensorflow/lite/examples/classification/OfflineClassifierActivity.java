package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import org.tensorflow.lite.examples.classification.tflite.Classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class OfflineClassifierActivity extends ClassifyImageActivity {
    FirebaseCustomLocalModel localModel;
    private  static FirebaseCustomRemoteModel remoteModel = null;
    private final int MODEL_DIM = 224;

    public static void downloadModel(String model_name){
         remoteModel =
                new FirebaseCustomRemoteModel.Builder("remote_mobilenet_quant").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Success.
                        Log.d("kkkk", "download process stoppped");
                       // Toast.makeText(getApplicationContext(), "download task finished", Toast.LENGTH_LONG).show();
                    }
                });

        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.


                        Log.d("kkkk", "model downloaaaaaaaaaaaaaded");
//                        Toast.makeText(Off.this, "model successfully downloaded", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //model should be downloaded before this
        //downloadModel("remote_mobilenet_model");

//         localModel = new FirebaseCustomLocalModel.Builder()
//                .setAssetFilePath("mobilenet_v1_1.0_224.tflite")
//                .build();
//         Log.d("kkkk","localModel created");


    }



    @Override
    public void classifyImage(final Bitmap bitmap) {
        // get image in the shape of  bitmap
        Log.d("kkkk","start classify Image Method");


            float[][][][] input = bitmapToInputArray(bitmap);

            try {
                FirebaseModelInputOutputOptions inputOutputOptions = null;
                inputOutputOptions = createInputOutputOptions();
                // [START mlkit_run_inference]
                FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                        .add(input)  // add() as many input arrays as your model requires
                        .build();

                FirebaseModelInputOutputOptions finalInputOutputOptions = inputOutputOptions;
                FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                        .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                            @Override
                            public void onSuccess(Boolean isDownloaded) {
                                FirebaseModelInterpreterOptions options;
                                if (isDownloaded) {
                                    Log.d("kkkkk", "model yeeeeeesssssssssss downloaded");

                                    options = new FirebaseModelInterpreterOptions.Builder(remoteModel).build();

                                    FirebaseModelInterpreter firebaseInterpreter = null;
                                    try {
                                        firebaseInterpreter = FirebaseModelInterpreter.getInstance(options);
                                    } catch (FirebaseMLException e) {
                                        e.printStackTrace();
                                    }

                                    firebaseInterpreter.run(inputs, finalInputOutputOptions)
                                            .addOnSuccessListener(
                                                    new OnSuccessListener<FirebaseModelOutputs>() {
                                                        @Override
                                                        public void onSuccess(FirebaseModelOutputs result) {
                                                            // ...
                                                            float[][] output = result.getOutput(0);
                                                            float[] probabilities = output[0];
                                                            BufferedReader reader = null;
                                                            try {
                                                                reader = new BufferedReader(
                                                                        new InputStreamReader(getAssets().open("labels.txt")));
                                                                for (int i = 0; i < probabilities.length; i++) {
                                                                    String label = reader.readLine();
                                                                    Log.i("MLKit", String.format("%s: %1.4f", label, probabilities[i]));
                                                                }

                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    })
                                            .addOnFailureListener(
                                                    new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Task failed with an exception
                                                            // ...
                                                            Log.d("xxxxx", "exception is : "+e.getMessage());
                                                            Log.d("kkkkkk", "faaaaaaaaaaaaaaaailed");
                                                        }
                                                    });

                                } else {
                                    Log.d("kkkkk", "model not downloaded");
                                }
                                // ...
                            }
                        });


            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }

    }
    private FirebaseModelInputOutputOptions createInputOutputOptions() throws FirebaseMLException {
        // [START mlkit_create_io_options]
        FirebaseModelInputOutputOptions inputOutputOptions =
                new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 224, 224, 3})
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 1001})
                        .build();
        // [END mlkit_create_io_options]

        return inputOutputOptions;
    }
    private float[][][][] bitmapToInputArray(Bitmap bmp) {
        // [START mlkit_bitmap_input]
        Bitmap bitmap = bmp;
        bitmap = Bitmap.createScaledBitmap(bitmap, MODEL_DIM, MODEL_DIM, true);

        int batchNum = 0;
        float[][][][] input = new float[1][MODEL_DIM][MODEL_DIM][3];
        for (int x = 0; x < MODEL_DIM; x++) {
            for (int y = 0; y < MODEL_DIM; y++) {
                int pixel = bitmap.getPixel(x, y);
                // Normalize channel values to [-1.0, 1.0]. This requirement varies by
                // model. For example, some models might require values to be normalized
                // to the range [0.0, 1.0] instead.
                input[batchNum][x][y][0] = (Color.red(pixel) - 127) / 128.0f;
                input[batchNum][x][y][1] = (Color.green(pixel) - 127) / 128.0f;
                input[batchNum][x][y][2] = (Color.blue(pixel) - 127) / 128.0f;
            }
        }
        // [END mlkit_bitmap_input]

        return input;
    }


    public void  SendResultToResultActivity(List<Classifier.Recognition> results) {
        Intent intent = new Intent(this, ResultActivity.class);

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

