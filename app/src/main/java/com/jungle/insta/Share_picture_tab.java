package com.jungle.insta;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Share_picture_tab extends Fragment implements View.OnClickListener {
    private ImageView shareImg;
    private TextView description;
    private Button btnShare;
   private Bitmap receivedImageBitmap;


    public Share_picture_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        shareImg = view.findViewById(R.id.shareImg);
        description = view.findViewById(R.id.description);
        btnShare = view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(Share_picture_tab.this);
        shareImg.setOnClickListener(Share_picture_tab.this);


   return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shareImg:
                if (Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
                }else {
                    getChosenImage();
                }
                break;
            case R.id.btnShare:
                if (receivedImageBitmap != null) {

                    if (description.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Error: You must specify a description.", Toast.LENGTH_SHORT).show();


                    } else {

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("img.png", bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("image", parseFile);
                        parseObject.put("image_des", description.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Loading...");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT).show();
                                 //   FancyToast.makeText(getContext(), "Done!!!", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                } else {
                                    Toast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                  //  FancyToast.makeText(getContext(), "Unknown error: " + e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                                dialog.dismiss();
                            }
                        });


                    }

                } else {

                    Toast.makeText(getContext(), "Error: You must select an image.", Toast.LENGTH_SHORT).show();
                   // FancyToast.makeText(getContext(), "Error: You must select an image."  , Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                }
                break;
        }

    }

    private void getChosenImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"),2000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1000){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), ""+grantResults[0], Toast.LENGTH_SHORT).show();
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 2000) {



            if (resultCode == Activity.RESULT_OK) {


                //Do something with your captured image.
                try {

                    Uri selectedImage = data.getData();
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);

//                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//
//                    if (cursor!=null){
//                        Toast.makeText(getContext(), "**"+cursor, Toast.LENGTH_LONG).show();
//                    }
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeStream(inputStream);
//
                    shareImg.setImageBitmap(receivedImageBitmap);


                } catch (Exception e) {
                    Toast.makeText(getContext(), "someting Wrong here !", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }
    }
}
