package com.example.IOT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.util.*;
import android.widget.*;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ButtonsActivity extends Activity {
    ButtonsExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectKey key = new ObjectKey("key 1");
        key.AddSchedule(new ObjectSchedule(true, "7:00", true));

        setContentView(R.layout.buttons_background_layout);
        expListView = (ExpandableListView) findViewById(R.id.buttons_explv);
        prepareListData();
        listAdapter = new ButtonsExpandableListAdapter(this, listDataHeader, listDataChild, expListView);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {return false;}
        });
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {}
        });
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {}
        });
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader.add("اتاق خواب");
//        listDataHeader.add("آشپزخانه");
//        listDataHeader.add("توالت");

        List<String> bedroom = new ArrayList<String>();
//        bedroom.add("کلید ۱");
//        bedroom.add("کلید ۲");

//        List<String> kitchen = new ArrayList<String>();
//        kitchen.add("کلید ۱");
//        kitchen.add("کلید ۲");
//        kitchen.add("کلید ۳");
//
//        List<String> toilet = new ArrayList<String>();
//        toilet.add("کلید ۱");
        ObjectKey key = new ObjectKey("کلید ۱ ");
        bedroom.add(key.KeyName);
        key.AddSchedule(new ObjectSchedule(true, "07:00", true));
        key = new ObjectKey("کلید ۲");
        bedroom.add(key.KeyName);
        key.AddSchedule(new ObjectSchedule(true, "08:30", true));
        listDataChild.put(listDataHeader.get(0), bedroom); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), kitchen);
//        listDataChild.put(listDataHeader.get(2), toilet);
    }
    public void buttons_BarnameRiziClickHandler(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}