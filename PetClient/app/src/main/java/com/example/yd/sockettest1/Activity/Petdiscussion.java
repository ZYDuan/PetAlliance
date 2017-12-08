package com.example.yd.sockettest1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.LoadingDialogUtil;
import com.example.yd.sockettest1.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.URL_TOPIC;

public class Petdiscussion extends BaseActivity {

    ListView lvTopic;
    ImageView ivTopic,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petdiscussion);
        lvTopic = (ListView) findViewById(R.id.topic_listView);
        ivTopic = (ImageView) findViewById(R.id.title_imv);//+ 评论
        back=(ImageView)findViewById(R.id.dicuss_back);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Petdiscussion.this,HomeActivity.class);
                Petdiscussion.this.startActivity(intent);
            }
        });
        getListData();

        ivTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现监听操作
                Intent intent = new Intent(Petdiscussion.this, SendTopicActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getListData() {
        CommonRequest request = new CommonRequest();
        sendHttpPostRequest(URL_TOPIC, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();

                if (response.getDataList().size() > 0) {
                    TopicAdapter adapter = new TopicAdapter(Petdiscussion.this, response.getDataList());
                    lvTopic.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                LoadingDialogUtil.cancelLoading();
            }
        }, true);
    }

    static class TopicAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public TopicAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
            Collections.reverse(list);//反转list
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

            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.topic_item, parent, false);
                holder = new ViewHolder();
                holder.textViewname = (TextView) convertView.findViewById(R.id.textView_name);
                holder.textViewcontent = (TextView) convertView.findViewById(R.id.textView_content);
                holder.textViewtime = (TextView) convertView.findViewById(R.id.textView_time);
                holder.huati = (TextView) convertView.findViewById(R.id.huati);
                holder.img=(ImageView) convertView.findViewById(R.id.img);
                holder.textViewtopicid= (TextView) convertView.findViewById(R.id.textView_topicid);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            HashMap<String, String> map = list.get(position);

            holder.textViewname.setText(map.get("topic_name"));
            holder.textViewcontent.setText(map.get("topic_content"));
            holder.textViewtime.setText(TimeUtil.getTimeRange(map.get("topic_time")));
            holder.huati.setText(map.get("user_name"));
            holder.img.setImageResource(R.drawable.touxiang);
            holder.textViewtopicid.setText(map.get("topic_id"));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  初始化一个准备跳转到TopicDetailActivity的Intent
                    Intent intent = new Intent(getContext(), TopicDetailActivity.class);
                    // 准备跳转
                    intent.putExtra("name",holder.textViewname.getText());
                    intent.putExtra("content",holder.textViewcontent.getText());
                    intent.putExtra("time",holder.textViewtime.getText());
                    intent.putExtra("huati",holder.huati.getText());
                    intent.putExtra("img",R.drawable.touxiang);
                    intent.putExtra("topic_id",holder.textViewtopicid.getText());
                    getContext().startActivity(intent);
                }
            });
            return convertView;
        }

        private static class ViewHolder {
            private TextView textViewname;
            private TextView textViewcontent;
            private TextView textViewtime;
            private TextView huati;
            private ImageView img;
            private TextView textViewtopicid;
        }
    }
    }
