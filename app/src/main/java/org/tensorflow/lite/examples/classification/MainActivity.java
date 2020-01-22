package org.tensorflow.lite.examples.classification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_CODE=1;
    public Uri imageUri;
    public Bitmap bitmap;
    File storageRootDir= Environment.getExternalStorageDirectory();
    GridView gallaryGrid;
    ArrayList<File> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cameraBtn= (Button) findViewById(R.id.cameraBtn);
        Button gallaryBtn= (Button) findViewById(R.id.gallaryBtn);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClasifierActivity();
            }
        });


        gallaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////open the gallary
                Intent gallary_intent=new Intent();
                gallary_intent.setType("image/*");
                gallary_intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser( gallary_intent,"select picture"),PICK_IMAGE_CODE);


            }
        });


//        gallaryGrid=(GridView) findViewById(R.id.gallaryGrid);
//           imagesList=image_Read(storageRootDir);
//           grid_adapter ga=new grid_adapter();
//           gallaryGrid.setAdapter(ga);

//                     TextView tv=findViewById(R.id.tv);
//                     Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                               tv.setText(String.valueOf(allImagesuri));

//        Cursor Cursor = getContentResolver()
//                .query(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        null,
//                        null,
//                        null,
//                        MediaStore.Images.Media.DEFAULT_SORT_ORDER);
//


    }

//    public class grid_adapter extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return imagesList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return imagesList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
////            View convert_view=null;
//            if (convertView==null){
//                convertView=getLayoutInflater().inflate(R.layout.gallary_image_layout,parent,false);
//                ImageView image=(ImageView)convertView.findViewById(R.id.gallaryImageItem);
//                image.setImageURI(Uri.parse(imagesList.get(position).toString()));
//            }
//            return convertView;
//
//        }
//    }

//    private ArrayList<File> image_Read(File storageRootDir) {
//        File[] files=storageRootDir.listFiles();
//        ArrayList<File> imagesList=new ArrayList<>();
//        for(int i=0;i<files.length;i++){
//            if (files[i].isDirectory()){
//                imagesList.addAll(image_Read(files[i]));
//            }else {if(files[i].getName().endsWith(".jpg")) {
//                imagesList.add(files[i]);
//            }
//
//            }
//
//        }
//        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
//        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
//
//        return imagesList;
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==PICK_IMAGE_CODE && resultCode==RESULT_OK){
            imageUri=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                /////////The image is stored in the bitmap
                opnenImageActivity();

        }catch (IOException e){
                e.printStackTrace();

            }

            }
    }

    public void opnenImageActivity(){
        Intent intent=new Intent(this,ImageActivity.class);
        //put the image into the intent
        startActivity(intent);


    }


    public void openClasifierActivity(){
        Intent intent=new Intent(this,ClassifierActivity.class);
        startActivity(intent);
    }


}
