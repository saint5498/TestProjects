package com.example.currencyconverter.request;

import com.example.currencyconverter.model.CbData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("daily_json.js")
    Call<CbData> getCurrencies();
}
