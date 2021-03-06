package com.example.IOT.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.IOT.Model.ObjectDevice;
import com.example.IOT.Model.ObjectGroup;
import com.example.IOT.Models;
import com.example.IOT.R;

import java.util.ArrayList;
import java.util.List;

public class SettingExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    public SettingExpandableListAdapter(Context context) {
        this._context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Models.Groups.get(groupPosition).getDevice(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ObjectDevice device = Models.Groups.get(groupPosition).getDevice(childPosition);
        //if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.setting_childrow, null);
        //}
        ImageView image=(ImageView) convertView.findViewById(R.id.typeImage);
        if(device.getTypeDevice()==1)
            image.setBackgroundResource(R.drawable.key_off);
        else
            image.setBackgroundResource(R.drawable.socket_off);

        TextView DevicePorts=(TextView)convertView.findViewById(R.id.DevicePorts);
        DevicePorts.setText("("+device.getPortsCount().toString()+")");

        TextView txtListChild = (TextView) convertView.findViewById(R.id.setting_keyname);
        txtListChild.setText(device.getName());

        Button btn2 = (Button) convertView.findViewById(R.id.setting_removeBttton);
        btn2.setTag((ObjectDevice)getChild(groupPosition, childPosition));
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(_context);
                dlgAlert.setMessage("آیا از پاک کردن این کلید اطمینان دارید؟");
                dlgAlert.setCancelable(false).setPositiveButton("تایید",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ObjectDevice keyTag = (ObjectDevice) arg0.getTag();
                                Models.RemoveDevice(keyTag.getId());
                                Models.Load(_context);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("بازگشت",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = dlgAlert.create();
                alertDialog.show();
            }
        });
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
    public int getGroupCount() {
        return Models.Groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    int getEditTextById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.port1Name;
                break;
            case 1:
                buttonId = R.id.port2Name;
                break;
            case 2:
                buttonId = R.id.port3Name;
                break;
            case 3:
                buttonId = R.id.port4Name;
                break;
        }
        return buttonId;
    }
    int getTextViewById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.port1;
                break;
            case 1:
                buttonId = R.id.port2;
                break;
            case 2:
                buttonId = R.id.port3;
                break;
            case 3:
                buttonId = R.id.port4;
                break;
        }
        return buttonId;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.setting_parentrow, null);
        //}
        String headerTitle = ((ObjectGroup) getGroup(groupPosition)).getName();
        final TextView lblListHeader = (TextView) convertView.findViewById(R.id.setting_groupname);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        Button btn2 = (Button) convertView.findViewById(R.id.setting_removegroup);
        btn2.setTag((ObjectGroup) getGroup(groupPosition));
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(_context);

                dlgAlert.setMessage("آیا از پاک کردن این گروه اطمینان دارید؟");

                dlgAlert.setCancelable(false).setPositiveButton("تایید",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ObjectGroup keyTag = (ObjectGroup) arg0.getTag();
                                Models.RemoveGroup(keyTag.getId());
                                Models.Load(_context);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("بازگشت",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = dlgAlert.create();
                alertDialog.show();
            }
        });
        Button addButton = (Button) convertView.findViewById(R.id.setting_addgroup);
        addButton.setFocusable(false);
        addButton.setTag((ObjectGroup) getGroup(groupPosition));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                LayoutInflater li = LayoutInflater.from(_context);
                View promptsView = li.inflate(R.layout.setting_adddevice_dialog, null);
                final Spinner spinnerType = (Spinner) promptsView.findViewById(R.id.deviceType);
                final Spinner spinnerCount = (Spinner) promptsView.findViewById(R.id.deviceCount);

                ArrayAdapter<String> adapter;
                List<String> list;

                list = new ArrayList<>();
                list.add("کلید");
                list.add("پریز");
                list.add("RGB LED");
                adapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapter);

                list = new ArrayList<>();
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                adapter = new ArrayAdapter<>(_context, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCount.setAdapter(adapter);

                final EditText deviceName = (EditText) promptsView.findViewById(R.id.deviceName);
                final EditText deviceAddress = (EditText) promptsView.findViewById(R.id.deviceAddress);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setCancelable(false).setPositiveButton("ادامه",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final int deviceType;
                                final ObjectGroup groupTag = (ObjectGroup) arg0.getTag();
                                if (spinnerType.getSelectedItem().toString() == "کلید")
                                    deviceType = 1;
                                else
                                    deviceType = 2;
                                LayoutInflater li = LayoutInflater.from(_context);
                                final View promptsView = li.inflate(R.layout.setting_addports_dialog, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
                                alertDialogBuilder.setView(promptsView);
                                for (int j = spinnerCount.getSelectedItemPosition() + 1; j < Models.MAXIMUM_PORT_COUNT; j++) {
                                    promptsView.findViewById(getEditTextById(j)).setVisibility(View.GONE);
                                    promptsView.findViewById(getTextViewById(j)).setVisibility(View.GONE);
                                }
                                alertDialogBuilder.setCancelable(false).setPositiveButton("تایید",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
//                                                    ObjectDevice keyTag = (ObjectDevice) arg0.getTag();
                                                long deviceId = Models.InsertDevice(deviceName.getText().toString(),
                                                        deviceType,
                                                        spinnerCount.getSelectedItemPosition() + 1,
                                                        deviceAddress.getText().toString(),
                                                        groupTag.getId());

                                                for (int j = 0; j < spinnerCount.getSelectedItemPosition() + 1; j++) {
                                                    EditText t = (EditText) promptsView.findViewById(getEditTextById(j));
                                                    Models.InsertPorts(t.getText().toString(), j, deviceId);
                                                }
                                                Models.Load(_context);
                                                notifyDataSetChanged();

                                            }
                                        })
                                        .create().show();
                            }
                        })
                        .setNegativeButton("بازگشت",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

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
