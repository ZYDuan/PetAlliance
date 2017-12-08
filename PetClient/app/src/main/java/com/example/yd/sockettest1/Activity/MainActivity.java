package com.example.yd.sockettest1.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yd.sockettest1.MyAdapter;
import com.example.yd.sockettest1.MyBean;
import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.chat_url;

public class MainActivity extends  BaseActivity {
    private RecyclerView rv;
    private MyAdapter adapter;
    private EditText et;
    private Button btn;
    private Socket socket;
    private ArrayList<MyBean> list;
;
    public static String to_user_id;
    public static String to_user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        to_user_id=getIntent().getStringExtra("to_user_id");
        to_user_name=getIntent().getStringExtra("user_name");
        rv = (RecyclerView) findViewById(R.id.rv);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        list = new ArrayList<>();
        adapter = new MyAdapter(this);

        try {
            getChatView();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        final Handler handler=new MyHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //socket=new Socket("172.20.10.2",10086);
                    socket=new Socket("110.64.89.190",10086);
                    InputStream inputStream=socket.getInputStream();
                    byte[] buffer=new byte[102400];
                    int len;
                    while((len=inputStream.read(buffer))!=-1){
                        String data=new String(buffer,0,len);

                        Message message=Message.obtain();
                        message.what=1;
                        message.obj=data;
                        handler.sendMessage(message);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String data=et.getText().toString();

                et.getText().clear();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OutputStream outputStream=socket.getOutputStream();
                            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            outputStream.write((User.user_id+"//"+data+"//"+df.format(new Date())+"//"+to_user_id).getBytes("GB2312"));
                            outputStream.flush();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                int localPort=socket.getLocalPort();
                String[] split=((String)msg.obj).split("//");
                if(split[0].equals(localPort+"")){
                    MyBean bean=new MyBean(split[1],1,split[2],"我：");
                    list.add(bean);
                }else{
                    MyBean bean = new MyBean(split[1],2,split[2],("来自：" + to_user_name));
                    list.add(bean);
                }

                adapter.setData(list);
                rv.setAdapter(adapter);

                LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                rv.setLayoutManager(manager);
                rv.smoothScrollToPosition(adapter.getData().size() - 1);
            }
        }
    }

    public void getChatView() throws UnsupportedEncodingException {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("user_id",User.user_id);
        request.addRequestParam("to_user_id",to_user_id);
        sendHttpPostRequest(chat_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {
                    ArrayList<HashMap<String, String>> map=new ArrayList<HashMap<String, String>>();
                    map=response.getDataList();
                    for(int i=0;i<map.size();i++){
                        HashMap<String, String> map1 = map.get(i);
                        if(map1.get("user_id").equals(User.user_id)){
                            MyBean bean=new MyBean(map1.get("chat_content"),1,map1.get("chat_time"),"我：");
                            list.add(bean);
                        }else{
                            MyBean bean = new MyBean(map1.get("chat_content"),2,map1.get("chat_time"),to_user_name+": ");
                            list.add(bean);
                        }

                        adapter.setData(list);
                        rv.setAdapter(adapter);

                        LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                        rv.setLayoutManager(manager);
                        rv.smoothScrollToPosition(adapter.getData().size() - 1);
                    }
                }else{
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                LogUtil.logErr(failMsg);
            }


        }, true);
    }
}
