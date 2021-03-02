package com.example.temiapp.temi;


import android.util.Log;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoboTemiListener extends RoboTemi implements
        Robot.NlpListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener,
        OnConstraintBeWithStatusChangedListener,
        OnDetectionStateChangedListener,
        Robot.AsrListener {

    private Robot robot;

    public RoboTemiListener() {
        this.robot = robot.getInstance();
    }



    public void init(){
        robot.addNlpListener(this);
        robot.addOnBeWithMeStatusChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addConversationViewAttachesListenerListener(this);
        robot.addWakeupWordListener(this);
        robot.addTtsListener(this);
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnConstraintBeWithStatusChangedListener(this);
        robot.addOnDetectionStateChangedListener(this);
        robot.addAsrListener(this);
    }

    public void stop(){
        robot.removeNlpListener(this);
        robot.removeOnBeWithMeStatusChangedListener(this);
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeConversationViewAttachesListenerListener(this);
        robot.removeWakeupWordListener(this);
        robot.removeTtsListener(this);
        robot.removeOnLocationsUpdateListener(this);
        robot.removeDetectionStateChangedListener(this);
        robot.removeAsrListener(this);
        robot.stopMovement();
    }


    @Override
    public void onPublish(@NotNull ActivityStreamPublishMessage activityStreamPublishMessage) {

    }

    // 대화가 시작되었을 경우
    @Override
    public void onConversationAttaches(boolean b) {

    }

    // 자연 언어 처리 완료 후(natural language processing)
    @Override
    public void onNlpCompleted(@NotNull NlpResult nlpResult) {

    }

    //테미가 말하는 것의 상태 변화(Text to Speech)
    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {

    }

    //테미를 불러서 깨웠을때
    @Override
    public void onWakeupWord(@NotNull String s, int i) {

    }

    //테미가 따라오는 것의 행동 변화시 호출
    @Override
    public void onBeWithMeStatusChanged(@NotNull String status) {
        /*
         중단, 계산중, 포착, 검색, 시작, 추적
         */
        switch (status) {
            case "abort":
                robot.speak(TtsRequest.create("Abort", false));
                break;

            case "calculating":
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case "lock":
                robot.speak(TtsRequest.create("Lock", false));
                break;

            case "search":
                robot.speak(TtsRequest.create("search", false));
                break;

            case "start":
                robot.speak(TtsRequest.create("Start", false));
                break;

            case "track":
                robot.speak(TtsRequest.create("Track", false));
                break;
        }
    }

    //저장된 지점으로 이동할때의 행동 변화시 호출
    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, String status, int descriptionId, @NotNull String description) {
        Log.e("GoToStatusChanged", "status=" + status + ", descriptionId=" + descriptionId + ", description=" + description);
        robot.speak(TtsRequest.create(description, false));
        switch (status) {
            case OnGoToLocationStatusChangedListener.START:
                robot.speak(TtsRequest.create("Starting", false));
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                robot.speak(TtsRequest.create("Going", false));
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                robot.speak(TtsRequest.create("Completed", false));
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }

    //새롭게 저장된 곳이 생겼을때 전체 목록 호출
    @Override
    public void onLocationsUpdated(@NotNull List<String> list) {
        /*for (String str: list) {
           //각 지점에 대해서 코드..
        }*/
    }

    /*
    자동 대화 인식 결과(Auto speech recognition)
    인식 결과: 문자열 s
     */
    @Override
    public void onAsrResult(@NotNull String s) {

    }

    //제한된 따라가기
    //robot.constraintBeWith(); 의 호출로 차이가 난다.
    @Override
    public void onConstraintBeWithStatusChanged(boolean b) {

    }

    @Override
    public void onDetectionStateChanged(int i) {
        // DETECTED, IDLE, LOST
        switch (i){
            case DETECTED:
                //대상 포착시 행동
                break;
            case IDLE:
                //대기상태
                break;
            case LOST:
                //대상을 잃어버림
                break;
        }
    }
}
