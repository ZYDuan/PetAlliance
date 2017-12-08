package com.example.yd.sockettest1.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.DialogUtil;
import com.example.yd.sockettest1.utils.LoadingDialogUtil;
import com.example.yd.sockettest1.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.URL_COMMENT;
import static com.example.yd.sockettest1.Urls.CommonUrl.URL_SENDCOMMENT;


public class TopicDetailActivity extends BaseActivity {
    ListView lvComment;
    ImageView ivComent;
    RelativeLayout rlcomment;
    Button comment_send;
    EditText comment_content;
    LinearLayout llcomment;
    ScrollView svcomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        final String topic_id=getIntent().getStringExtra("topic_id");
        showTopic();

        ivComent =(ImageView) findViewById(R.id.title_imv);//+ 评论
        rlcomment =(RelativeLayout)findViewById(R.id.rl_comment);// 评论区
        lvComment = (ListView) findViewById(R.id.ListView_comment);// 评论区列表
        comment_send=(Button) findViewById(R.id.comment_send);//发送评论Button
        comment_content=(EditText) findViewById(R.id.comment_content);// 评论框
        llcomment=(LinearLayout) findViewById(R.id.LinearLayout_comment);//屏幕
        svcomment=(ScrollView) findViewById(R.id.ScrollView_comment);

        getListData(topic_id);
        setListener(topic_id);
    }

    public void setListener(final String topic_id){
        llcomment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(rlcomment.getVisibility()
                        == View.VISIBLE) {

                    // 隐藏评论框
                    rlcomment.setVisibility(View.GONE);
                    // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                    InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                }
            }
        });
        ivComent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //实现监听操作
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                Handler mHandler = new Handler();

                // 这里必须要给一个延迟，如果不加延迟则没有效果
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 将ScrollView滚动到底
                        svcomment.fullScroll(View.FOCUS_DOWN);
                    }
                }, 10);
                // 显示评论框
                rlcomment.setVisibility(View.VISIBLE);
            }
        });
        comment_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendComment(topic_id);
            }
        });
    }

    private void showTopic(){
        // 从Intent获取数据
        String name = getIntent().getStringExtra("name");
        String content = getIntent().getStringExtra("content");
        String time = getIntent().getStringExtra("time");
        String huati = getIntent().getStringExtra("huati");
        int img=getIntent().getIntExtra("img",0);
        // 获取特定的视图

        TextView textView_name = (TextView) findViewById(R.id.textView_name);
        TextView textView_content = (TextView) findViewById(R.id.textView_content);
        TextView textView_time = (TextView) findViewById(R.id.textView_time);
        TextView textView_huati = (TextView) findViewById(R.id.huati);
        ImageView imageView_img=(ImageView) findViewById(R.id.img);
        // 根据数据设置视图展现

        textView_name.setText(name);
        textView_content.setText(content);
        textView_time.setText(time);
        textView_huati.setText(huati);
        imageView_img.setImageResource(img);
    }

    private void getListData(String topic_id) {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("topic_id", topic_id);
        sendHttpPostRequest(URL_COMMENT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();

                if (response.getDataList().size() >= 0) {
                    TopicDetailActivity.CommentAdapter adapter = new TopicDetailActivity.CommentAdapter(TopicDetailActivity.this, response.getDataList());
                    lvComment.setAdapter(adapter);
                } else {
                    DialogUtil.showHintDialog(TopicDetailActivity.this, "出现错误", true);
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                LoadingDialogUtil.cancelLoading();
            }
        }, true);
    }

    static class CommentAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public CommentAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }
        public Context getContext(){return context;}
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TopicDetailActivity.CommentAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
                holder = new TopicDetailActivity.CommentAdapter.ViewHolder();
                holder.textViewtitle = (TextView) convertView.findViewById(R.id.title);
                holder.textViewinfo = (TextView) convertView.findViewById(R.id.info);
                holder.textViewtime = (TextView) convertView.findViewById(R.id.time);
                holder.img=(ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (TopicDetailActivity.CommentAdapter.ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            holder.textViewtitle.setText(map.get("user_name"));
            holder.textViewinfo.setText(map.get("comment_content"));
            holder.textViewtime.setText(TimeUtil.getTimeRange(map.get("comment_time")));
            holder.img.setImageResource(R.drawable.touxiang);


            return convertView;
        }
        /**
         * 设置监听
         */

        private static class ViewHolder {
            private TextView textViewtitle;
            private TextView textViewinfo;
            private TextView textViewtime;
            private ImageView img;
        }
    }


    public void sendComment(final String topic_id){
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            // 生成评论数据
            CommonRequest request = new CommonRequest();
            request.addRequestParam("topic_id", topic_id);
            request.addRequestParam("comment_content",comment_content.getText().toString());
            request.addRequestParam("user_id", User.user_id);  //这里要改
            sendHttpPostRequest(URL_SENDCOMMENT, request, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    LoadingDialogUtil.cancelLoading();

                    if (response.getDataList().size() >= 0) {
                        // 发送完，清空输入框
                        comment_content.setText("");

                        Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                        getListData(topic_id);
                    } else {
                        DialogUtil.showHintDialog(TopicDetailActivity.this, "出现错误", true);
                    }
                }

                @Override
                public void fail(String failCode, String failMsg) {
                    LoadingDialogUtil.cancelLoading();
                }
            }, true);

        }
    }
}
