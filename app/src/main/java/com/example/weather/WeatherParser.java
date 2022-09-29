package com.example.weather;

import android.app.Activity;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class WeatherParser implements Runnable{
    String api_key = "12cdbf51592a4e5a914112046221402";
    String urlData = "https://api.weatherapi.com/v1/forecast.json?key=";
    String lang = "&lang=ru";

    static public Activity activity;
    static public RecyclerView hourWeather;
    static public String city = "";

    String data = "";

    @Override
    public void run() {
        HttpsURLConnection conn = null;
        URL url = null;
        try {
            url = new URL(urlData + api_key + "&q=" + city + lang);

            conn = (HttpsURLConnection) url.openConnection();
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();
                data = inline;

                JSONArray jsonHours = new JSONObject(data).getJSONObject("forecast").getJSONArray("forecastday")
                        .getJSONObject(0).getJSONArray("hour");
                List<Temperature> listData = new ArrayList<>();
                for(int i = 0; i < jsonHours.length(); i++){
                    JSONObject hour = jsonHours.getJSONObject(i);
                    JSONObject condition = hour.getJSONObject("condition");
                    Temperature temperature = new Temperature(
                            hour.getString("time"),
                            hour.getDouble("temp_c"),
                            condition.getString("icon")
                    );
                    listData.add(temperature);
                }

                hourWeather.post(new Runnable() {
                    @Override
                    public void run() {
                        TempAdapter tempAdapter = new TempAdapter(activity, listData);
                        hourWeather.setAdapter(tempAdapter);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
