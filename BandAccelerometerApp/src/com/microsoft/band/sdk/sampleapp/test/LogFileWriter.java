package com.microsoft.band.sdk.sampleapp.test;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vedantdasswain on 28/02/17.
 */

public class LogFileWriter {
    public static File axlLog;
    public static String ACCLHEADER="position"+","+"x"+","+"y"+","+"z"+","+"timestamp";
    public static File mBandDir=new File(Environment.getExternalStorageDirectory()+File.separator+"mBandData");
    public static File SensorDataDir=new File(Environment.getExternalStorageDirectory()+File.separator+"mBandData"+File.separator+"SensorData");


    public static void PathCheck(File logFile,String header){
//		Log.v("ELSERVICES", "checking path");
        if(!mBandDir.exists()){
            try{
                mBandDir.mkdir();
                Log.v("LOG WRITER","directory made at: "+mBandDir.getPath());
            }
            catch(Exception e){
                Log.v("LOG WRITER",e.toString());
            }
        }

        if(!SensorDataDir.exists()){
            try{
                SensorDataDir.mkdir();
                Log.v("LOG WRITER","directory made at: "+SensorDataDir.getPath());
            }
            catch(Exception e){
                Log.v("LOG WRITER",e.toString());
            }
        }

        if(!logFile.exists()){
            BufferedWriter buf;
            try {
                Log.v("LOG WRITER","file created at: "+logFile.getPath());
                logFile.createNewFile();
                buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(header);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.print(e.toString());
            }
        }
    }

    public static void LogWrite(File logFile,String logstring,String header,boolean isWifi,boolean isAudio){
        synchronized(logFile){

//					Log.v("ELSERVICES", "Writing Log to: "+logFile.toString()+" "+Common.LABEL);
            PathCheck(logFile,header);

            BufferedWriter buf;
            try {
                buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(logstring);
                buf.newLine();
                buf.close();
//						Log.v("ELSERVICES", logstring+" written  into"+logFile.toString() );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.v("LOG WRITER",e.toString());
            }

        }
    }

    public static void axlLogWrite(String logstring){
        axlLog=new File(Environment.getExternalStorageDirectory()+File.separator+"mBandData"+File.separator+"SensorData"+File.separator+"accelerometer_log"+".csv");
        LogWrite(axlLog,logstring,ACCLHEADER,false,false);
    }

}
