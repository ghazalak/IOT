package com.example.IOT.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.IOT.Connection.DeviceConnectionFactory;
import com.example.IOT.Model.ObjectDevice;
import com.example.IOT.Model.ObjectGroup;
import com.example.IOT.Model.ObjectPort;
import com.example.IOT.Models;
import com.example.IOT.IDeviceConnectionListener;
import com.example.IOT.R;

public class ButtonsExpandableListAdapter extends BaseExpandableListAdapter implements IDeviceConnectionListener {
    private Context _context;
    View view;
    public ButtonsExpandableListAdapter(Context context, View view) {
        this._context = context;
        this.view = view;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Models.Groups.get(groupPosition).getDevice(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    int getButtonById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.buttons_Child1Row_button;
                break;
            case 1:
                buttonId = R.id.buttons_Child2Row_button;
                break;
            case 2:
                buttonId = R.id.buttons_Child3Row_button;
                break;
            case 3:
                buttonId = R.id.buttons_Child4Row_button;
                break;
        }
        return buttonId;
    }
    int getTextById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.buttons_Child1Row_text;
                break;
            case 1:
                buttonId = R.id.buttons_Child2Row_text;
                break;
            case 2:
                buttonId = R.id.buttons_Child3Row_text;
                break;
            case 3:
                buttonId = R.id.buttons_Child4Row_text;
                break;
        }
        return buttonId;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ObjectDevice device = Models.Groups.get(groupPosition).getDevice(childPosition);
        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(device.getCount()==4)
            convertView = infalInflater.inflate(R.layout.buttons_4childrow, null);
        else if(device.getCount()==3)
            convertView = infalInflater.inflate(R.layout.buttons_3childrow, null);
        else if(device.getCount()==2)
            convertView = infalInflater.inflate(R.layout.buttons_2childrow, null);
        else if(device.getCount()==1)
            convertView = infalInflater.inflate(R.layout.buttons_1childrow, null);
        int i;
        for (i=0; i < device.getCount();i++){
            final ObjectPort key = device.getKey(i);
            final Button btn= (Button)convertView.findViewById(getButtonById(i));
            final TextView text=(TextView)convertView.findViewById(getTextById(i));
            btn.setTag(key);
//            btn.setText(key.getName());
            text.setText(key.getName());
            btn.setOnClickListener(null);
            final IDeviceConnectionListener thisListener = this;

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ObjectPort keyTag = (ObjectPort) view.getTag();
                    DeviceConnectionFactory.GetDeviceConnection(thisListener).SetStatus(keyTag.getDevice(), keyTag, !keyTag.getStatus());
                }
            });
            if(key.getStatus() == true && device.getTypeDevice()==2)
                btn.setBackgroundResource(R.drawable.socket_on);
//                btn.setBackgroundColor( Color.parseColor("#FF11AC06"));
            else if(key.getStatus()==true && device.getTypeDevice()==1)
                btn.setBackgroundResource(R.drawable.key_on);
            else if(key.getStatus()==false && device.getTypeDevice()==1)
                btn.setBackgroundResource(R.drawable.key_off);
            else
                btn.setBackgroundResource(R.drawable.socket_off);
//            btn.setBackgroundColor(Color.parseColor("#FF333333"));
        }

//        for(int j=i; j<4;j++){
//            convertView.findViewById(getButtonById(j)).setVisibility(View.GONE);
//        }
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return Models.Groups.get(groupPosition).getDeviceCount();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return Models.Groups.get(groupPosition);
    }
    @Override
    public int getGroupCount() { return Models.Groups.size(); }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        ExpandableListView eLV = (ExpandableListView) parent;
//        eLV.expandGroup(groupPosition);
//        ((ExpandableListView) parent).expandGroup(groupPosition);
        String headerTitle = ((ObjectGroup) getGroup(groupPosition)).getName();
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

    @Override
    public void StatusChangedCallback(long  deviceId,int keyIdx, boolean status) {
        Models.getDeviceById(deviceId).getKeyByIndex(keyIdx).setStatus(status);
        notifyDataSetChanged();
    }
}