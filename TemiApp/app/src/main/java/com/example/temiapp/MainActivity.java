package com.example.temiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.temiapp.temi.RoboTemiListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener {

    RoboTemiListener listener;
    Button btnOrder, btnServe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOrder = (Button)findViewById(R.id.btnOrder);
        btnServe = (Button)findViewById(R.id.btnServe);

        //버튼 애니메이션 추가
        btnOrder.animate().alpha(1f).setDuration(1000);
        btnServe.animate().alpha(1f).setDuration(1000);


    }

    @Override
    protected void onStart() {
        super.onStart();
        listener = new RoboTemiListener();
        listener.init();
        listener.getRobot().getInstance().addOnRobotReadyListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        listener.stop();
        listener.getRobot().getInstance().removeOnRobotReadyListener(this);
    }


    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                listener.getRobot().onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void goOrder(View v){
        listener.stop();// 예제 리스너 추가를 위해서 Main activity의 적용된 리스너 모두 제거
        Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
        //intent.putExtra("data", "data");
        startActivity(intent);
        //startActivityForResult(intent,0);
        finish();
    }
    
    public void goServe(View v){
        listener.stop();
        Intent intent = new Intent(getApplicationContext(),ServeActivity.class);
        startActivity(intent);

        //Mainactivity를 종료
        finish();
    }
}