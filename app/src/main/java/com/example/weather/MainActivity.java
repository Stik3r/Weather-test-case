package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Button get_btn;
    private Spinner list_citys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_btn = findViewById(R.id.get_btn);
        list_citys = findViewById(R.id.citys_list);
        Intent intent = new Intent(this, CityWeather.class);
        get_btn.setOnClickListener(view -> {
            WeatherParser.city = list_citys.getSelectedItem().toString();
            startActivity(intent);
        });
    }
}