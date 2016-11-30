package com.example.pxd.judgement.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pxd.judgement.Object.Team;
import com.example.pxd.judgement.R;

import java.util.List;

/**
 * Created by pxd on 2016/10/30.
 */
public class Grid1Adapter extends BaseAdapter {
    List<Team> list;
    int checked[];
    private LayoutInflater mInflater;
    public Grid1Adapter(Context context,List<Team> list,int checked[]){
        this.mInflater=LayoutInflater.from(context);
        this.list=list;
        this.checked=checked;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            holder = new ViewHolder();
            holder.tv_item = (TextView) convertView.findViewById(R.id.text_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item.setText(String.valueOf(list.get(position).getT_id()));
        if (checked[position] == 1) {
            holder.tv_item.setBackgroundColor(Color.parseColor("#CDC9A5"));
        } else {
            holder.tv_item.setBackgroundColor(Color.parseColor("#43CD80"));
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_item;
    }
}
