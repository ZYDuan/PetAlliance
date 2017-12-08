package com.example.yd.sockettest1.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.yd.sockettest1.Urls.CommonUrl.login_url;
import static com.example.yd.sockettest1.common.User.user_id;

//import web.WebService;


public class LoginActivity extends AppCompatActivity  {
    private String URL_LOGIN = login_url;
    public static String idd;
    //WebService serv;

    public Context getContext(){
        return this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        String id = sp.getString("user_id", "");
        if (id.equals("")) {

            final EditText etName = (EditText) findViewById(R.id.et_name);
            //final TextView tvInfo = (TextView) findViewById(R.id.tv_info);
            final EditText etPassword = (EditText) findViewById(R.id.et_password);

            //点击注册按钮发生的事件
            Button btnRegister = (Button) findViewById(R.id.btn_register);
            if (btnRegister != null) {
                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent regItn = new Intent(LoginActivity.this, Register.class);
                        startActivity(regItn);
                    }
                });
            }

            //点击登录按钮发生的事件
            Button btnLogin = (Button) findViewById(R.id.btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final String text1 = etName.getText().toString();//获取用户名的字符串
                    final String text2 = etPassword.getText().toString();//获取密码的字符串
                    if (!text1.isEmpty() && !text2.isEmpty()) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpURLConnection connection = null;
                                try {
                                    URL url = new URL(URL_LOGIN + "username=" + text1 + "&password=" + text2);
                                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间

                                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    StringBuilder response = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        response.append(line);
                                    }
                                    idd= response.toString();
                                    user_id = idd;
                                    User.user_name=text1;
                                    SharedPreferences mSharedPreference = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mSharedPreference.edit();
                                    editor.putString("user_id", user_id);
                                    editor.putString("user_name",text1);
                                    editor.commit();
                                    Intent intent = new Intent(getContext(), HomeActivity.class);

                                    getContext().startActivity(intent);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "账号、密码都不能为空！", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }else{
            user_id=id;
            User.user_name=sp.getString("user_name", "");
            Intent intent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(intent);
        }
    }
}




