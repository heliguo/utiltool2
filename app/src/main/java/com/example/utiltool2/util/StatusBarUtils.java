package com.example.utiltool2.util;

import android.app.Activity;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @创建者 lgh
 * @创建时间 2019-08-14 15:44
 * @描述 状态栏设置
 */
public class StatusBarUtils {

    //设置状态栏颜色
    public static void setStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    //设置状态栏字体颜色
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    //隐藏状态栏
    public static void hideStatusBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int res = activity.getResources().getIdentifier("status_bar_height", "dimen", "  ");
        if (res > 0) {
            result = activity.getResources().getDimensionPixelSize(res);
        }
//        Rect rect= new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//        result = rect.top;
        return result;

    }



    /**
     *  Calculate ActionBar height
     * @param activity
     * @return
     */
    public static int getActionBarHeight(Activity activity) {

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


}
