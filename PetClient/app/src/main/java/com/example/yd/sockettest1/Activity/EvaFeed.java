package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yd.sockettest1.R;

public class EvaFeed extends AppCompatActivity {

    int i=1;
    int j=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evafeed);
        findViews();
        setListeners1();
        setListeners2();
        setListeners3();
    }

    private void findViews() {
        button1= (Button) findViewById(R.id.Ffamily);
        button2= (Button) findViewById(R.id.Fsystem);
        button3= (Button) findViewById(R.id.Fselect);
    }


    private Button button1;
    private Button button2;
    private Button button3;

    private void setListeners1(){button1.setOnClickListener(call1);}
    private void setListeners2(){button2.setOnClickListener(call2);}
    private void setListeners3(){button3.setOnClickListener(call3);}

    private Button.OnClickListener call1=new Button.OnClickListener(){
        public void onClick(View v){
            if(i==0)
                Toast.makeText(EvaFeed.this, "亲，您只能评价一次哦", Toast.LENGTH_LONG).show();
            else{
                Intent intent1=new Intent();
                intent1.setClass(EvaFeed.this, evaluation.class);
                startActivity(intent1);
                i=0;
            }
        }
    };

    private Button.OnClickListener call2=new Button.OnClickListener(){
        public void onClick(View v){
            if(j==0)
                Toast.makeText(EvaFeed.this, "亲，您只能反馈一次哦", Toast.LENGTH_LONG).show();
            else{
                Intent intent2=new Intent();
                intent2.setClass(EvaFeed.this, feedback.class);
                startActivity(intent2);
                j=0;
            }
        }
    };
    private Button.OnClickListener call3=new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent3=new Intent();
            intent3.setClass(EvaFeed.this,end.class);
            startActivity(intent3);
        }
    };


}
