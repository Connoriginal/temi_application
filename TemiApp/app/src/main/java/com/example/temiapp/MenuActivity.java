package com.example.temiapp;

import android.os.Bundle;

import com.example.temiapp.temi.RoboTemiListener;

import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity{

    RoboTemiListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listener = new RoboTemiListener();
        listener.init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        listener.speak("주문을 도와드리겠습니다");

    }




}