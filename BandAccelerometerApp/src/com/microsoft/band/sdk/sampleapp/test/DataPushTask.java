package com.microsoft.band.sdk.sampleapp.test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by vedantdasswain on 19/02/17.
 */

public class DataPushTask extends AsyncTask<Void,Void,String> {

    Context context;
    JSONArray jsonArray;
    OnDataPushTaskCompleted listener;
    Boolean isRunning = true;

    private static String TAG = "DataPushTask";


    public DataPushTask(Context context, JSONArray jsonArray, OnDataPushTaskCompleted listener){
        this.context = context;
        this.jsonArray = jsonArray;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        String msg = "";
        msg = postData();
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
//        Log.i(TAG, msg);
        listener.OnTaskCompleted(msg);
        if (msg.equals("Some error")){
            cancel(true);
            isRunning=false;
        }
    }

    private String postData(){
        if(isRunning)
            try {
                String msg = "";
                HttpClient httpClient = new DefaultHttpClient();
                String url = Common.SERVER_API + "/data";
                HttpPost httpPost = new HttpPost(url);
                JSONObject jsonObject = new JSONObject();
                StringEntity se;


                jsonObject.put("data",jsonArray);

                se = new StringEntity(jsonObject.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));

                httpPost.setEntity(se);
                HttpResponse response = httpClient.execute(httpPost);

                Log.d(TAG, response.getStatusLine().toString());
                String responseBody= EntityUtils.toString(response.getEntity());
    //            Log.d(TAG,responseBody );
                return response.getStatusLine().toString();
            }
            catch (JSONException | IOException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        return "Some error";
    }
}
