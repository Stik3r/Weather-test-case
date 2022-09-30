package com.example.weather;

import android.app.Activity;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    @Override
    public void run() {
        HttpsURLConnection conn = null;
        URL url;
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

                List<Temperature> listData = StringToListData(inline);
                DataController.SerializeData(city, listData, activity);

                hourWeather.post(() -> {
                    TempAdapter tempAdapter = new TempAdapter(activity, listData);
                    hourWeather.setAdapter(tempAdapter);
                });
            }
        } catch (Exception e) {
            activity.runOnUiThread(() -> Toast.makeText(activity, "Не удалоь подключиться к серверу. " +
                    "Загрузка данных с устройства", Toast.LENGTH_SHORT).show());
            LoadDataFromDevice();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public List<Temperature> StringToListData(String res) throws JSONException {
        JSONArray jsonHours = new JSONObject(res).getJSONObject("forecast").getJSONArray("forecastday")
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
        return listData;
    }

    private void LoadDataFromDevice(){
        List<Temperature> listData = DataController.DeserializeData(city, activity);
        if(listData == null){
            activity.runOnUiThread(() -> Toast.makeText(activity, "Данных не найдено",
                    Toast.LENGTH_SHORT).show());
            return;
        }
        hourWeather.post(() -> {
            TempAdapter tempAdapter = new TempAdapter(activity, listData);
            hourWeather.setAdapter(tempAdapter);
        });
    }
}
