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
import com.example.yd.sockettest1.common.User;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.chat_list_url;

public class ChatList extends BaseActivity {

    private ListView chat_lv;
    public static String to_user_id;

    public static String user_name;

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chat_lv = (ListView) findViewById(R.id.chat_list);
        back=(ImageView)findViewById(R.id.chat_list_back);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatList.this,HomeActivity.class);
                ChatList.this.startActivity(intent);
            }
        });
        try {
            getListData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public void getListData() throws UnsupportedEncodingException {
        CommonRequest request = new CommonRequest();

        request.addRequestParam("user_id",User.user_id);

        sendHttpPostRequest(chat_list_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                if (response.getDataList().size() > 0) {
                    ChatListAdapter adapter = new ChatListAdapter(ChatList.this, response.getDataList());
                    chat_lv.setAdapter(adapter);
                }else{
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                LogUtil.logErr(failMsg);
            }


        }, true);
    }

    static class ChatListAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public ChatListAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        public Context getContext() {
            return context;
        }

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
                convertView = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
                holder = new ViewHolder();
                holder.textViewcontent = (TextView) convertView.findViewById(R.id.textView_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            String str = "来自 " + map.get("user_name") + " 于  " + map.get("chat_time") + "  发来的消息： " + map.get("chat_content");


            holder.textViewcontent.setText(str);

            holder.textViewcontent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> map = list.get(position);
                    user_name=map.get("user_name");
                    to_user_id=map.get("user_id");
                    //  初始化一个准备跳转到TopicDetailActivity的Intent
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    // 准备跳转
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("to_user_id",to_user_id);
                    getContext().startActivity(intent);
                }
            });
            return convertView;
        }

        private static class ViewHolder {
            private TextView textViewcontent;
        }
    }




}
