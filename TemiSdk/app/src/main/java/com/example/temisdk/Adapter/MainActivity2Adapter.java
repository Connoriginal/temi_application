package com.example.temisdk.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.temisdk.R;

import java.util.ArrayList;

public class MainActivity2Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public MainActivity2Adapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(ArrayList<String> list) {//리스트 추가
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView== null){
            convertView = View.inflate(context, R.layout.activity_main2_dialog,null);
        }
        TextView log =convertView.findViewById(R.id.dialogView);
        log.setText(list.get(position));

        return convertView;
    }


}
