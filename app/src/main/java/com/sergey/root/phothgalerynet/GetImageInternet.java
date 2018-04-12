package com.sergey.root.phothgalerynet;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetImageInternet { // Класс для запроса данных из Интернета //работа с сервисом Flickr
    private static final String TAG = "FlickrFetchr";

    private static final String API_KEY = "2cef31dfc6d9db43677a982332d345b7";

    public byte[] getUrlBytes(String urlSpec) throws IOException { //метод для запроса данных из интернета
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec)); //Преобразование ответа в строку
    }

    public ArrayList<Data> getDatas(){

        try {
            ArrayList<Data> datas = new ArrayList<>();
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONObject JObject = jsonBody.getJSONObject("photos");
            JSONArray jsonArray = JObject.getJSONArray("photo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JsonI = jsonArray.getJSONObject(i);
                Data data = new Data();
                Log.d(TAG, i + "");
                data.setCaption(JsonI.getString("title"));
                data.setId(JsonI.getString("id"));
                if (!JsonI.has("url_s")) {
                    continue;
                }
                data.setUrl(JsonI.getString("url_s"));
                datas.add(data);

            }
            return datas;
        }
         catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
