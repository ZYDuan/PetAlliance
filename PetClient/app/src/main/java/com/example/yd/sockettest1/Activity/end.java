package com.example.yd.sockettest1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yd.sockettest1.R;

public class end extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);
        buttonbutton= (Button) findViewById(R.id.bbtt);
        buttonbutton.setOnClickListener(callcall);
    }
    private Button buttonbutton;

    private Button.OnClickListener callcall=new Button.OnClickListener(){
        public void onClick(View v){
            Intent it=new Intent();
            it.setClass(end.this,HomeActivity.class);
            startActivity(it);
        }
    };
}
