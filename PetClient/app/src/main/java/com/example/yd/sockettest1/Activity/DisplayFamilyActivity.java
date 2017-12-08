package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.family_url;


public class DisplayFamilyActivity extends BaseActivity implements View.OnClickListener{

    String tag = DisplayFamilyActivity.class.getName();

    private String myUserId = new String("xiaoliang");
    private String toUserId = new String("xiaoli");

    String toUserName = "11";
    String toUserPhone = "18585080755";
    String toUserPhoto = "hello";

    Object user;
    String text;

    Button getChattingBtn;
    Button makeTransactionBtn;

    Intent intent;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Intent startingIntent = getIntent();
        if (startingIntent == null) {
            Log.e(tag, "No Intent? We're not supposed to be here...");
            finish();
            return;
        }
        myUserId = getIntent().getStringExtra("myUserId");
        toUserId = getIntent().getStringExtra("toUserId");
        setContentView(R.layout.activity_familyinfo);

        getChattingBtn = (Button)findViewById(R.id.getChattingBtn);
        getChattingBtn.setOnClickListener(this);
        makeTransactionBtn = (Button) findViewById(R.id.makeTransactionBtn);
        makeTransactionBtn.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.familyInfoTV);
        Drawable drawable = getResources().getDrawable(getResourceId(toUserPhoto));
        drawable.setBounds(0, 0, 230, 230);
        textView.setCompoundDrawables(null,drawable,null,null);
        showText();
    }
    public void onClick(View v) {

//根据 id 来区分分别是哪个按钮的点击事件
        switch (v.getId()){
            case R.id.getChattingBtn:
                Log.i(tag,myUserId+" chat with "+toUserId);
                 intent = new Intent(DisplayFamilyActivity.this, MainActivity.class);
                intent.putExtra("toUserId",toUserId);
                intent.putExtra("myUserId",myUserId);
                startActivity(intent);
                break;
            case R.id.makeTransactionBtn:
                Log.i(tag,myUserId+" make transaction to "+toUserId);
                intent = new Intent(DisplayFamilyActivity.this,Transaction.class);
                intent.putExtra("toUserId",toUserId);
                intent.putExtra("myUserId",myUserId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public int getResourceId(String name) {
        try{
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        }catch (Exception e){}
        return 0;
    }
    public void showText() {
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextSize(15);
        CommonRequest request = new CommonRequest();

        request.addRequestParam("user_id", toUserId);

        sendHttpPostRequest(family_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                if (response.getDataList().size() > 0) {
                    HashMap<String, String> map = response.getDataList().get(0);
                    toUserName=map.get("user_name");
                    toUserPhone=map.get("user_phone");
                    if(("".equals(toUserName)&&toUserName.length()<=0)||("".equals(toUserPhone)&&toUserPhone.length()<=0))
                        text="xiaoxiaoyan 18585080755";
                    textView.setText(text);
                }else{

                }

            }
            @Override
            public void fail(String failCode, String failMsg) {
                LogUtil.logErr(failMsg);
            }
        }, true);
        text=toUserName+"  "+ toUserPhone;
        textView.setText(text);
    }
}
