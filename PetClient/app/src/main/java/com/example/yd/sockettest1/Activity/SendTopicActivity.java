package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.DialogUtil;
import com.example.yd.sockettest1.utils.LoadingDialogUtil;

import static com.example.yd.sockettest1.Urls.CommonUrl.URL_SENDTOPIC;

public class SendTopicActivity extends BaseActivity {
    TextView tvcancel;
    TextView tvsend;
    EditText etname;
    EditText etcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_topic);
        tvcancel=(TextView) findViewById(R.id.textView_cancel);
        tvsend=(TextView) findViewById(R.id.textView_send);
        etname=(EditText) findViewById(R.id.editView_name);
        etcontent=(EditText) findViewById(R.id.editView_content);
        setListener();

    }
    public void setListener() {
        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendTopicActivity.this, Petdiscussion.class);
                startActivity(intent);
            }
        });
        tvsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etname.getText().toString().equals("") || etcontent.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请完善信息！", Toast.LENGTH_SHORT).show();
                }
                else{
                    // 生成话题数据
                    CommonRequest request = new CommonRequest();
                    request.addRequestParam("topic_name", etname.getText().toString());
                    request.addRequestParam("topic_content",etcontent.getText().toString());
                    request.addRequestParam("user_id", User.user_id);  //这里要改
                    sendHttpPostRequest(URL_SENDTOPIC, request, new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            LoadingDialogUtil.cancelLoading();

                            if (response.getDataList().size() >= 0) {

                                Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                                // 这里必须要给一个延迟，如果不加延迟则没有效果
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(SendTopicActivity.this, Petdiscussion.class);
                                        startActivity(intent);
                                    }
                                }, 100);

                            } else {
                                DialogUtil.showHintDialog(SendTopicActivity.this, "出现错误", true);
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
