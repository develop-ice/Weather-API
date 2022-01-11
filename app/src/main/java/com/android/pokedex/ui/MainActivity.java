package com.android.pokedex.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pokedex.R;
import com.android.pokedex.model.OpenWeatherMap;
import com.android.pokedex.networking.RetrofitClient;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // UI
    private TextView tvCity, tvTemperature, tvWeather, tvHumidity, tvMaxT, tvMinT, tvPressure, tvWind;
    private ImageView imgIcon;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private LinearLayout layout;

    // Location
    private LocationManager locationManager;

    // Permissions
    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initViews
        initViews();

        // Permissions
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        // Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double lon = locationGPS.getLongitude();
                // Get data
                getWeatherData(lat, lon);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getWeatherData(double lat, double lon) {
        progressBar.setVisibility(View.VISIBLE);

        Call<OpenWeatherMap> call = RetrofitClient.getInstance().getApi().fetchWeaterWhithLocation(lat, lon);
        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                // hide progress
                progressBar.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                // show data
                tvCity.setText(String.format("%s , %s", response.body().getName(), response.body().getSys().getCountry()));
                tvTemperature.setText(String.format("%s ºC", response.body().getMain().getTemp()));
                tvWeather.setText(String.format(" : %s", response.body().getWeather().get(0).getDescription()));
                tvHumidity.setText(String.format(": %s", response.body().getMain().getHumidity()));
                tvMaxT.setText(String.format(": %s ºC", response.body().getMain().getTempMax()));
                tvMinT.setText(String.format(": %s ºC", response.body().getMain().getTempMin()));
                tvPressure.setText(String.format(": %s", response.body().getMain().getPressure()));
                tvWind.setText(String.format(": %s", response.body().getWind().getSpeed()));
                // Icon
                String iconCode = response.body().getWeather().get(0).getIcon();
                String image_url = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                Glide.with(MainActivity.this).load(image_url).into(imgIcon);
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable throwable) {
                // hide progress
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "ERROR: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        // UI
        tvCity = findViewById(R.id.tv_city);
        tvTemperature = findViewById(R.id.tv_temp);
        tvWeather = findViewById(R.id.tv_weather_condition);
        tvHumidity = findViewById(R.id.tv_d_1);
        tvMaxT = findViewById(R.id.tv_d_2);
        tvMinT = findViewById(R.id.tv_d_3);
        tvPressure = findViewById(R.id.tv_d_4);
        tvWind = findViewById(R.id.tv_d_5);
        imgIcon = findViewById(R.id.img_icon);
        fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.linear_layout);
        // OnClick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

}
