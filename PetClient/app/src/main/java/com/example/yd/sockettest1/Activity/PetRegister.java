package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;

import static com.example.yd.sockettest1.Urls.CommonUrl.pet_register_url;

public class PetRegister extends BaseActivity {

    ImageView back;
    TextView save;
    Spinner type,sex;
    EditText name,date,weight,notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_register);

        save=(TextView)findViewById(R.id.save);
        back=(ImageView)findViewById(R.id.pet_regis_back);
        type=(Spinner)findViewById(R.id.spinner_pettype);
        sex=(Spinner)findViewById(R.id.sp_pet_sex);

        name=(EditText)findViewById(R.id.res_name);
         date=(EditText)findViewById(R.id.res_date);
        weight=(EditText)findViewById(R.id.res_weight);
        notes=(EditText)findViewById(R.id.res_notes);

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetRegister.this,HomeActivity.class);
                PetRegister.this.startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        CommonRequest request = new CommonRequest();

        request.addRequestParam("user_id", User.user_id);
        request.addRequestParam("pet_name",name.getText().toString());

        request.addRequestParam("pet_sex",sex.getSelectedItem().toString());
        request.addRequestParam("pet_kind",type.getSelectedItem().toString());
        request.addRequestParam("pet_weight", weight.getText().toString());
        request.addRequestParam("pet_day", date.getText().toString());
        request.addRequestParam("pet_intro", notes.getText().toString());

        sendHttpPostRequest(pet_register_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                Toast.makeText(getApplicationContext(), "宠物添加成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PetRegister.this,HomeActivity.class);
                PetRegister.this.startActivity(intent);
            }

            @Override
            public void fail(String failCode, String failMsg) {
            }
        }, true);
    }


}
