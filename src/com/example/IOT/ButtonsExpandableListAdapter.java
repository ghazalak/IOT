package com.example.IOT;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.HashMap;
import java.util.List;

public class ButtonsExpandableListAdapter extends BaseExpandableListAdapter {
    String COLOR_ON = "#FF11AC06";
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;
    View view;
    public ButtonsExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData, View view) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.view = view;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.buttons_childrow, null);
        }
        Button btn= (Button)convertView.findViewById(R.id.buttons_ChildRow_button);
        btn.setWidth(500);
        new RequestTask(btn,view.getContext()).execute("http://192.168.1.1/get_status"+String.valueOf(childPosition+1));
        btn.setText(childText);
        btn.setOnClickListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            int color = Color.TRANSPARENT;
            Drawable background = view.getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();
            if (color == Color.parseColor(COLOR_ON)) {
                new RequestTask((Button)view,_context).execute("http://192.168.1.1/key"+String.valueOf(childPosition+1)+"_off");

            } else {
                new RequestTask((Button)view,_context).execute("http://192.168.1.1/key"+String.valueOf(childPosition+1)+"_on");
            }
            }
        });
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);
        ((ExpandableListView) parent).expandGroup(groupPosition);
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.buttons_parentrow, null);
            TextView lblListHeader = (TextView)convertView.findViewById(R.id.buttons_parentRow_textview);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
        }
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}