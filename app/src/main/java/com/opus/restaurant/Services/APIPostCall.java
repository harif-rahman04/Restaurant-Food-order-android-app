package com.opus.restaurant.Services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class APIPostCall extends AsyncTask<String, String, String> {
    private String url;
    private APICallback callback;
  //  private ArrayList<KeyValue> params;
    private HashMap<String, String> params;

    public APIPostCall(String url, APICallback callback, HashMap<String, String> params) {
        this.url = url;
        this.callback = callback;
        this.params = params;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... strings) {
        String contentAsString = null;
        try {
            HttpUtility utility = new HttpUtility(this.url);

            Iterator i = params.keySet().iterator();
            while (i.hasNext()) {

                String key = (String) i.next();
                String value = this.params.get(key);
                utility.addFormField(key, value);
                Log.d("Result", key +","+value);
            }
            contentAsString = utility.finish();
        } catch (Exception e) {
            Log.e("ERROR", "" + e.getMessage());
        }
        return contentAsString;
    }

    private static String readIt(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.callback.onPostExecute(s);
    }
}
