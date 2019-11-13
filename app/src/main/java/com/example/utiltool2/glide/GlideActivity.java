package com.example.utiltool2.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.util.BitmapUtil;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * author:lgh on 2019-11-08 08:57
 */
public class GlideActivity extends BaseActivity {

    private SubsamplingScaleImageView scaleImageView;
    private MyLayout myLayout;
    private String url = "http://192.168.20.140:8080/app/book.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        scaleImageView = findViewById(R.id.glide_subsampling_scale_imageview);
        myLayout = findViewById(R.id.background);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.load_failed)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transforms(new BlurTransformation(), new GrayscaleTransformation());

//        Glide + SubsampleScaleImageView
        SimpleTarget<Drawable> target = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                scaleImageView.setImage(ImageSource.bitmap(BitmapUtil.DrawableToBitmap(resource)));
            }
        };
//        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                scaleImageView.setImage(ImageSource.bitmap(resource));
//            }
//        };
//        Glide.with(this)
//                .load("http://guolin.tech/book.png")
//                .apply(options)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(target);
////                .into(myLayout.getTarget());//背景


        //测试自定义模块OKHTTP
        Glide.with(GlideActivity.this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(target);
    }
}
