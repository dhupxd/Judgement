package com.example.pxd.judgement;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    private String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_button= (Button) findViewById(R.id.login);
        final EditText user_name= (EditText) findViewById(R.id.user_name);
        final EditText password= (EditText) findViewById(R.id.password);
        login_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(user_name.getText().toString(), password.getText().toString());
            }
        });

    }
    //
    private void Login(String name,String password){
        NetWorkRequest.getInstance(LoginActivity.this).requestLog(name,password, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d(TAG,"网络异常");
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                if("failure".equals(response)){
                    Toast.makeText(LoginActivity.this, "密码错误或者该账户不存在", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    //Bundle bundle=new Bundle();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    //bundle.putString("today_match",response);
                    //intent.putExtras(bundle);
                    LoginActivity.this.startActivity(intent);
                }
            }
        });
    }

}
