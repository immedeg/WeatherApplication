package com.example.weatherapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<CityItem> cityItems;
    private Map<Integer, String> periods;

    private Spinner spinnerCity;
    private RadioGroup periodGroup;
    private Button launchButton;

    private CityItem selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
        setupCitySpinner();
        setupLaunchButton();
    }

    private void initData() {
        cityItems = new ArrayList<>();
        cityItems.add(new CityItem("Нижний Новгород", "nizhny-novgorod", 4355));
        cityItems.add(new CityItem("Москва", "moscow", 4368));
        cityItems.add(new CityItem("Санкт-Петербург", "sankt-peterburg", 4079));
        cityItems.add(new CityItem("Казань", "kazan", 4364));
        cityItems.add(new CityItem("Екатеринбург", "yekaterinburg", 4517));
        cityItems.add(new CityItem("Улан-Удэ", "ulan-ude", 4804));
        cityItems.add(new CityItem("Чита", "chita", 4797));
        cityItems.add(new CityItem("Владивосток", "vladivostok", 4877));

        periods = new HashMap<>();
        periods.put(R.id.radioToday, "");
        periods.put(R.id.radioTomorrow, "tomorrow/");
        periods.put(R.id.radio3days, "3-days/");
        periods.put(R.id.radio10days, "10-days/");

        selectedCity = cityItems.get(0);
    }

    private void initViews() {
        spinnerCity = findViewById(R.id.spinnerCity);
        periodGroup = findViewById(R.id.radioGroupPeriod);
        launchButton = findViewById(R.id.btnLaunch);
    }

    private void setupCitySpinner() {
        List<String> cityNames = new ArrayList<>();
        for (CityItem city : cityItems) {
            cityNames.add(city.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cityNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityItems.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupLaunchButton() {
        launchButton.setOnClickListener(v -> openGismeteo());
    }

    private void openGismeteo() {
        int selectedPeriodId = periodGroup.getCheckedRadioButtonId();
        String period = periods.get(selectedPeriodId);
        if (period == null) {
            period = "";
        }

        String url = "https://www.gismeteo.ru/weather-"
                + selectedCity.getSlug()
                + "-" + selectedCity.getId()
                + "/" + period;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    MainActivity.this,
                    "Не найден браузер для открытия ссылки",
                    Toast.LENGTH_LONG
            ).show();
        } catch (Exception e) {
            Toast.makeText(
                    MainActivity.this,
                    "Ошибка при открытии ссылки: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}