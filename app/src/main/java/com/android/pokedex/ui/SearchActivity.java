package com.android.pokedex.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pokedex.R;

public class SearchActivity extends AppCompatActivity {
    // UI
    private TextView tvCity, tvTemperature, tvWeather, tvHumidity, tvMaxT, tvMinT, tvPressure, tvWind;
    private ImageView imgIcon;
    private EditText etSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // initViews
        init();
    }

    private void init() {
        tvCity = findViewById(R.id.tv_city);
        tvTemperature = findViewById(R.id.tv_temp);
        tvWeather = findViewById(R.id.tv_weather_condition);
        tvHumidity = findViewById(R.id.tv_d_1);
        tvMaxT = findViewById(R.id.tv_d_2);
        tvMinT = findViewById(R.id.tv_d_3);
        tvPressure = findViewById(R.id.tv_d_4);
        tvWind = findViewById(R.id.tv_d_5);
        imgIcon = findViewById(R.id.img_icon);
        etSearch = findViewById(R.id.et_city);
        btnSearch = findViewById(R.id.btn_search);
    }

}
