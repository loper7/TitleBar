package com.loper7.titlebarsimple;

import android.app.Application;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.loper7.layout.TitleBar;

/**
 * @author LOPER7
 * @date 2018/1/8 13:43
 * @Description: 生活不止眼前的苟且
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TitleBar.getConfig()
                .setTitleTextSize(getApplicationContext(), 16)
                .setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextBlackLight))
                .setMenuTextSize(getApplicationContext(), 14)
                .setMenuTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextBlackLight))
                .setPadding(getApplicationContext(), 16)
                .setCenterTitle(false)
                .setUseRipple(false)
                .setTitleEllipsize(TextUtils.TruncateAt.MARQUEE)
                .setBackgroundColor(Color.WHITE)
                .setBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBorder))
                .setShowBorder(false)
                .setBorderWidth(getApplicationContext(), 0.6f)
                .setActivityBackAnim(R.anim.activity_backward_enter, R.anim.activity_backward_exit);

    }
}
