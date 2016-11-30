package com.example.pxd.judgement;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pxd.judgement.Adapter.Grid1Adapter;
import com.example.pxd.judgement.Adapter.LvTeamAdapter;
import com.example.pxd.judgement.Adapter.TableAdapter;
import com.example.pxd.judgement.CustomView.LinkedHorizontalScrollView;
import com.example.pxd.judgement.CustomView.NoScrollHorizontalScrollView;
import com.example.pxd.judgement.Object.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pxd on 2016/9/28.
 */
public class MainActivity extends Activity{
    private int[] checked=new int[500];
    NoScrollHorizontalScrollView rating;//不可滑动的scrollview
    LinkedHorizontalScrollView detail;
    ListView team;
    boolean isLeftListEnabled;
    boolean isRightListEnabled;
    List<String> team_list;
    List<Team> TL;
    List<String> list;
    List<Integer> mark;
    Button confirmButtn;
    GridView gridView;
    int list_len;
    String TAG="MainActivity";
    private boolean isClose=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_data);
        Thread thread=new startThread();
        thread.start();
    }

    private void dataUpload(String[] mat){
        String mat_name=mat[0];
        final int mat_id=Integer.parseInt(mat[1]);
        final String way=mat[2];
        if("1".equals(way)) {
            setContentView(R.layout.activity_main1);
            initView1();
            TextView title= (TextView) findViewById(R.id.title1);
            title.setText(mat_name);
            confirmButtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
                    for(int i=0;i<list_len;i++){
                        if(checked[i]==1)
                            scoreMap.put(TL.get(i).getT_id(),1);
                    }
                    NetWorkRequest.getInstance(MainActivity.this).requestRating(scoreMap,way,mat_id,new OkHttpClientManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Toast.makeText(MainActivity.this, "提交失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String response) {
                            if("success".equals(response))
                                Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "提交失败，请重新提交", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }
            });
            main1layout();
        }
        else {
            setContentView(R.layout.activity_main2);
            initView2();
            TextView title= (TextView) findViewById(R.id.title2);
            title.setText(mat_name);
            confirmButtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<Integer, Integer> scoreMap = new HashMap<Integer, Integer>();
                    for (int i = 0; i < (list_len) * (list_len); i++) {
                        if (checked[i] == 1)
                            scoreMap.put(TL.get(i / list_len).getT_id(), (i % (list_len) + 1));
                    }
                    NetWorkRequest.getInstance(MainActivity.this).requestRating(scoreMap, way, mat_id, new OkHttpClientManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            Toast.makeText(MainActivity.this, "提交失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(String response) {
                            if("success".equals(response))
                                Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "提交失败，请重新提交", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }
            });
            main2layout();
        }
    }

    private void initView1(){
        confirmButtn= (Button) findViewById(R.id.confirm);
    }
    private void initView2() {
        list=new ArrayList<>();
        mark=new ArrayList<>();
        rating= (NoScrollHorizontalScrollView) findViewById(R.id.scroll1);
        detail= (LinkedHorizontalScrollView) findViewById(R.id.scroll2);
        team= (ListView) findViewById(R.id.list_team);
        isLeftListEnabled=false;
        isRightListEnabled=false;
        team_list=new ArrayList<>();
        confirmButtn= (Button) findViewById(R.id.confirm2);
    }

    public void main1layout(){
        Grid1Adapter adapter=new Grid1Adapter(this,TL,checked);
        //获得GridView实例
        gridView= (GridView) findViewById(R.id.gridview1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView= (TextView) view.findViewById(R.id.text_item);
                if (checked[position] == 0) {
                    checked[position] = 1;
                    textView.setBackgroundColor(Color.parseColor("#CDC9A5"));
                } else {
                    checked[position] = 0;
                    textView.setBackgroundColor(Color.parseColor("#43CD80"));
                }
            }
        });
    }
    public void main2layout(){
        int rk=R.id.rating1;
        for(int i=0;i<list_len;i++) {
            team_list.add(TL.get(i).getT_name());
            TextView tmp = (TextView) findViewById(rk++);
            tmp.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < list_len; i++) {
            for (int j = 1; j <=list_len; j++) {
                list.add("");
                if (i % 2 == 0) {
                    mark.add(1);

                } else {
                    mark.add(2);
                }
            }
        }
        GridView gridView = (GridView) findViewById(R.id.gridview2);
        LvTeamAdapter lvTeamAdapter=new LvTeamAdapter(this,team_list);
        TableAdapter adapter = new TableAdapter(this, list, mark,checked,list_len);
        team.setAdapter(lvTeamAdapter);

        //获得GridView实例
        LinearLayout ll_title= (LinearLayout) findViewById(R.id.ll_title);
        ll_title.setLayoutParams(new FrameLayout.LayoutParams((list_len) * 300, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout ll_table= (LinearLayout) findViewById(R.id.ll_table);
        ll_table.setLayoutParams(new FrameLayout.LayoutParams((list_len)*300,LinearLayout.LayoutParams.MATCH_PARENT));
        gridView.setNumColumns(list_len);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idx = position % (list_len);
                TextView textView = (TextView) view.findViewById(R.id.tv_item);
                if (checked[position] == 0) {
                    textView.setText(String.valueOf(idx + 1));
                    checked[position] = 1;

                } else {
                    textView.setText("");
                    checked[position] = 0;
                }

            }
            //}
        });
        combination(team, gridView, rating, detail);
    }

    public void combination(final ListView lvTeam,final GridView gvRating,final HorizontalScrollView scRating,final LinkedHorizontalScrollView content){
        //左右滑动同步
        content.setMyScrollChangeListener(new LinkedHorizontalScrollView.LinkScrollChangeListener() {
            @Override
            public void onscroll(LinkedHorizontalScrollView view, int l, int t, int oldl, int oldt) {
                scRating.scrollTo(l,t);
            }
        });
        //上下滑动同步
        //禁止快速滑动
        lvTeam.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        gvRating.setOverScrollMode(GridView.OVER_SCROLL_NEVER);
        //左侧滚动时带动右侧滚
        lvTeam.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //这两个标志是为了避免死循环
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isRightListEnabled = false;
                    isLeftListEnabled = true;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isRightListEnabled = true;
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View child = view.getChildAt(0);
                if (child != null && isLeftListEnabled) {
                    gvRating.smoothScrollToPositionFromTop(firstVisibleItem*list_len,child.getTop());
                    //gvRating.setSelectionFromTop(firstVisibleItem, child.getTop());
                }
            }
        });
        gvRating.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isLeftListEnabled = false;
                    isRightListEnabled = true;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isLeftListEnabled = true;
                }
            }

            //firstVisibleItem是对应出现的第一个view,如果是gridview的话需要除以他的列个数才得到对应listview的行号
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View child = view.getChildAt(0);
                if (child != null && isRightListEnabled) {
                    lvTeam.setSelectionFromTop(firstVisibleItem / list_len, child.getTop());
                }
            }
        });

    }

    class startThread extends Thread{
        @Override
        public void run() {
            while(!isClose) {
                NetWorkRequest.getInstance(MainActivity.this).requestTeamAll("1", new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e(TAG, "error");
                    }

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response);
                        if ("failure".equals(response)) {
                            //Toast.makeText(MainActivity.this, "数据传送失败", Toast.LENGTH_SHORT).show();
                        }else{
                            isClose = true;
                            for (int i = 0; i < 500; i++) {
                                checked[i] = 0;
                            }
                            int i = 0;
                            String[] mat = new String[3];
                            int cnt = 0;
                            for (; i < response.length(); i++) {
                                if (response.charAt(i) == '&') {
                                    i++;
                                    break;
                                }
                                if (response.charAt(i) == '[') {
                                    i++;
                                    mat[cnt] = "";
                                    while (response.charAt(i) != ']') {
                                        mat[cnt] += response.charAt(i);
                                        i++;
                                    }
                                    Log.e(TAG, mat[cnt]);
                                    cnt++;
                                }
                            }
                            String json = response.substring(i);
                            Gson gson = new Gson();
                            TL = gson.fromJson(json, new TypeToken<List<Team>>() {
                            }.getType());
                            list_len = TL.size();
                            dataUpload(mat);
                        }

                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isClose=true;
                }
            }
        }
    }
//    public void onBackPressed(){
//        NetWorkRequest.getInstance(MainActivity.this).requestTeam("-1", new OkHttpClientManager.ResultCallback<String>() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.i(TAG, "error");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                finish();
//            }
//        });
//
//    }
    @Override
    protected void onPause() {//不在最上面一层的Activity进入睡眠
        super.onPause();
        isClose = true;
//        NetWorkRequest.getInstance(MainActivity.this).requestTeam("-1", new OkHttpClientManager.ResultCallback<String>() {
//
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.i(TAG, "error");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                finish();
//            }
//        });
    }
}
/*
public class MainActivity extends Activity implements OnItemClickListener {


    //清空列表
    public void RemoveAll() {
        list.clear();
        adapter.notifyDataSetChanged();
    }
}
*/