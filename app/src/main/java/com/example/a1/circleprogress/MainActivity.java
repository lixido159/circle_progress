package com.example.a1.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements CircleProgressBar.RotationListener {
    CircleProgressBar circleProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleProgressBar = findViewById(R.id.bar);
        circleProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressBar.startAnim(0);
            }
        });
        circleProgressBar.setOnRotationListener(this);
    }


    @Override
    public void onStartRotating() {
        Log.v("开始", "开始了");
    }

    @Override
    public void onRotating(int value) {
        circleProgressBar.setText(value+"%");
    }

    @Override
    public void onFinishRotating() {
        circleProgressBar.setText("100%");
    }
}
