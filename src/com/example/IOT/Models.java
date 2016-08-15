package com.example.IOT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.IOT.DB.DbHelper;
import com.example.IOT.Model.*;

import java.security.acl.Group;
import java.util.ArrayList;

public class Models {
    public static ArrayList<ObjectGroup> Groups;
    private static DbHelper dbHelper;
    public static ObjectDevice getDeviceById(long deviceId) {
        for (int i = 0; i < Groups.size(); i++) {
            for (int j = 0; j < Groups.get(i).getDeviceCount(); j++) {
                if (Groups.get(i).getDevice(j).getId() == deviceId)
                    return Groups.get(i).getDevice(j);
            }
        }
        return null;
    }




    public static void Load(Context context) {

        dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<ObjectSchedule> schedules = new ArrayList<>();
        Cursor cursor = db.query("schedules",
                new String[]{"*"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int hour = cursor.getInt(cursor.getColumnIndex("hour"));
            int minute = cursor.getInt(cursor.getColumnIndex("minute"));
            long port_id = cursor.getInt(cursor.getColumnIndex("port_id"));
            long id = cursor.getInt(cursor.getColumnIndex("_id"));
            boolean status = cursor.getInt(cursor.getColumnIndex("status"))==1;
            boolean repeat = cursor.getInt(cursor.getColumnIndex("repeat"))==1;
            boolean days0 = cursor.getInt(cursor.getColumnIndex("days0"))==1;
            boolean days1 = cursor.getInt(cursor.getColumnIndex("days1"))==1;
            boolean days2 = cursor.getInt(cursor.getColumnIndex("days2"))==1;
            boolean days3 = cursor.getInt(cursor.getColumnIndex("days3"))==1;
            boolean days4 = cursor.getInt(cursor.getColumnIndex("days4"))==1;
            boolean days5 = cursor.getInt(cursor.getColumnIndex("days5"))==1;
            boolean days6 = cursor.getInt(cursor.getColumnIndex("days6"))==1;
            ObjectSchedule sched = new ObjectSchedule(id, hour, minute, status, repeat, days0, days1,
                    days2, days3, days4, days5, days6, port_id);
            schedules.add(sched);
            Log.d("Models", "Task: " + "");
        }
        cursor.close();


        ArrayList<ObjectPort> ports = new ArrayList<>();
        cursor = db.query("ports",
                new String[]{"*"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex("_id"));
            int index = cursor.getInt(cursor.getColumnIndex("idx"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long device_id = cursor.getInt(cursor.getColumnIndex("device_id"));
            boolean status = cursor.getInt(cursor.getColumnIndex("status"))==1;

            ObjectPort port = new ObjectPort(id, name, status, index, device_id);

            for(int i=0; i<schedules.size();i++){
                ObjectSchedule schedule = schedules.get(i);
                if(schedule.getPortId()==id){
                    port.AddSchedule(schedule);
                }
            }
            ports.add(port);
            Log.d("Models", "Task: " + name);
        }
        cursor.close();


        ArrayList<ObjectDevice> devices = new ArrayList<>();
        cursor = db.query("devices",
                new String[]{"*"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex("_id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long group_id = cursor.getInt(cursor.getColumnIndex("group_id"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            int portCount = cursor.getInt(cursor.getColumnIndex("portCount"));

            ObjectDevice device = new ObjectDevice(id, name, type, address,portCount, group_id);
            for(int i=0;i<ports.size();i++){
                ObjectPort port = ports.get(i);
                if(port.getDeviceId()==id){
                    port.setDevice(device);
                    device.addPort(port);
                }
            }
            devices.add(device);
            Log.d("Models", "Task: " + name);
        }
        cursor.close();

        Groups = new ArrayList<>();
        cursor = db.query("groups",
                new String[]{"*"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));

            ObjectGroup group = new ObjectGroup(id, name);
            for(int i=0; i<devices.size();i++){
                if(devices.get(i).getGroupId()==id){
                    group.addDevice(devices.get(i));
                }
            }
            Groups.add(group);
            Log.d("Models", "Task: " + name);
        }
        cursor.close();
    }
    public static void InsertGroup(String name){
        ContentValues values;
        values = new ContentValues();
        values.put("name", name);
        dbHelper.getReadableDatabase().insertOrThrow("groups", null, values);
    }
    public static void InsertDevice(String deviceName,int deviceType,int portCount, String deviceAddress, long group_id){
        ContentValues values;
        values = new ContentValues();
        values.put("name", deviceName);
        values.put("type", deviceType);
        values.put("address", deviceAddress);
        values.put("portCount",portCount);
        values.put("group_id", group_id);
        dbHelper.getWritableDatabase().insert("devices", null, values);
    }
    public static void InsertPorts(String portName, int idx, long device_id){
        ContentValues values;
        values = new ContentValues();
        values.put("name", portName);
        values.put("idx", idx);
        values.put("status", 0);
        values.put("device_id", device_id);
        dbHelper.getReadableDatabase().insertOrThrow("ports", null, values);


    }
    public static void RemoveGroup(String groupName, long groupId){
        ContentValues values;
        values = new ContentValues();
        values.put("name", groupName);
        dbHelper.getReadableDatabase().delete("groups", "name=?", new String[]{groupName});

        dbHelper.getReadableDatabase().delete("devices", "group_id=?", new String[]{Long.toString(groupId)});

    }
    public static void RemoveDevice(String deviceName, long deviceId){
        ContentValues values;
        values = new ContentValues();
        values.put("name", deviceName);
        dbHelper.getReadableDatabase().delete("devices", "name=?", new String[]{deviceName});

        dbHelper.getReadableDatabase().delete("ports", "device_id=?", new String[]{Long.toString(deviceId)});

    }
}
