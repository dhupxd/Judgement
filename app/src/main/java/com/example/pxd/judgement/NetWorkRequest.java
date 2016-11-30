package com.example.pxd.judgement;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by pxd on 2016/9/28.
 */
public class NetWorkRequest {
    private static NetWorkRequest mInstance;
    public  static NetWorkRequest getInstance(Context context){
        if(mInstance==null){
            mInstance=new NetWorkRequest();
        }
        WifiIP(context);
        return mInstance;
    }
    private static String base_path;
    private static String login_path;
    private static String rating_path;
    private static String match_path;
    public static void WifiIP(Context context){//获取路由器ip
        NetworkInfo  info= ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            if(info.getType()==ConnectivityManager.TYPE_WIFI){
                WifiManager wifiManager= (WifiManager) context.getSystemService((Context.WIFI_SERVICE));
                WifiInfo wifiInfo=wifiManager.getConnectionInfo();
                String ipAddress =intIP2StringIP(wifiInfo.getIpAddress());
                Log.e("Wifi",ipAddress);
                base_path="http://"+ipAddress+":8080/HelloWeb/servlet/";

            }else{
                Log.e("Wifi","nowifi");
                base_path="http://10.202.113.92:8080/HelloWeb/servlet/";
                Log.e("Wifi",base_path);
            }
        }else{
            Log.e("Wifi","error");
            base_path="http://10.202.113.92:8080/HelloWeb/servlet/";
        }
        login_path=base_path+"LoginServlet";
        rating_path=base_path+"RatingServlet";
        match_path=base_path+"getTeamServlet";
    }
    public static String intIP2StringIP(int ip){
        return (ip&0xFF)+"."+((ip>>8)&0xFF)+"."+((ip>>16)&0xFF)+"."+1;
    }
    //登陆网络请求
    public void requestLog(String name, String pwd, OkHttpClientManager.ResultCallback<String> callback) {
        Map<String,String> params=new HashMap<String,String>();
        params.put("username",name);
        params.put("password",pwd);
        OkHttpClientManager.postAsyn(login_path, callback,params);
    }
    //评分网络请求
    public void requestRating(Map<Integer,Integer> score,String way,int mat_id,OkHttpClientManager.ResultCallback<String> callback) {
        String json=mapToJson(score);
        Map<String,String> params=new HashMap<String,String>();
        params.put("Json",json);
        params.put("way",way);
        params.put("mat_id",String.valueOf(mat_id));
        Log.e("Net", params.get("Json"));
        OkHttpClientManager.postAsyn(rating_path, callback,params);
    }
    //队伍信息网络请求
    public void requestTeam(String mat_id,OkHttpClientManager.ResultCallback<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("mat_id", mat_id);
        OkHttpClientManager.postAsyn(match_path,callback,params);
    }
    public void requestTeamAll(String isOn,OkHttpClientManager.ResultCallback<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("isOn", isOn);
        OkHttpClientManager.postAsyn(match_path,callback,params);
    }
    public String mapToJson(Map<Integer,Integer> score){
        StringBuilder json=new StringBuilder();
        json.append("{");
        Iterator<Map.Entry<Integer,Integer>> entries=score.entrySet().iterator();
        if(!entries.hasNext()) {
            json.append("}");
        }
        while(entries.hasNext()){
            Map.Entry<Integer,Integer> entry=entries.next();
            json.append("{");
            json.append(entry.getKey());
            json.append(":");
            json.append(entry.getValue());
            json.append("}");
            if(!entries.hasNext()){
                json.append("}");
                break;
            }
            json.append(",");
        }
        return json.toString();

    }
}
