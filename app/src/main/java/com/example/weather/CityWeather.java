package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

public class CityWeather extends AppCompatActivity {

    WeatherParser parser = new WeatherParser();
    RecyclerView hourWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        hourWeather = findViewById(R.id.hourWeather);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        hourWeather.setLayoutManager(llm);

        WeatherParser.activity = this;
        WeatherParser.hourWeather = hourWeather;
        Thread thread = new Thread(parser);
        thread.start();
    }
}