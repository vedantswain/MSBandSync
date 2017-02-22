package com.microsoft.band.sdk.sampleapp.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.SampleRate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class BackgroundBandService extends Service implements OnDataPushTaskCompleted{

    private Context context;
    private OnDataPushTaskCompleted listener;
    private BandClient client;
    private Date currentHeartDate;
    private Date currentAccelDate;
    private BandHeartRateEventListener mHeartRateEventListener;
    private BandAccelerometerEventListener mAccelerometerEventListener;
    private String outputHeartRate = "";
    private String outputAccelerometer = "";
    private int heartCounter = 0;
    private int accelCounter = 0;

    private static final String TAG="BackgroundBandService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {

        Log.d(TAG,"Service running");

        this.context = this;
        this.listener = this;

        try {
            new MonitorTask().execute();
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            this.stopSelf();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void writeFile(String sensorName, String outputData){
        File dataFile = new File(BackgroundBandService.this.getExternalFilesDir("Data"),sensorName+".csv");
        try {
            dataFile.createNewFile();
            FileOutputStream dataStream = new FileOutputStream(dataFile, true);
            dataStream.write(outputData.getBytes());
            dataStream.flush();
            dataStream.close();
        } catch (IOException e) {
            Log.d("HeartRate", e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.d("BackgroundBandService", "Service is stopping");
        try {
//            client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
            client.getSensorManager().unregisterAccelerometerEventListener(mAccelerometerEventListener);
        } catch (BandIOException ex) {
            Log.d("BandException", ex.getMessage());
        } catch (NullPointerException ex) {
            Log.d("NullPointerException", ex.getMessage());
        }

//        if (!outputHeartRate.isEmpty()) {
//            writeFile("HeartRate",outputHeartRate);
//        }
//        if (!outputAccelerometer.isEmpty()) {
//            writeFile("Accelerometer",outputAccelerometer);
//        }

        Intent resetView = new Intent();
        resetView.setAction("ResetView");
        sendBroadcast(resetView);

//        stopSelf();
    }

    @Override
    public void OnTaskCompleted(String msg, AccelObject ao) {

    }

    private class MonitorTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            Log.d("BackgroundBandService", "Monitor is working");

            Log.d(TAG,"Postion: "+Common.POSITION+"\n"+"Server: "+Common.SERVER_API);

            if (getConnectedBandClient()) {
                Log.d("BackgroundBandService", "Client is connected");


                mAccelerometerEventListener = new BandAccelerometerEventListener() {
                    @Override
                    public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
                        if (event != null) {
                            currentAccelDate = new Date();
                            accelCounter += 1;

                            float accelX = event.getAccelerationX();
                            float accelY = event.getAccelerationY();
                            float accelZ = event.getAccelerationZ();

                            Log.d(TAG, String.format(" X = %.3f , Y = %.3f , Z = %.3f", accelX,accelY,accelZ));

                            AccelObject ao = new AccelObject(Common.POSITION,accelX,accelY,accelZ, System.currentTimeMillis());
                            (new DataPushTask(context,ao,listener)).execute();
                        }
                    }
                };

                try {
                    client.getSensorManager().registerAccelerometerEventListener(mAccelerometerEventListener, SampleRate.MS16);
                } catch (BandException ex) {
                    Log.d("BandException", ex.getMessage());
                }
            } else {
                Log.d("BackgroundBandService","Stopped service");
                BackgroundBandService.this.stopSelf();
            }
            return null;
        }
    }

    private boolean getConnectedBandClient() {
        try {
            if (client == null) {
                BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
                if (devices.length == 0) {
                    Log.d("Error", "Band isn't paired with your phone.\n");
                    return false;
                }
                client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
            } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
                return true;
            }

            Log.d("Error", "Band is connecting...\n");
            return ConnectionState.CONNECTED == client.connect().await();
        } catch (InterruptedException e) {
            Log.d("Exception", e.getMessage());
        } catch (BandException e) {
            Log.d("Exception", e.getMessage());
        }

        return false;
    }
}
