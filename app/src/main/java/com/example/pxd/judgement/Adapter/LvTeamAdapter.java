package com.example.pxd.judgement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pxd.judgement.R;

import java.util.List;

/**
 * Created by pxd on 2016/10/25.
 */
public class LvTeamAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public LvTeamAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount()  {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_lv_team_name,null);
            holder.tv_teamname= (TextView) convertView.findViewById(R.id.tv_team);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_teamname.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView tv_teamname;
    }
}
