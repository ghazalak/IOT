package com.example.IOT;

import java.util.ArrayList;

public class ObjectKey {
    String KeyName;
    ArrayList<ObjectSchedule> schedules = new ArrayList<ObjectSchedule>();

    public ObjectKey(String keyName) {
        this.KeyName = keyName;

    }

    public void AddSchedule(ObjectSchedule sched)
    {
        this.schedules.add(sched);
    }
}
