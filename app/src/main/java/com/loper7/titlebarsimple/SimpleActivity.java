package com.loper7.titlebarsimple;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.loper7.layout.TitleBar;

/**
 * @author LOPER7
 * @date 2018/1/15 11:17
 * @Description: 生活不止眼前的苟且
 */

public class SimpleActivity extends Activity {

    private TitleBar titleBar;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        type = getIntent().getIntExtra("type", 0);
        titleBar = findViewById(R.id.simple_titleBar);

        switch (type) {
            case 0://标题居中
                titleBar.setTitleText("标题");
                titleBar.setCenterTitle(true);
                titleBar.setBackImageResource(R.mipmap.ic_back_white);
                titleBar.clearActivityAnim();
                break;
            case 1://标题滚动
                titleBar.setTitleText("这是一个超级长的标题，所以他会滚动.................");
                titleBar.setBackImageResource(R.mipmap.ic_back_white);
                titleBar.setMenuImageResource(R.mipmap.ic_menu_white);
                titleBar.clearActivityAnim();
                break;
            case 2://点击水波纹
                titleBar.setTitleText("标题");
                titleBar.setUseRipple(true);
                titleBar.setMenuText("菜单");
                titleBar.setBackImageResource(R.mipmap.ic_back_white);
                titleBar.clearActivityAnim();
                break;
            case 3://下划线
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorTextWhite), 0);
                setDarkStatusIcon(true);
                titleBar.setTitleText("标题");
                titleBar.setUseRipple(true);
                titleBar.setShowBorder(true);
                titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.colorTextWhite));
                titleBar.setBackImageResource(R.mipmap.ic_back_gary);
                titleBar.setMenuImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_menu_gary));
                titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTextGrayDark));
                titleBar.clearActivityAnim();
                break;
            case 4://返回动画
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorTextWhite), 0);
                setDarkStatusIcon(true);
                titleBar.setTitleText("标题");
                titleBar.setBackGroundColor(ContextCompat.getColor(this, R.color.colorTextWhite));
                titleBar.setUseRipple(true);
                titleBar.setShowBorder(true);
                titleBar.setBackImageResource(R.mipmap.ic_back_gary);
                titleBar.setMenuImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_menu_gary));
                titleBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTextGrayDark));
                titleBar.setActivityAnim(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
                break;
        }

        titleBar.setOnMenuListener(new TitleBar.OnMenuListener() {
            @Override
            public void onMenuClick() {
                new AlertDialog.Builder(SimpleActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("您将前往浏览器应用，是否确认前往？")
                        .setNegativeButton("不，算了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setPositiveButton("是的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri content_url = Uri.parse("https://github.com/loperSeven/TitleBar");
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (type == 4)
            overridePendingTransition(R.anim.activity_backward_enter, R.anim.activity_backward_exit);
    }

    /**
     * 是否使用深色状态栏
     *
     * @param bDark
     */
    protected void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
}
