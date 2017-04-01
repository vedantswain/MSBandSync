package com.microsoft.band.sdk.sampleapp.test;

/**
 * Created by vedantdasswain on 01/04/17.
 */

public class GyroObject {

    public String sensorType="gyro";

    String id;
    long timestamp;
    float x,y,z;

    public GyroObject(String id, float x, float y, float z, long timestamp){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    public String getId(){
        return id;
    }
    public long getTimestamp(){
        return timestamp;
    }
}
