package com.example.yd.sockettest1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import static com.example.yd.sockettest1.Urls.CommonUrl.order_url;

public class OrderList extends BaseActivity {
    ListView lvOrder;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        lvOrder = (ListView) findViewById(R.id.lv_order);
        back=(ImageView)findViewById(R.id.order_back);

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderList.this,HomeActivity.class);
                OrderList.this.startActivity(intent);
            }
        });
        getListData();
    }
    private void getListData() {
        CommonRequest request = new CommonRequest();

        request.addRequestParam("user_id", User.user_id);

        sendHttpPostRequest(order_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                if (response.getDataList().size() > 0) {

                    OrderList.OrderAdapter adapter = new OrderList.OrderAdapter(OrderList.this, response.getDataList());
                    lvOrder.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void fail(String failCode, String failMsg) {

            }
        }, true);
    }

    static class OrderAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public OrderAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.order_list, parent, false);
                holder = new  ViewHolder();
                holder.textViewname = (TextView) convertView.findViewById(R.id.TextView_name);
                holder.textViewphone = (TextView) convertView.findViewById(R.id.TextView_phone);
                holder.textViewendtime = (TextView) convertView.findViewById(R.id.TextView_time);
                holder.textViewtype = (TextView) convertView.findViewById(R.id.TextView_pettype);
                holder.textViewstarttime= (TextView) convertView.findViewById(R.id.textView_starttime);
                holder.bt= (Button) convertView.findViewById(R.id.button);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            HashMap<String, String> map = list.get(position);

            holder.textViewname.setText(map.get("user_name"));
            holder.textViewphone.setText(map.get("user_phone"));
            holder.textViewendtime.setText(map.get("end_time"));
            holder.textViewtype.setText(map.get("pet_type"));
            holder.textViewstarttime.setText(map.get("start_time"));
            holder.bt.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(),EvaFeed.class);
                    getContext().startActivity(intent);
                }
            });
            return convertView;
        }

        private static class ViewHolder {
            private TextView textViewname;
            private TextView textViewphone;
            private TextView textViewtype;
            private TextView textViewendtime;
            private TextView textViewstarttime;
            private Button bt;
        }
    }
}

