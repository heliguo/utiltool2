package com.example.utiltool2.ui.explosion.Impl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.ui.explosion.ExplosionFiled;
import com.example.utiltool2.ui.explosion.FallingParticleFactory;


/**
 * author:lgh on 2019-09-03 10:47
 */
public class ExplosionActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explosion);
        ExplosionFiled explosionFiled = new ExplosionFiled(this, new FallingParticleFactory());
        explosionFiled.addListener(findViewById(R.id.ll_explosion));
        //        explosionFiled.addListener(findViewById(R.id.imageview_explosion));
    }
}
