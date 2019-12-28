package com.andre.projjoe.imageManipulation;

import android.os.AsyncTask;

/**
 * Created by andreinsigne on 06/11/2017.
 */

public class AIAsync extends AsyncTask<String,Integer,String> {

    private String url;
    private AsyncInterface asyncInterface;
    public AIAsync(String url1, AsyncInterface asyncInterface1)
    {
        url = url1;
        asyncInterface = asyncInterface1;
    }

    public AIAsync(AsyncInterface asyncInterface1)
    {
        asyncInterface = asyncInterface1;
    }

    @Override
    protected String doInBackground(String... strings) {
        return asyncInterface.onBackground();
    }

    @Override
    protected void onPostExecute(String result) {
        asyncInterface.onUpdated(result);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }
}
