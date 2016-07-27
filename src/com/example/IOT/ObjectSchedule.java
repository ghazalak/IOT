package com.example.IOT;

import java.util.ArrayList;

public class ObjectSchedule {
    Boolean state =false;
    String time;
    ArrayList<String> days=new ArrayList<String>();
    Boolean repeat=false;
    public ObjectSchedule(Boolean state ,String time,Boolean repeat)
    {
        this.state = state;
        this.time = time;
        this.repeat = repeat;
    }
}
