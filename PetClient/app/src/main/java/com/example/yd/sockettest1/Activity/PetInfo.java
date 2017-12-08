package com.example.yd.sockettest1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.yd.sockettest1.Urls.CommonUrl.pet_info_url;

public class PetInfo extends BaseActivity {


    ListView pet_list;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        pet_list=(ListView)findViewById(R.id.pet_info_list);

        back=(ImageView)findViewById(R.id.pet_info_back);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfo.this,HomeActivity.class);
                PetInfo.this.startActivity(intent);
            }
        });
        getPetInfo();
    }

    private void getPetInfo() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("user_id", User.user_id);
        sendHttpPostRequest(pet_info_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() >= 0) {
                    PetListAdapter adapter=new PetListAdapter(PetInfo.this, response.getDataList());
                    pet_list.setAdapter(adapter);
                } else {
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Log.e("xx",failMsg);
            }
        }, true);
    }

    static class PetListAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public PetListAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.pet_info_list, parent, false);
                holder = new PetInfo.PetListAdapter.ViewHolder();
                holder.pet_name = (TextView) convertView.findViewById(R.id.pet_name);
                holder.pet_date = (TextView) convertView.findViewById(R.id.pet_date);
                holder.pet_weight = (TextView) convertView.findViewById(R.id.pet_weight);
                holder.pet_intro = (TextView) convertView.findViewById(R.id.pet_intro);
                holder.pet_img=(ImageView) convertView.findViewById(R.id.pet_img);
                holder.pet_sex=(ImageView) convertView.findViewById(R.id.pet_sex);
                convertView.setTag(holder);
            } else {
                holder = (PetInfo.PetListAdapter.ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            holder.pet_weight.setText("体重："+map.get("pet_weight"));
            holder.pet_name.setText(map.get("pet_name"));
            holder.pet_date.setText("生日："+map.get("pet_date"));
            holder.pet_intro.setText("简介： "+map.get("pet_intro"));
            holder.pet_img.setImageResource(R.drawable.dog);
            if(map.get("pet_sex").equals("GG")) {
                holder.pet_sex.setImageResource(R.drawable.gg);
            }else {
                holder.pet_sex.setImageResource(R.drawable.mm);
            }

            return convertView;
        }

        private static class ViewHolder {
            private TextView pet_name;
            private TextView pet_date;
            private TextView pet_intro;
            private TextView pet_weight;
            private ImageView pet_img;
            private ImageView pet_sex;
        }
    }

}
