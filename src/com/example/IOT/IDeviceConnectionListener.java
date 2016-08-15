package com.example.IOT;

public interface IDeviceConnectionListener {
    void StatusChangedCallback(long deviceId,int keyIdx, boolean status);
}
