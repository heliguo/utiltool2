package com.example.utiltool2.examination;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.utiltool2.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * author:lgh on 2019-12-11 10:12
 */
public class ExamAppBarLayout extends AppBarLayout {

    private Context mContext;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private ImageView mIamgeView;
    private TextView mTextView;
    private Button mButton;

    public ExamAppBarLayout(Context context) {
        this(context, null);
    }

    public ExamAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();
            if (mActionBar != null)
                mActionBar.hide();
            initView(context);
            initWidget(context, attrs);
        }

    }

    private void initView(Context context) {

        LayoutInflater.from(context).inflate(R.layout.exam_system_toolbar, this, true);
        mToolbar = findViewById(R.id.toolbar);
        mIamgeView = findViewById(R.id.toolbar_left_image);
        mTextView = findViewById(R.id.toolbar_title);
        mButton = findViewById(R.id.toolbar_right_btn);

    }

    private void initWidget(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExamAppBarLayout);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.color.white, typedValue, true);

        int leftImageColor = typedArray.getColor(R.styleable.ExamAppBarLayout_leftImageColor, typedValue.data);
        mIamgeView.setColorFilter(leftImageColor);

        int toolbarColor =  typedArray.getColor(R.styleable.ExamAppBarLayout_toorbarColor, typedValue.data);
        mToolbar.setBackgroundColor(toolbarColor);

        int titleColor = typedArray.getColor(R.styleable.ExamAppBarLayout_titleColor, typedValue.data);
        mTextView.setTextColor(titleColor);

//        int titleTextSize = (int) typedArray.getDimension(R.styleable.ExamAppBarLayout_titleTextSize, typedValue.data);
//        mTextView.setTextSize(titleTextSize);

//        String titleText = typedArray.getString(R.styleable.ExamAppBarLayout_titleText);
//        mTextView.setText(titleText);

        typedArray.recycle();

    }

    public ExamAppBarLayout setBtnText(String btnText){

        mButton.setText(btnText);

        return this;
    }

    public ExamAppBarLayout setBtnListener(View.OnClickListener listener){
        mButton.setOnClickListener(listener);
        return this;
    }



}
