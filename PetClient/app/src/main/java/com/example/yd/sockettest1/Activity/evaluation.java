package com.example.yd.sockettest1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.yd.sockettest1.Urls.CommonUrl.eva_url;

public class evaluation extends Activity implements OnRatingBarChangeListener{
    private RatingBar ratingBar1;
    private RatingBar ratingBar2;
    private RatingBar ratingBar3;
    private Button button1;
    private Button button2;
    private TextView tvResult;
    private EditText pj;
    int familyid=142;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);
        findViews();
        setListeners();
    }

    private void findViews(){
        button1= (Button) findViewById(R.id.firstbutton);
        button2= (Button) findViewById(R.id.secondbutton);
        ratingBar1= (RatingBar) findViewById(R.id.ratingbar1);
        ratingBar2= (RatingBar) findViewById(R.id.ratingbar2);
        ratingBar3= (RatingBar) findViewById(R.id.ratingbar3);
        pj= (EditText) findViewById(R.id.pj);
    }
    private void setListeners(){
        button1.setOnClickListener(lsn1);
        button2.setOnClickListener(lsn2);
    }

    String id1=String.valueOf(familyid);
    private Button.OnClickListener lsn1=new Button.OnClickListener(){
        public void onClick(View v1){
            String star1=String.valueOf(ratingBar1.getRating());
            String star2=String.valueOf(ratingBar2.getRating());
            String star3=String.valueOf(ratingBar3.getRating());
            if(ratingBar1.getRating()!=0&&ratingBar2.getRating()!=0&&ratingBar3.getRating()!=0){
                register(id1,star1, star2, star3, pj.getText().toString());
            }else{
                Toast.makeText(evaluation.this, "请您对寄养家庭打星", Toast.LENGTH_LONG).show();
            }
            evaluation.this.finish();
        }
    };

    private Button.OnClickListener lsn2=new Button.OnClickListener(){
        public void onClick(View v2){
            String star1=String.valueOf(ratingBar1.getRating());
            String star2=String.valueOf(ratingBar2.getRating());
            String star3=String.valueOf(ratingBar3.getRating());
            if(ratingBar1.getRating()!=0&&ratingBar2.getRating()!=0&&ratingBar3.getRating()!=0){
                register(id1,star1, star2, star3, pj.getText().toString());
            }else{
                Toast.makeText(evaluation.this, "请您对寄养家庭打星", Toast.LENGTH_LONG).show();
            }
            Intent intent=new Intent();
            intent.setClass(evaluation.this,end.class);
            startActivity(intent);
        }
    };

    private void register(String str1, String str2, String str3, String str4, String str5){
        String registerURLStr=eva_url+"?fId="+str1+"&description="+str2+"&environment="+str3+"&attitude="+str4+"&longevaluation="+str5+"&user_id="+User.user_id;
        new MyAsyncTask(tvResult).execute(registerURLStr);
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private TextView tv; // 举例一个UI元素，后边会用到

        public MyAsyncTask(TextView v) {
            tv = v;
        }

        @Override
        protected void onPreExecute() {
            //Log.w("WangJ", "task onPreExecute()");
        }

        /**
         * @param params 这里的params是一个数组，即AsyncTask在激活运行是调用execute()方法传入的参数
         */
        @Override
        protected String doInBackground(String... params) {
            //Log.w("WangJ", "task doInBackground()");
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(params[0]); // 声明一个URL,注意如果用百度首页实验，请使用https开头，否则获取不到返回报文
                connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString(); // 这里返回的结果就作为onPostExecute方法的入参
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 如果在doInBackground方法，那么就会立刻执行本方法
            // 本方法在UI线程中执行，可以更新UI元素，典型的就是更新进度条进度，一般是在下载时候使用
        }

        /**
         * 运行在UI线程中，所以可以直接操作UI元素
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            //Log.w("WangJ", "task onPostExecute()");
            //tv.setText(s);
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
    }
}