package com.example.IOT.Connection;

import com.example.IOT.IDeviceConnectionListener;
import com.example.IOT.Model.ObjectDevice;
import com.example.IOT.Model.ObjectPort;

public interface IDeviceConnection {
    void SetListener(IDeviceConnectionListener listener);
    void GetStatus(long DeviceId, int count);
    void SetStatus(ObjectDevice device, ObjectPort key, boolean status);
}
