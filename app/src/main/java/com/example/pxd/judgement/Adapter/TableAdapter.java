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

import com.example.pxd.judgement.R;

import java.util.List;

/**
 * Created by pxd on 2016/10/16.
 */
public class TableAdapter extends BaseAdapter {
    List<String> list;
    List<Integer> booleans;
    int checked[];
    int list_len;
    private LayoutInflater mInflater;
    public TableAdapter(Context context,List<String> list,List<Integer> booleans,int checked[],int list_len){
        this.mInflater=LayoutInflater.from(context);
        this.list=list;
        this.booleans=booleans;
        this.checked=checked;
        this.list_len=list_len;
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
            convertView = mInflater.inflate(R.layout.item_table, null);
            holder = new ViewHolder();
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.i("Adapter", list.get(position));
        holder.tv_item.setText(list.get(position));
        int idx = position % (list_len);
        if (checked[position] == 1) {
            holder.tv_item.setText(String.valueOf(idx + 1));
        } else {
            holder.tv_item.setText("");
        }
        if (booleans.get(position) == 1) {
            //奇数行颜色
            holder.tv_item.setBackgroundColor(Color.parseColor("#383838"));
            holder.ll_item.setBackgroundColor(Color.parseColor("#dadada"));
        } else {
            //偶数行颜色
            holder.tv_item.setBackgroundColor(Color.parseColor("#5CACEE"));
            holder.ll_item.setBackgroundColor(Color.parseColor("#dadada"));
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_item;
        private LinearLayout ll_item;
    }
}
