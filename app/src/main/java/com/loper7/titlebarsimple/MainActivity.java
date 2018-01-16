package com.loper7.titlebarsimple;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.loper7.layout.TitleBar;

public class MainActivity extends Activity implements View.OnClickListener {

    private TitleBar titleBar;
    private TextView tvCenter, tvRolling, tvRipple, tvBorder, tvBackAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);


        titleBar = findViewById(R.id.main_titleBar);
        tvCenter = findViewById(R.id.tv_titleCenter);
        tvRolling = findViewById(R.id.tv_titleRolling);
        tvRipple = findViewById(R.id.tv_titleRipple);
        tvBorder = findViewById(R.id.tv_titleBorder);
        tvBackAnim = findViewById(R.id.tv_titleBackAnim);

        tvCenter.setOnClickListener(this);
        tvRolling.setOnClickListener(this);
        tvRipple.setOnClickListener(this);
        tvBorder.setOnClickListener(this);
        tvBackAnim.setOnClickListener(this);

        titleBar.setOnMenuListener(new TitleBar.OnMenuListener() {
            @Override
            public void onMenuClick() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/loperSeven/TitleBar");
                intent.setData(content_url);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SimpleActivity.class);
        switch (view.getId()) {
            case R.id.tv_titleCenter:
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.tv_titleRolling:
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_titleRipple:
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.tv_titleBorder:
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.tv_titleBackAnim:
                intent.putExtra("type", 4);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_forward_enter, R.anim.activity_forward_exit);
                break;
        }
    }
}
