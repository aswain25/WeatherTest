package com.example.admin.weathertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.prefs.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NativeClient.IHandler {

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private final String CITY_ONWARDS = ",us&mode=json&appid=";
    private final String apiKey = "ccb67a7bbec4c82b2b835fd65d0c0ca1";

    EditText etCity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = findViewById(R.id.etCity);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String cityPref = sharedPref.getString("city", null);
        if (cityPref != null)
            startClient(cityPref);
    }

    public void btnShow_Clicked(View view)
    {
        startClient(etCity.getText().toString());
    }

    private void startClient(String city)
    {
        NativeClient client = new NativeClient(BASE_URL + city + CITY_ONWARDS + apiKey, this);
        client.start();
    }

    @Override
    public void onHandle(String content)
    {
        Gson gson = new Gson();
        Weather weather = (Weather)gson.fromJson(content, Weather.class);

        //Toast.makeText(this, "Saved city preferences", Toast.LENGTH_LONG);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        sharedPref.edit().putString("city", etCity.getText().toString()).commit();

        Intent intent = new Intent(getApplicationContext(), WeatherView.class);
        intent.putExtra("model", weather);
        startActivity(intent);
    }

    @Override
    public void onError()
    {
        //Toast.makeText(this, "Not a United Stated city", Toast.LENGTH_LONG);
    }
}
