package com.example.android.foodopdowner.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.Attributes;

public class BackgroundTask extends AsyncTask<String, String, String> {
    Context context;

    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String fstname = strings[1];
        String middlename = strings[2];
        String lastname = strings[3];
        String mobile = strings[4];
        String mail = strings[5];
        String regurl = "http://127.0.0.1/Exmaple/abc.php";
        if (type.equals("reg")) {
            try {
                URL url = new URL(regurl);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insertdata = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(fstname, "UTF-8") +
                            "&" + URLEncoder.encode("MiddelName", "UTF-8") + "=" + URLEncoder.encode(middlename, "UTF-8") +
                            "&" + URLEncoder.encode("LastName", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8") +
                            "&" + URLEncoder.encode("Mobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8") +
                            "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
                    bufferedWriter.write(insertdata);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";

                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");

                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(String s) {
        Toast.makeText(context, "s", Toast.LENGTH_SHORT).show();
        //super.onPostExecute(s);
    }
}
