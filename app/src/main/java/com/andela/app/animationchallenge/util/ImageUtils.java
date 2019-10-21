package com.andela.app.animationchallenge.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {
    public static void loadImageFromUrl(ImageView imageView, String url) {
        if (url == null) return;
        Glide.with(imageView)
            .load(url)
            .into(imageView);
    }
}
