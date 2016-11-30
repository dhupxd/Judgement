package com.example.pxd.judgement;

import com.google.gson.Gson;

/**
 * Created by pxd on 2016/9/28.
 */
public class Result {
    private String data;
    private String resultState;
    public String getResultState(){
        return resultState;
    }

    public void setResultState(String resultState){
        this.resultState=resultState;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString(Result result){
        String json=new Gson().toJson(result);
        return json;
    }
}
