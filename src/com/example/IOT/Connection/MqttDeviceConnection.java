package com.example.IOT.Connection;

import com.example.IOT.IDeviceConnectionListener;
import com.example.IOT.Model.ObjectDevice;
import com.example.IOT.Model.ObjectPort;

/**
 * Created by ghazal on 7/30/2016.
 */
public class MqttDeviceConnection implements IDeviceConnection {

    @Override
    public void SetListener(IDeviceConnectionListener listener) {

    }

    @Override
    public void GetStatus(long DeviceId, int count) {
    }

    @Override
    public void SetStatus(ObjectDevice device, ObjectPort key, boolean status) {

    }


}
