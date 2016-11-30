package com.example.pxd.judgement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pxd.judgement.Object.DanceMatch;
import com.example.pxd.judgement.R;


import java.util.List;

/**
 * Created by pxd on 2016/10/14.
 */
public class TodayMatchAdapter extends BaseAdapter {

    private List<DanceMatch> list;
    private LayoutInflater mInflater;
    public TodayMatchAdapter(Context context,List<DanceMatch> list){
        this.mInflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_match_layout,null);
            holder.match_id= (TextView) convertView.findViewById(R.id.match_id);
            holder.match_name= (TextView) convertView.findViewById(R.id.match_name);
            holder.match_time= (TextView) convertView.findViewById(R.id.match_time);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.match_id.setText(String.valueOf(list.get(position).getD_id()));
        holder.match_name.setText(list.get(position).getD_name());
        holder.match_time.setText(list.get(position).getD_time().substring(11,16));
        return convertView;
    }

    public final class ViewHolder{
        public TextView match_id;
        public TextView match_name;
        public TextView match_time;
    }

}
