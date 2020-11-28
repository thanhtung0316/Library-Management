package com.hoptb.library_management.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class AppBindingAdapter {

    @BindingAdapter("setImage")
    public static void setImage( ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
