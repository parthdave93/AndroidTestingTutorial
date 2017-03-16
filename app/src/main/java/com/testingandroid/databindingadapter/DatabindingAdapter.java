package com.testingandroid.databindingadapter;

import android.databinding.ObservableField;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.testingandroid.R;

/**
 * Created by Parth Dave on 16/3/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class DatabindingAdapter {
    
    @android.databinding.BindingAdapter("custom:imageFromFile")
    public static <T> void viewImage(ImageView view, ObservableField<T> filePath) {
        Glide.with(view.getContext()).load("file://" + filePath.get()).placeholder(R.drawable.ic_user_vector).error(R.drawable.ic_user_vector).into(view);
    }
}
