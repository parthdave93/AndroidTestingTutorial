package com.testingandroid.login;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.testingandroid.R;
import com.testingandroid.databinding.ActivityGallerypickBinding;
import com.testingandroid.databinding.ActivityLoginBinding;
import com.testingandroid.model.LoginModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageFromGalleryActivity extends AppCompatActivity {
    private ActivityGallerypickBinding activityLoginBinding;
    public ObservableField<String> imagePath = new ObservableField<>();
    private final int RC_IMAGE_GALLARY = 89;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallerypick);
        activityLoginBinding.setImagePath(imagePath);
    }
    
    public void onGalleryPickClick(View view) {
        Intent iGetAvatar = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGetAvatar, RC_IMAGE_GALLARY);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_IMAGE_GALLARY && resultCode==RESULT_OK){
            if (data == null)
                return;
            Uri uri = data.getData();
            if(uri==null)
                return;
            imagePath.set(getRealPathFromURI(uri));
        }
    }
    
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
