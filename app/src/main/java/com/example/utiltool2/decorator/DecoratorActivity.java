package com.example.utiltool2.decorator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

/**
 * author:lgh on 2020-01-10 14:54
 */
public class DecoratorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorator);
    }

    public void cost(View view) {
        Coffee coffee = new Mocha();
        coffee = new Milk(coffee);//加奶
        coffee = new Sugar(coffee);//加糖
        Toast.makeText(this, "牛奶类型：" + coffee.getDescriptionString() + "   价格：" + coffee.cost(),
                Toast.LENGTH_SHORT).show();
    }
}
