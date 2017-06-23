package com.example.jsy.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;

/**
 * Created by hoosp on 2017-06-23.
 */

public class DrinkExpandableListViewActivity extends Activity {

    private ArrayList<String> mGroupList=null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent=null;

    ExpandableListView mListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_list);

        setLayout();

        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();

        mGroupList.add();

        mChildListContent.add("Wine");


        mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

        mListView.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        mListView.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {

            }
        });

        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {

            }
        });
    }

    private void setLayout(){
        mListView=(ExpandableListView) findViewById(R.id.);
    }
}
