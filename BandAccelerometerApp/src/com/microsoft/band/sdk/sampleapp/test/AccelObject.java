package com.microsoft.band.sdk.sampleapp.test;

/**
 * Created by vedantdasswain on 19/02/17.
 */

public class AccelObject {

    String id;
    long timestamp;
    float x,y,z;

    public AccelObject(String id, float x, float y, float z, long timestamp){
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
