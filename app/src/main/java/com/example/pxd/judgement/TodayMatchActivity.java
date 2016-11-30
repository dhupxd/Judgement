package com.example.pxd.judgement;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pxd.judgement.Adapter.TodayMatchAdapter;
import com.example.pxd.judgement.Object.DanceMatch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class TodayMatchActivity extends Activity {

    private String TAG="TodayMatchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_match);
        Intent intent=TodayMatchActivity.this.getIntent();
        Bundle bundle=intent.getExtras();
        String json=bundle.getString("today_match");
        Log.i(TAG,json);
        if("None".equals(json)){
            TextView textView= (TextView) findViewById(R.id.today_match_item);
            textView.setText("今日没有比赛！");
            return;
        }
        final Gson gson=new Gson();
        final List<DanceMatch> DL= gson.fromJson(json,new TypeToken<List<DanceMatch>>(){}.getType());
        ListView listView= (ListView) findViewById(R.id.today_match);
        TodayMatchAdapter adapter=new TodayMatchAdapter(this,DL);
        listView.setAdapter(adapter);
        //ListView的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView id_tv = (TextView) view.findViewById(R.id.match_id);
                TimeZone.setDefault(TimeZone.getDefault().getTimeZone("GMT+8"));
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = format.format(date);
                String time = dateString.substring(0, 16);
                String db_time="";
                String tmp=DL.get(position).getD_time();
                for(int i=0;i<tmp.length();i++){
                    if(tmp.charAt(i)=='T'){
                        db_time+=' ';
                    }else{
                        db_time+=tmp.charAt(i);
                    }
                }
                if (time.compareTo(db_time) < 0) {
                    Toast.makeText(TodayMatchActivity.this, "比赛还未开始", Toast.LENGTH_LONG).show();
                } else {
                    NetWorkRequest.getInstance(TodayMatchActivity.this).requestTeam(id_tv.getText().toString(), new OkHttpClientManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Log.e(TAG, "error");
                        }

                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, response);
                            if ("failure".equals(response)) {
                                Toast.makeText(TodayMatchActivity.this, "人数已满", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                intent.setClass(TodayMatchActivity.this, MainActivity.class);
                                bundle.putString("team", response);
                                bundle.putString("mat_name", DL.get(position).getD_name());
                                bundle.putString("way", DL.get(position).getD_way());
                                bundle.putInt("mat_id", DL.get(position).getD_id());
                                intent.putExtras(bundle);
                                TodayMatchActivity.this.startActivity(intent, bundle);
                            }
                        }
                    });
                }
            }
        });
    }



}
