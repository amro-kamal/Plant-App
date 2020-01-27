package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import org.tensorflow.lite.examples.classification.env.Logger;
import org.tensorflow.lite.examples.classification.tflite.Classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class RemoteClassifierActivity extends AppCompatActivity {
    private Button classfyBtn;
    private ImageView image;


    private Bitmap rgbFrameBitmap = null;


    private Uri uriToSend= null;

    private  FirebaseCustomRemoteModel remoteModel = null;
    private void downloadModel(){
         remoteModel =
                new FirebaseCustomRemoteModel.Builder("remote_mobilenet_model").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Success.
                        classfyBtn.setEnabled(true);
                        Log.d("kkkk", "model downloaaaaaaaaaaaaaded");
                        Toast.makeText(getApplicationContext(), "model successfully downloaded", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classify_image_activity);

        image= findViewById(R.id.image_container);
        classfyBtn=findViewById(R.id.classifyBtn);

        classfyBtn.setEnabled(false);


        downloadModel();
       /* Bundle extras = getIntent().getExtras();
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

        }*/
       Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
               R.drawable.lion);
        image.setImageBitmap(bitmap);

        //set rgbFrameBitmap from Intent
        rgbFrameBitmap = bitmap;


        classfyBtn.setOnClickListener(v -> {
            //send the selected image to classifierActivity
            try {
                ClassifyImage();
            } catch (FirebaseMLException e) {
                e.printStackTrace();
            }

        });


    }







    protected void ClassifyImage() throws FirebaseMLException {
        // get image in the shape of  bitmap



        if (true) {
            float[][][][] input = bitmapToInputArray();
            FirebaseModelInputOutputOptions inputOutputOptions = createInputOutputOptions();

            // [START mlkit_run_inference]
            FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                    .add(input)  // add() as many input arrays as your model requires
                    .build();

            FirebaseModelInterpreterOptions options =
                    new FirebaseModelInterpreterOptions.Builder(remoteModel).build();
            FirebaseModelInterpreter firebaseInterpreter = FirebaseModelInterpreter.getInstance(options);
            firebaseInterpreter.run(inputs, inputOutputOptions)
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
                                    Log.d("kkkkkk", "faaaaaaaaaaaaaaaailed");
                                }
                            });




        }

    }
    private FirebaseModelInputOutputOptions createInputOutputOptions() throws FirebaseMLException {
        // [START mlkit_create_io_options]
        FirebaseModelInputOutputOptions inputOutputOptions =
                new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 224, 224, 3})
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 3})
                        .build();
        // [END mlkit_create_io_options]

        return inputOutputOptions;
    }
    private float[][][][] bitmapToInputArray() {
        // [START mlkit_bitmap_input]
        Bitmap bitmap = rgbFrameBitmap;
        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

        int batchNum = 0;
        float[][][][] input = new float[1][224][224][3];
        for (int x = 0; x < 224; x++) {
            for (int y = 0; y < 224; y++) {
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

