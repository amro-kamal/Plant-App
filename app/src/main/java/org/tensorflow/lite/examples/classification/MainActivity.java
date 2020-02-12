package org.tensorflow.lite.examples.classification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.lite.examples.classification.recyclerview.HistoryItemsAdaptor;
import org.tensorflow.lite.examples.classification.utils.HistoryItem;
import org.tensorflow.lite.examples.classification.utils.imageFolder;
import org.tensorflow.lite.examples.classification.utils.pictureFacer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {
    private static final int PICK_IMAGE_CODE = 1;
    public Uri imageUri;
    private int STORAGE_PERMISSION_CODE = 1;

    // Recycler View object
    RecyclerView recyclerView;
    // adapter class object
    GalleryItemAdaptor gAdapter;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
        //____________________________________________________________

        Button cameraBtn = (Button) findViewById(R.id.cameraBtn);
        Button gallaryBtn = (Button) findViewById(R.id.gallaryBtn);

        Intent intent=getIntent();
        int model_id=intent.getIntExtra("Model ID",-1);
        Log.d("kkkk","model id="+model_id);

        String model_name="";
        switch (model_id){
            case 0: {model_name="tomatoes"; break;}
            case 1:{ model_name="potatoes"; break;}
            case 2: {model_name="t"; break;}
            default:{model_name="empty";}
        }
//        if(!model_name.equals("")){
//        RemoteClassifierActivity.downloadModel(model_name);
//        }
//        else{
//            Toast.makeText(this,"model Name Error",Toast.LENGTH_LONG).show();
//        }
        Log.d("kkkk","model name ="+model_name);

        cameraBtn.setOnClickListener(v -> openClasifierActivity());
        ArrayList<imageFolder> folds = getPicturePaths();
        Log.d("kkkk","getPicturePaths done");


        // initialisation with id's
        recyclerView = findViewById(R.id.recyclerview);


        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(MainActivity.this,
                                                                LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(HorizontalLayout);


        if (folds.size()==0) {
            recyclerView.setVisibility(View.GONE);
            Log.d("kkkk","folds.size()==0");
        } else {
            Log.d("kkkk", "folds size="+folds.size());
            ArrayList<pictureFacer> images = getAllImagesByFolder(folds.get(0).getPath());
            Log.d("kkkk", "getAllImagesByFolder done");

            gAdapter = new GalleryItemAdaptor(images);
            recyclerView.setAdapter(gAdapter);
            Log.d("imagessize","images size is "+images.size());
        }

       gallaryBtn.setOnClickListener(v -> {
           //////open the gallary
           Intent gallary_intent = new Intent();
           gallary_intent.setType("image/*");
           gallary_intent.setAction(Intent.ACTION_GET_CONTENT);
           startActivityForResult(Intent.createChooser(gallary_intent, "select picture"), PICK_IMAGE_CODE);
       });

        //Histroyitems list Fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.historyFragmentContainer);
        if (fragment == null) {
            fragment = HistoryListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.historyFragmentContainer, fragment)
                    .commit();
        }


    }


    private ArrayList<imageFolder> getPicturePaths() {
        ArrayList<imageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                imageFolder folds = new imageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder + "/"));
                folderpaths = folderpaths + folder + "/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                } else {
                    for (int i = 0; i < picFolders.size(); i++) {
                        if (picFolders.get(i).getPath().equals(folderpaths)) {
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        for (int i = 0; i < picFolders.size(); i++) {
            Log.d("kkkkkkk", picFolders.get(i).getFolderName() + " and path = " + picFolders.get(i).getPath() + " " + picFolders.get(i).getNumberOfPics());
        }


        return picFolders;
    }

    public ArrayList<pictureFacer> getAllImagesByFolder(String path){
        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);

        try {
            cursor.moveToFirst();
            do{
                pictureFacer pic = new pictureFacer();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));
                images.add(pic);

            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > images.size()-11;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Log.d("imageuri","image received");

            Intent intent = new Intent(this, ClassifyImageActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            startActivity(intent);
            Log.d("imageuri","image sending");

        }
    }


    public void openClasifierActivity() {
        Intent intent = new Intent(this, ClassifierActivity.class);
        startActivity(intent);
    }


    public class GalleryItemAdaptor extends RecyclerView.Adapter<GalleryItemHolder> {

        private List<pictureFacer> picturesList;

        public GalleryItemAdaptor(List<pictureFacer> picturesList) {
            this.picturesList = picturesList;
        }

        @Override
        public GalleryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Get LayoutInflater object.
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            // Inflate the RecyclerView item layout xml.
            View itemView = layoutInflater.inflate(R.layout.gallary_image_layout, parent, false);

            // Create and return our customRecycler View Holder object.
            GalleryItemHolder holder = new GalleryItemHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(GalleryItemHolder holder, int position) {
            if (picturesList != null) {
                // Get car item dto in list.
                pictureFacer image = picturesList.get(position);
                String imagePath = image.getPicturePath();
                if (image != null) {
                    Glide.with(getApplicationContext())
                            .load(imagePath)
                            .apply(new RequestOptions().centerCrop())
                            .into(holder.getImageView());
                    holder.getImageView().setOnClickListener(v ->{
                        Intent in = new Intent(getApplicationContext() , ClassifyImageActivity.class);
                        File f = new File(imagePath);
                        String uriToSend = Uri.fromFile(f).toString();

                        in.putExtra("imageUri", uriToSend);
                        startActivity(in);
                    });

                }
            }
        }

        @Override
        public int getItemCount() {
            int ret = 0;
            if (picturesList != null) {
                ret = picturesList.size();
            }
            return ret;
        }

    }

    public class GalleryItemHolder extends RecyclerView.ViewHolder {

        private ImageView img ;

        public GalleryItemHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.gallaryImageItem);

        }

        public ImageView getImageView() {
            return img;
        }


    }



    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
