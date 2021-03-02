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
import com.robotemi.sdk.TtsRequest;
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

public class ServeActivity extends AppCompatActivity implements OnRobotReadyListener,
        OnLocationsUpdatedListener,
        OnGoToLocationStatusChangedListener,
        OnDistanceToLocationChangedListener,
        OnCurrentPositionChangedListener,
        OnReposeStatusChangedListener,
        OnLoadMapStatusChangedListener {

    RoboTemiListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serve);


    }

    @Override
    protected void onStart() {
        super.onStart();
        listener = new RoboTemiListener();
        listener.init();
        listener.getRobot().addOnRobotReadyListener(this);
        listener.getRobot().addOnGoToLocationStatusChangedListener(this);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.serveLayout);
        List<String> locationList = listener.getRobot().getLocations();

        ArrayList<Button> buttons = new ArrayList<>();

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
            buttons.add(button);
            linearLayout.addView(button);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        listener.stop();
        listener.getRobot().getInstance().removeOnRobotReadyListener(this);
    }

    public void onServe(){
        listener.speak("주문하신 음료가 나왔습니다\n 가져가신 후에 돌아가기 버튼을 눌러주세요");
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.serveLayout);
        linearLayout.removeAllViews(); // view 초기화

        Button button = new Button(this);
        button.setText("돌아가기");
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                listener.goTo("home base");
            }
        });
        linearLayout.addView(button);
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
                listener.speak("Starting");
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                listener.speak("Calculating");
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                listener.speak("GOING");
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                listener.speak("COMPLETE");
                if(location.equals("home base")) goToMain();
                else onServe();
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                listener.speak("ABORT");
                break;
        }
    }

    @Override
    public void onLocationsUpdated(@NotNull List<String> list) {

    }

    @Override
    public void onLoadMapStatusChanged(int i) {

    }

    @Override
    public void onCurrentPositionChanged(@NotNull Position position) {

    }

    @Override
    public void onDistanceToLocationChanged(@NotNull Map<String, Float> map) {

    }

    @Override
    public void onReposeStatusChanged(int i, @NotNull String s) {

    }
}
