package com.example.currencyconverter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.adapter.CurrencyAdapter;
import com.example.currencyconverter.adapter.ListViewInterface;
import com.example.currencyconverter.model.CbData;
import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.request.ApiInterface;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CurrenciesActivity extends AppCompatActivity implements ListViewInterface {
    public static String BASE_URL = "https://www.cbr-xml-daily.ru/";

    private static final int PERMISSION_STORAGE_CODE = 1000;

    private CurrencyAdapter currencyAdapter;

    private List<Currency> currencies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        RecyclerView currenciesRecyclerView = findViewById(R.id.recyclerview);
        currenciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        currencyAdapter = new CurrencyAdapter(this);
        currenciesRecyclerView.setAdapter(currencyAdapter);

        download(findViewById(R.id.currenciesLayout));


    }


    public void onClickGoToConverter(Currency currency) {
        Intent intent = new Intent(CurrenciesActivity.this, ConverterActivity.class);
        intent.putExtra("currency for convert", currency);
        startActivity(intent);


    }

    @Override
    public void onItemClick(int position) {
        onClickGoToConverter(currencies.get(position));


    }

    public void download(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            } else {
                startDownloading();
            }
        } else {
            startDownloading();
        }
    }

    private void startDownloading() {
        ApiInterface apiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);


        Call<CbData> call = apiService.getCurrencies();

        call.enqueue(new Callback<CbData>() {
            @Override
            public void onResponse(Call<CbData> call, Response<CbData> response) {

                CbData cbData = response.body();

                fillActivityData(cbData);

            }

            @Override
            public void onFailure(Call<CbData> call, Throwable t) {

            }
        });

        Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void fillActivityData(CbData cbData) {
        TextView date = findViewById(R.id.textCurrenciesDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String formatDate = simpleDateFormat.format(cbData.getDate());
        date.setText(formatDate);

        currencies = new ArrayList<>(cbData.getValute().values());
        currencyAdapter.setItems(currencies);


    }

}