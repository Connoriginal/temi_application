package com.example.temisdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openExample(view);
            }
        });
    }

    public void openExample(View v){
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        //intent.putExtra("data", "data");
        startActivity(intent);
        //startActivityForResult(intent,0);
    }
}