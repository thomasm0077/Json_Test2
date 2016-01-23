package com.example.user.json_test2;

/**
 * Created by USER on 23-01-16.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
public class JsonParser {
    public String getJSON(String url) {
        StringBuilder builder = new StringBuilder();
        String line;
        String responseMessage;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
            }
            responseMessage = builder.toString();
        } catch (Exception ex) {
            responseMessage = ex.getMessage().toString();
        }
        return responseMessage;
    }
}