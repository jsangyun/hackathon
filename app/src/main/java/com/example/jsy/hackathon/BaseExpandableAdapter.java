package com.example.jsy.hackathon;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import arabiannight.tistroy.com.expandable.R;
/**
 * Created by hoosp on 2017-06-23.
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter{

    private ArrayList<String> groupList=null;
    private ArrayList<ArrayList<String>> childList=null;
    private LayoutInflater inflater=null;
    private ViewHolder viewHolder=null;

    public BaseExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){
        super();
        this.inflater=LayoutInflater.from(c);
        this.groupList=groupList;
        this.childList=childList;
    }

    public String getGroup(int groupPosition){
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public int getGroupCount(){
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    public long getGroupId(int group)

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v=convertView;

        if(v==null){
            viewHolder=new ViewHolder();
            v=inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.tv_group
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
