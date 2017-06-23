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
    private Layoutinflater inlater=null;
    private ViewHolder viewHolder=null;

    public BaseExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){
        super();
        this.inflater=LayoutInflater.from(c);
        this.groupList=groupList;
        this.childList=childList;
    }

    public String getGroup(int groupPostion){
        return groupList.get(groupPostion);
    }

    public int getGroupCount(){
        return groupList.size();
    }

    public long getGroupId(int group)
}
