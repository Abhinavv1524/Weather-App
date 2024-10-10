package com.example.weather;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = BuildConfig.WEATHER_API_KEY;
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    EditText etCityName;
    Button btnGetWeather;
    TextView tvWeather , WeatherCity, WeatherTemp , WeatherDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCityName = findViewById(R.id.etCityName);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        tvWeather = findViewById(R.id.tvWeather);
        WeatherCity = findViewById(R.id.WeatherCity);
        WeatherTemp = findViewById(R.id.WeatherTemp);
        WeatherDes = findViewById(R.id.WeatherDes);

        btnGetWeather.setOnClickListener(v -> {
            String city = etCityName.getText().toString();
            if (!city.isEmpty()) {
                getWeather(city);
                WeatherCity.setText(etCityName.getText());
            }
        });
    }

    private void getWeather(String city) {
        String urlString = String.format(API_URL, city, API_KEY);
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    inputStream.close();

                    updateUI(response.toString());
                } else {
                    runOnUiThread(() -> tvWeather.setText("Failed to fetch weather data. Response code: " + responseCode));
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> tvWeather.setText("Error fetching weather data: " + e.getMessage()));
            }
        }).start();
    }

    private void updateUI(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            // Check if the response contains the "weather" array
            if (jsonResponse.has("weather")) {
                JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String description = weather.getString("description");

                // Check if the response contains the "main" object
                if (jsonResponse.has("main")) {
                    JSONObject main = jsonResponse.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    int temperature = (int) (temp - 273.15); // Convert temperature to Celsius

                    runOnUiThread(() -> WeatherTemp.setText(temperature + "°C"));
                    runOnUiThread(() ->WeatherDes.setText(description));
                } else {
                    runOnUiThread(() -> tvWeather.setText("Unable to fetch temperature"));
                }
            } else {
                runOnUiThread(() -> tvWeather.setText("Unable to fetch weather"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> tvWeather.setText("Error fetching weather"));
        }
    }
}