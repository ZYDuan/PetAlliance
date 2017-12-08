package com.example.yd.sockettest1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;

import static com.example.yd.sockettest1.Urls.CommonUrl.feed_url;

public class feedback extends Activity implements OnRatingBarChangeListener,View.OnClickListener{

    private RatingBar ratingBar1;
    private RatingBar ratingBar2;
    private RatingBar ratingBar3;
    private EditText edt;
    private TextView tvResult;
    private ImageView image1;
    private ArrayList<String>msg1;
    private ListView lv1;
    private Button button3;
    private Button button4;
    int numberid=100;
    public String user_id= User.user_id;
    String nId=String.valueOf(numberid);
    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        image1= (ImageView) findViewById(R.id.down);
        msg1= new ArrayList<>();
        String str1="受众太少";
        String str2="功能不够完善";
        String str3="界面不舒适";
        String str4="交易信息不够完善";
        String str5="地图定位不够准确";
        String str6="App易出现卡顿现象";
        String str7="付费系统不够方便";
        String str8="寄养家庭提供服务水平偏低";
        String str9="其他";
        msg1.add(str1);
        msg1.add(str2);
        msg1.add(str3);
        msg1.add(str4);
        msg1.add(str5);
        msg1.add(str6);
        msg1.add(str7);
        msg1.add(str8);
        msg1.add(str9);
        image1.setOnClickListener(this);
        lv1= (ListView) findViewById(R.id.lv);
        lv1.setAdapter(new Myadapter());
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                edt.setText(msg1.get(position));
                lv1.setVisibility(View.GONE);
            }
        });
        findViews();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.down:
                lv1.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void findViews(){
        ratingBar1= (RatingBar) findViewById(R.id.ratingbar1);
        ratingBar2= (RatingBar) findViewById(R.id.ratingbar2);
        ratingBar3= (RatingBar) findViewById(R.id.ratingbar3);
        button3= (Button) findViewById(R.id.onebutton);
        button4= (Button) findViewById(R.id.twobutton);
        edt= (EditText) findViewById(R.id.fk);
    }

    private class Myadapter extends BaseAdapter{
        @Override
        public int getCount() {
            return msg1.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= LayoutInflater.from(feedback.this).inflate(R.layout.aaaa,null);
            TextView tv= (TextView) convertView.findViewById(R.id.tv1);
            tv.setText(msg1.get(position));
            return convertView;
        }
    }

    private void setListeners(){
        button3.setOnClickListener(lsn3);
        button4.setOnClickListener(lsn4);
    }

    private Button.OnClickListener lsn3=new Button.OnClickListener(){
        public void onClick(View v3){
            String star1=String.valueOf(ratingBar1.getRating());
            String star2=String.valueOf(ratingBar2.getRating());
            String star3=String.valueOf(ratingBar3.getRating());
            if(ratingBar1.getRating()!=0&&ratingBar2.getRating()!=0&&ratingBar3.getRating()!=0){
                register(nId, star1, star2, star3, edt.getText().toString());
            }else{
                Toast.makeText(feedback.this, "请您对本app打星", Toast.LENGTH_LONG).show();
            }
            feedback.this.finish();
        }
    };

    private Button.OnClickListener lsn4=new Button.OnClickListener(){
        public void onClick(View v4){
            String star1=String.valueOf(ratingBar1.getRating());
            String star2=String.valueOf(ratingBar2.getRating());
            String star3=String.valueOf(ratingBar3.getRating());
            if(ratingBar1.getRating()!=0&&ratingBar2.getRating()!=0&&ratingBar3.getRating()!=0){
                register(nId, star1, star2, star3, edt.getText().toString());
            }else{
                Toast.makeText(feedback.this, "请您对本app打星", Toast.LENGTH_LONG).show();
            }
            Intent intent=new Intent();
            intent.setClass(feedback.this,end.class);
            startActivity(intent);
        }
    };

    private void register(String str1, String str2, String str3, String str4, String str5){
        String registerURLStr=feed_url+"?nId="+str1+"&appearance="+str2+"&function="+str3+"&payment="+str4+"&longfeedback="+str5+"&user_id="+user_id;
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
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
    }
}
