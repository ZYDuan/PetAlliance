package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.DialogUtil;
import com.example.yd.sockettest1.utils.LoadingDialogUtil;

import static com.example.yd.sockettest1.Urls.CommonUrl.home_register_url;

public class PetHomeRegis extends BaseActivity {

        TextView Save;
        EditText home_name;
        EditText home_address;
        EditText home_exp_year;
        EditText home_type;
        EditText home_pet;
        EditText home_info;
        ImageView home_regis_back;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pet_home_regis);
            Save= (TextView) findViewById(R.id.Save);
            home_name = (EditText) findViewById(R.id.home_name);
            home_address = (EditText) findViewById(R.id.home_address);
            home_exp_year = (EditText) findViewById(R.id.home_exp_year);
            home_type = (EditText) findViewById(R.id.home_type);
            home_pet = (EditText) findViewById(R.id.home_pet);
            home_info= (EditText) findViewById(R.id.home_info);
            home_regis_back=(ImageView)findViewById(R.id.pet_home_back);

            home_regis_back.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PetHomeRegis.this,HomeActivity.class);
                    PetHomeRegis.this.startActivity(intent);
                }
            });
            Save.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (home_name.getText().toString().equals("") || home_address.getText().toString().equals("") || home_exp_year.getText().toString().equals("")
                            || home_type.getText().toString().equals("") || home_pet.getText().toString().equals("")
                            || home_info.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请完善信息！", Toast.LENGTH_SHORT).show();
                    } else {
                        // 生成订单数据
                        CommonRequest request = new CommonRequest();
                        request.addRequestParam("user_id", User.user_id);  //这里要改
                        request.addRequestParam("home_name", home_name.getText().toString());  //这里要改
                        request.addRequestParam("home_address", home_address.getText().toString());
                        request.addRequestParam("home_exp_year", home_exp_year.getText().toString());
                        request.addRequestParam("home_type", home_type.getText().toString());
                        request.addRequestParam("home_pet", home_pet.getText().toString());
                        request.addRequestParam("home_intro", home_info.getText().toString());




                        sendHttpPostRequest(home_register_url, request, new ResponseHandler() {
                            @Override
                            public void success(CommonResponse response) {
                                LoadingDialogUtil.cancelLoading();
                                if (response.getDataList().size() >= 0) {

                                    Toast.makeText(getApplicationContext(), "登记成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PetHomeRegis.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    DialogUtil.showHintDialog(PetHomeRegis.this, "出现错误", true);
                                }
                            }

                            @Override
                            public void fail(String failCode, String failMsg) {
                                LoadingDialogUtil.cancelLoading();
                            }
                        }, true);
                    }

                }
            });
        }


}
