package com.example.jsy.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hoosp on 2017-06-23.
 */

public class DrinkExpandableListViewActivity extends Activity {

    private ArrayList<string> mGroupList=null;
    private ArrayList<arraylist<string>> mChildList = null;
    private ArrayList<string> mChildListContent=null;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setLayout();

        mGroupList=new ArrayList<string>();
        mChildList=new ArrayList<arraylist<string>>();
        mChildListContent=new ArrayList<string>();

        mGroupList.add();

        mChildListContent.add();

        mListView.setAdapter(new BaseExpandableListAdapter(this, mGroupList, mChildList));

        mListView.setOnGroupClickListner(new ExpandableListView.OnGroupClickListener(){
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id){
                return false;
            }
        });

        mListView.setOnChildClickListener(new onChildClickListner() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mListView.setOnGroupCollapseListner(new OnGroupCollapseListner(){
            public void onGroupCollapse(int groupPosition){

            }
        });

        mListView.setOnGroupExpandLister(new OnGroupExpandListener(){
            public void onGroupExpand(int groupPosition){

            }
        }
    })

    private ExpandableListView mListView;

    private void setLayout(){
        mListView=(ExpandableListView) findVieByld(R.id.drink_list);
    }
}
