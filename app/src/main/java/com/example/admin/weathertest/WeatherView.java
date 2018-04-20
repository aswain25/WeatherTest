package com.example.admin.weathertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherView extends AppCompatActivity
{
    Weather model;
    TextView tvTitle;
    ListView lvWeather;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);

        tvTitle = findViewById(R.id.tvTitle);
        lvWeather = findViewById(R.id.lvWeather);

        model = (Weather)getIntent().getSerializableExtra("model");

        tvTitle.setText("Forecast for " + model.getCity().getName());

        List<String> data = new ArrayList<>();
        for (int i = 0; i < model.getList().size(); i++)
            data.add(model.getList().get(i).toString());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lvWeather.setAdapter(adapter);
    }
}