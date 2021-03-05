package com.example.temiapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.temiapp.temi.RoboTemiListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.listener.OnReposeStatusChangedListener;
import com.robotemi.sdk.navigation.model.Position;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity implements OnRobotReadyListener,
        OnGoToLocationStatusChangedListener {

    RoboTemiListener listener;
    Button btnOrderTable1, btnOrderTable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


    }

    @Override
    protected void onStart() {
        super.onStart();
        listener = new RoboTemiListener();
        listener.init();
        listener.getRobot().addOnRobotReadyListener(this);
        listener.getRobot().addOnGoToLocationStatusChangedListener(this);

        btnOrderTable1 = (Button)findViewById(R.id.btnOrderTable1);
        btnOrderTable2 = (Button)findViewById(R.id.btnOrderTable2);

        btnOrderTable1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goTo("1번");
            }
        });

        btnOrderTable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goTo("2번");
            }
        });


        /*
        List<String> locationList = listener.getRobot().getLocations();


        for(String str : locationList){
            if(str.equals("home base")) continue;
            Button button = new Button(this);
            button.setText(str);
            button.setTag(str);
            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.goTo(str);
                }
            });

        }
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        listener.stop();
        listener.getRobot().getInstance().removeOnRobotReadyListener(this);
    }

    public void goToMain(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        listener.stop();
        finish();
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

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, String status, int descriptionId, @NotNull String description) {
        Log.e("GoToStatusChanged", "location = " + location + "status=" + status + ", descriptionId=" + descriptionId + ", description=" + description);
        listener.speak(description);
        switch (status) {
            case OnGoToLocationStatusChangedListener.START:
                listener.speak(location +" 테이블로 이동하겠습니다");
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                listener.speak("이동중입니다");
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                listener.speak("도착했습니다.");
                if(location.equals("home base")) goToMain();
                else {
                    Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                    intent.putExtra("location",location);
                    startActivity(intent);
                    finish();
                }
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                listener.speak("ABORT");
                break;
        }
    }
}
