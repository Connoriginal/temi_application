package com.example.temiapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.temiapp.temi.RoboTemiListener;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


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










    public class MenuAdapter extends BaseAdapter {

        public ArrayList<MenuData> items;

        public MenuAdapter() {
            this.items = new ArrayList<>();
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public MenuData getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void add(MenuData item) {
            items.add(item);
            notifyDataSetChanged();
        }

        public void setItem(MenuData menuData,int index){
            items.set(index,menuData);
        }

        public void removeItem(int index){
            items.remove(index);
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.menulist, viewGroup, false);
            }

            MenuData menuData = getItem(i);
            if(menuData != null) {
                TextView nameText = (TextView)findViewById(R.id.nameText);
                TextView priceText = (TextView)findViewById(R.id.priceText);
                nameText.setText(menuData.menuname);
                priceText.setText(Integer.toString(menuData.price));
            }

            return view;
        }
    }



}