package com.example.IOT;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class RequestTask extends AsyncTask<String, String, String> {
    Button btn = null;
    Context context;
    Exception error;
    public RequestTask(Button btn, Context context){
        this.btn = btn;
            this.context=context;
    }
    String COLOR_ON = "#FF11AC06";
    String COLOR_OFF = "#FF333333";
//    @Override
//    protected void onPreExecute(){
//
//    }
    @Override
    protected String doInBackground(String[] uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                Toast.makeText(context,"error", Toast.LENGTH_SHORT).show();
                //btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            cancel(true);
            error = e;
            //TODO Handle problems..
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
            //btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
        } catch (Exception e) {
            error = e;
            cancel(true);
            //TODO Handle problems..
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
            //btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
        }
        return responseString;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (Integer.valueOf(result) == 1) {
            btn.setBackgroundColor(Color.parseColor(COLOR_ON));
        }else if(Integer.valueOf(result) == 0){
            btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
        }else{
            if (error != null) {
                Toast.makeText(context, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
            //btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
        }
        //delegate.processFinish(result);
        //Do anything with response..
    }
    @Override
    protected void onCancelled(){
        super.onCancelled();
        Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
        //btn.setBackgroundColor(Color.parseColor(COLOR_OFF));
    }
}