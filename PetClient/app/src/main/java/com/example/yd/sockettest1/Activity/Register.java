package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yd.sockettest1.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.yd.sockettest1.Urls.CommonUrl.register_url;

public class Register extends AppCompatActivity {
    String URL_REGISTER =register_url;



    //WebService serv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = (EditText) findViewById(R.id.et_uname);
        final EditText etPassword1 = (EditText) findViewById(R.id.et_pass);

        //final EditText etPassword2 = (EditText) findViewById(R.id.et_passConfirm);
        final EditText etAddress = (EditText) findViewById(R.id.et_address);
        final EditText etPhone = (EditText) findViewById(R.id.et_phone);
        final EditText etEmail = (EditText) findViewById(R.id.et_email);

        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkPw()){
                    Toast toast = Toast.makeText(getApplicationContext(),"两次密码输入不一致，请重新输入！",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    //serv.register(etName.getText().toString(),etPassword1.getText().toString(),
                    //etAddress.getText().toString(),etPhone.getText().toString(),etEmail.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpURLConnection connection = null;
                            try{
                                //URL url = new URL(URL_REGISTER+"username="+etName.toString()+"&password="+etPassword1.toString()+
                                //"&address="+etAddress.toString()+"&phone="+etPhone.toString()+"&email="+etEmail.toString());
                                URL url = new URL(URL_REGISTER);
                                connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                                connection.setRequestMethod("POST"); // 设置请求方法，“POST或GET”
                                connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                                connection.setReadTimeout(8000);// 设置网络报文收发超时时间
                                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                                String param = "username="+etName.getText().toString()+"&password="+etPassword1.getText().toString()+
                                        "&address="+ URLEncoder.encode(etAddress.getText().toString(),"utf-8")+"&phone="+
                                        etPhone.getText().toString()+"&email="+etEmail.getText().toString();
                                out.writeBytes(param);
                                InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null){
                                    response.append(line);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Intent logIn = new Intent(Register.this,LoginActivity.class);
                    startActivity(logIn);
                    Register.this.isFinishing();//注册结束之后，关闭界面
                }
            }
        });
    }
    public  boolean checkPw(){
        final EditText etPassword1 = (EditText) findViewById(R.id.et_pass);
        final EditText etPassword2 = (EditText) findViewById(R.id.et_passConfirm);

        String pw1 = etPassword1.getText().toString();
        String pw2 = etPassword2.getText().toString();

        if(!pw1.isEmpty()&&pw1.equals(pw2))
            return true;
        else
            return false;
    }

}
