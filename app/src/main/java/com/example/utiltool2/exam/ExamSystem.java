package com.example.utiltool2.exam;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.utiltool2.R;
import com.example.utiltool2.examination.ExamAppBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-12-11 09:34
 */
public class ExamSystem extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ExamSystem";

    private ExamViewPager examViewPager;
    private ExamViewPagerAdapter adapter;

    private List<View> viewList = new ArrayList<>();

    private int questionNumber = 5;
    private int choiceNumber = 4;
    //item_type.xml
    private TextView itemTypeTv;//题目类型
    private TextView currentNumTv;//当前题目
    private TextView totalNumTv;//题目总数

    //item_question.xml
    private TextView questionContentTv;//题目内容
    private String choiceContent = "B.音乐";

    //item_answer_keys.xml
    private LinearLayout cardViewLl;
    private TextView correctChoiceTv;//正确答案
    private TextView answerContentTv;//答案解析

    private ExamAppBarLayout barLayout;

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_system);
        examViewPager = findViewById(R.id.exam_viewpager);
        barLayout = findViewById(R.id.exam_system_bar);
        barLayout.setBtnText(questionNumber > 1 ? "下一题" : "确定");
        barLayout.setBtnListener(this);

        initView();

        adapter = new ExamViewPagerAdapter(viewList);

        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(examViewPager);
        examViewPager.setAdapter(adapter);

        examViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == questionNumber - 1 && sparseBooleanArray.get(position)) {
                    barLayout.setBtnText(position < questionNumber - 1 ? "下一题" : "完成");
                } else {
                    barLayout.setBtnText(position < questionNumber - 1 ? "下一题" : "确定");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        examViewPager.setPageTransformer(true,new DepthPageTransformer());

        examViewPager.setPagerListener(new PagerListener() {
            @Override
            public void scroll(float offsetX, float offsetY) {
                if (offsetX < 0) {//右滑
                    if (sparseBooleanArray.get(examViewPager.getCurrentItem()))
                        setCurrenView(true);
                    Log.e(TAG, "scroll: < 0  ```` " + sparseBooleanArray.get(examViewPager.getCurrentItem()));
                }
                if (offsetX > 0) {//左滑
                    setCurrenView(false);
                    Log.e(TAG, "scroll: > 0");
                }

            }
        });

    }

    private SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();

    private SparseArray<SparseArray<TextView>> questionAnswerArray;

    //key：当前题目，value：正确答案
    private SparseArray<Integer[]> correctAnswerArray;

    private SparseArray<LinearLayout> cardViewSparseArray;

    private void initView() {

        //key：当前题目，value：TextView实例及序号
        questionAnswerArray = new SparseArray<>();

        correctAnswerArray = new SparseArray<>();

        cardViewSparseArray = new SparseArray<>();

        for (int i = 0; i < questionNumber; i++) {

            //key：当前选项，value：textview实例
            SparseArray<TextView> answerArray = new SparseArray<>();

            SparseBooleanArray booleanArray1 = new SparseBooleanArray();

            ScrollView layout = (ScrollView) getLayoutInflater().inflate(R.layout.exam_view_item, null);
            //题目类型
            itemTypeTv = layout.findViewById(R.id.item_type);
            itemTypeTv.setText("多选题");

            //当前题目
            currentNumTv = layout.findViewById(R.id.item_type_current_num);
            currentNumTv.setText(String.valueOf(i + 1));

            //题目总数
            totalNumTv = layout.findViewById(R.id.item_type_current_total);
            totalNumTv.setText(questionNumber + "");

            //问题内容
            questionContentTv = layout.findViewById(R.id.item_question_content);
            questionContentTv.setText(R.string.question_content);

            //答案选项
            LinearLayout choiceLl = layout.findViewById(R.id.item_question_linearlayout);
            for (int j = 0; j < choiceNumber; j++) {
                TextView questionChoiceTv = (TextView) getLayoutInflater().inflate(R.layout.item_question_choice, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                if (j == choiceNumber - 1)
                    params.setMargins(px2dp(10), px2dp(20), px2dp(10), px2dp(30));
                else
                    params.setMargins(px2dp(10), px2dp(20), px2dp(10), 0);
                questionChoiceTv.setLayoutParams(params);
                questionChoiceTv.setText(choiceContent + i + j);
                questionChoiceTv.setPadding(px2dp(20), px2dp(20), px2dp(20), px2dp(20));

                SparseIntArray array = new SparseIntArray();
                array.put(i, j);//第几题，第几个选项
                questionChoiceTv.setTag(array);
                questionChoiceTv.setOnClickListener(this);

                answerArray.put(j, questionChoiceTv);//第几个选项的实例
                choiceLl.addView(questionChoiceTv);

                booleanArray1.put(j, false);

            }
            clickArray.put(i, booleanArray1);
            questionAnswerArray.put(i, answerArray);//第几道题的实例
            //正确答案
            cardViewLl = layout.findViewById(R.id.cardview_answer_ll);
            cardViewSparseArray.put(i, cardViewLl);
            correctChoiceTv = layout.findViewById(R.id.cardview_answer_title_correct);
            correctChoiceTv.setText(choiceNumber + "");
            correctAnswerArray.put(i, new Integer[]{1});//(第几题,正确答案)
            sparseBooleanArray.put(i, false);
            answerContentTv = layout.findViewById(R.id.cardview_answer_content);
            answerContentTv.setText(R.string.answer_content);

            viewList.add(layout);
        }
    }

    /**
     * clickArray中已存题目及选项选择情况，可用来统计用户选项分布情况
     */
    //key：当前题目，value：确定哪个选项被选择。 click 事件
    private SparseArray<SparseBooleanArray> clickArray = new SparseArray<>();


    private int totalRight;
    private int totalFalse;


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.toolbar_right_btn) {

            //只有答错才会进入
            if (sparseBooleanArray.get(examViewPager.getCurrentItem())) {
                //进入下一题或者提交
                if (examViewPager.getCurrentItem() < questionNumber - 1) {
                    setCurrenView(true);
                } else {
                    //提交操作
//                    barLayout.setBtnText( "确定");
                    Log.e(TAG, "答对" + totalRight + ";答错" + totalFalse);
                }
                return;
            }

            SparseArray<TextView> textViewSparseArray = questionAnswerArray.get(examViewPager.getCurrentItem());
            Integer[] integers = correctAnswerArray.get(examViewPager.getCurrentItem());
            SparseBooleanArray booleanArray = clickArray.get(examViewPager.getCurrentItem());
            int totalCount = 0;//选项记录

            switch (itemTypeTv.getText().toString()) {

                case "单选题":
                case "判断题":
                    for (int i = 0; i < booleanArray.size(); i++) {
                        if (booleanArray.get(i)) {
                            if (i == integers[0]) {//正确
                                textViewSparseArray.get(i).setBackgroundResource(R.drawable.textview_right_broder);
                                textViewSetUnClickable(textViewSparseArray);
                                totalRight++;
                                //答案正确，进入下一题或者提交
                                sparseBooleanArray.put(examViewPager.getCurrentItem(), true);
                                if (examViewPager.getCurrentItem() < questionNumber - 1) {
                                    setCurrenView(true);
                                } else {
                                    barLayout.setBtnText("完成");
                                    Log.e(TAG, "答对" + totalRight + ";答错" + totalFalse);
                                }

                                break;
                            } else {//错误
                                textViewSparseArray.get(i).setBackgroundResource(R.drawable.textview_false_broder);
                                textViewSparseArray.get(integers[0]).setBackgroundResource(R.drawable.textview_right_broder);
                                textViewSetUnClickable(textViewSparseArray);
                                totalFalse++;
                                sparseBooleanArray.put(examViewPager.getCurrentItem(), true);
                                //显示答案解析，使用标志位，再次点击进入下一题
                                cardViewSparseArray.get(examViewPager.getCurrentItem()).setVisibility(View.VISIBLE);
                                if (examViewPager.getCurrentItem() == questionNumber - 1) {
                                    barLayout.setBtnText("完成");
                                }
                            }
                        } else {
                            totalCount++;
                        }
                    }
                    if (totalCount == booleanArray.size()) {
                        toastShow("请选择答案!");
                        textViewSetClickable(textViewSparseArray);
                        return;
                    }

                    break;
                case "多选题":

                    int rightCount = 0;//多选正确记录

                    for (int i = 0; i < booleanArray.size(); i++) {
                        if (booleanArray.get(i)) {
                            for (Integer integer : integers) {
                                if (i == integer) {//正确
                                    rightCount++;
                                }
                            }
                        } else {
                            totalCount++;
                        }
                    }
                    if (totalCount == booleanArray.size()) {
                        toastShow("请选择答案!");
                        textViewSetClickable(textViewSparseArray);
                        return;
                    }

                    if (rightCount == booleanArray.size() - totalCount && rightCount == integers.length) {

                        for (Integer integer : integers) {
                            textViewSparseArray.get(integer).setBackgroundResource(R.drawable.textview_right_broder);
                        }
                        totalRight++;
                        //答案正确，进入下一题或者提交
                        sparseBooleanArray.put(examViewPager.getCurrentItem(), true);
                        if (examViewPager.getCurrentItem() < questionNumber - 1) {
                            setCurrenView(true);
                        } else {
                            barLayout.setBtnText("完成");
                            Log.e(TAG, "答对" + totalRight + ";答错" + totalFalse);
                        }
                        textViewSetUnClickable(textViewSparseArray);
                    } else {//错误
                        for (int i = 0; i < booleanArray.size(); i++) {
                            if (booleanArray.get(i)) {
                                textViewSparseArray.get(i).setBackgroundResource(R.drawable.textview_false_broder);
                            }
                        }
                        sparseBooleanArray.put(examViewPager.getCurrentItem(), true);

                        totalFalse++;
                        //显示答案解析，使用标志位，再次点击进入下一题
                        if (examViewPager.getCurrentItem() == questionNumber - 1) {
                            barLayout.setBtnText("完成");
                        }
                        cardViewSparseArray.get(examViewPager.getCurrentItem()).setVisibility(View.VISIBLE);
                        textViewSetUnClickable(textViewSparseArray);
                    }


                    break;
                case "填空题":
                    Log.e(TAG, "onClick: " + "填空题未完");

                    break;
            }


        } else {

            SparseIntArray array = (SparseIntArray) v.getTag();
            int key = array.keyAt(0);
            int value = array.get(key);
            switch (itemTypeTv.getText().toString()) {

                case "单选题":
                case "判断题":

                    v.setBackgroundResource(R.drawable.textview_checked_border);
                    SparseArray<TextView> textViewSparseArray = questionAnswerArray.get(key);
                    for (int i = 0; i < textViewSparseArray.size(); i++) {
                        if (i != value) {
                            textViewSparseArray.get(i).setBackgroundResource(R.drawable.textview_unchecked_border);
                            clickArray.get(key).put(i, false);
                        }
                    }
                    clickArray.get(key).put(value, true);
                    Log.e(TAG, itemTypeTv.getText().toString() + "  " + clickArray.get(key).get(value));

                    break;
                case "多选题":

                    if (clickArray.get(key).get(value)) {
                        v.setBackgroundResource(R.drawable.textview_unchecked_border);
                        clickArray.get(key).put(value, false);
                    } else {
                        v.setBackgroundResource(R.drawable.textview_checked_border);
                        clickArray.get(key).put(value, true);
                    }

                    break;
                case "填空题":
                    Log.e(TAG, "onClick: " + "填空题未完");

                    break;
            }
        }

    }

    private void textViewSetClickable(SparseArray<TextView> textViewSparseArray) {
        if (textViewSparseArray.size() == 0) {
            return;
        }
        for (int i = 0; i < textViewSparseArray.size(); i++) {
            textViewSparseArray.get(i).setFocusable(true);
            textViewSparseArray.get(i).setClickable(true);
        }
    }

    //提交后失去焦点
    private void textViewSetUnClickable(SparseArray<TextView> textViewSparseArray) {
        if (textViewSparseArray.size() == 0) {
            return;
        }
        for (int i = 0; i < textViewSparseArray.size(); i++) {
            textViewSparseArray.get(i).setFocusable(false);
            textViewSparseArray.get(i).setClickable(false);
        }
    }

    private void toastShow(String toast) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
        mToast.show();
    }


    private int px2dp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }

    private void setCurrenView(boolean leftOrRight) {
        if (!leftOrRight)
            examViewPager.setCurrentItem(examViewPager.getCurrentItem() == 0 ? 0 : examViewPager.getCurrentItem() - 1, true);
        else
            examViewPager.setCurrentItem(examViewPager.getCurrentItem() == questionNumber - 1 ? examViewPager.getCurrentItem() : examViewPager.getCurrentItem() + 1, true);
    }

}
