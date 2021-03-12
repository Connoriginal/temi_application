package com.example.temiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.temiapp.temi.RoboTemiListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity{

    RoboTemiListener listener;
    ListView menuList;
    MyAdapter menuAdapter;
    ListView selectList;
    MyAdapter selectAdapter;
    Button btDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listener = new RoboTemiListener();
        listener.init();

        menuList = (ListView) findViewById(R.id.listviewForMenu);
        menuAdapter = new MyAdapter(true);

        menuList.setAdapter(menuAdapter);

        selectList = (ListView) findViewById(R.id.listviewForSelect);
        selectAdapter = new MyAdapter(false);
        selectList.setAdapter(selectAdapter);

        btDone = (Button) findViewById(R.id.btDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice=0;
                for(MenuData menuData: selectAdapter.getItems()){
                    totalPrice += menuData.price;
                }
                Toast.makeText(getApplicationContext(),"총 "+Integer.toString(totalPrice)+"원 입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        for(int i =1;i<10;i++)
            menuAdapter.add(new MenuData("test"+Integer.toString(i),1));

    }

    @Override
    protected void onStart() {
        super.onStart();
        listener.speak("주문을 도와드리겠습니다");

    }




    public class MyAdapter extends BaseAdapter {

        public ArrayList<MenuData> items;
        public boolean is_menu = false;

        public MyAdapter(boolean is_menu) {
            this.items = new ArrayList<>();
            this.is_menu = is_menu;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        public ArrayList<MenuData> getItems(){
            return items;
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
                TextView nameText = (TextView) view.findViewById(R.id.nameText);
                TextView priceText = (TextView) view.findViewById(R.id.priceText);
                nameText.setText(menuData.menuname);
                priceText.setText(Integer.toString(menuData.price));

                LinearLayout l = (LinearLayout) view.findViewById(R.id.linearForListParent);
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(is_menu){
                            selectAdapter.add(menuAdapter.getItem(i));
                            notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"주문 목록에 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setTitle("주문을 취소하시겠습니까?");
                            builder.setMessage("\"예\"를 누르면 주문이 취소됩니다.");
                            builder.setPositiveButton("예",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            selectAdapter.removeItem(i);
                                            notifyDataSetChanged();
                                            Toast.makeText(getApplicationContext(),"주문 취소.",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            builder.setNegativeButton("아니오",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            builder.show();
                        }
                    }
                });
            }

            return view;
        }
    }



}