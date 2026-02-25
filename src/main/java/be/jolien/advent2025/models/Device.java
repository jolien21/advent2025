package be.jolien.advent2025.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Device {
    private final String name;
    private final List<Device> atachedDevices;

    public Device(String name){
        this.name = name;
        this.atachedDevices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Device> getAtachedDevices() {
        return Collections.unmodifiableList(atachedDevices);
    }

    public void addDevice(Device device){
        if(device == null){
            throw new IllegalArgumentException("device cannot be null");
        }
        atachedDevices.add(device);
    }
}
