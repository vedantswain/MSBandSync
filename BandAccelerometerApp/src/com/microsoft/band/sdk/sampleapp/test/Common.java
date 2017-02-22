package com.microsoft.band.sdk.sampleapp.test;

/**
 * Created by vedantdasswain on 19/02/17.
 */

public class Common {
    public  static  String POSITION = "Wrist";
    public  static  String SERVER_API = "http://128.61.23.159:5000";
    public  static  final String PORT = "5000";

    public static void  setServerApi(String servAddr) {
        SERVER_API = "http://" + servAddr + ":" + PORT;
    }
    public static void setPosition(String position){
        POSITION = position;
    }

}
