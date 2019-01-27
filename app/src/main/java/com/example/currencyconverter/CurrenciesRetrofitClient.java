package com.example.currencyconverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import retrofit2.Retrofit;

public class CurrenciesRetrofitClient {

    private Retrofit mRetrofit;
    private static final String BASE_URL = "free.currencyconverterapi.com/api/v6/";

    public CurrenciesRetrofitClient(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }

    static class CurrencyTypeAdapter extends TypeAdapter<String> {

        @Override
        public void write(JsonWriter out, String value) throws IOException {

        }

        @Override
        public String read(JsonReader in) throws IOException {
            return null;
        }
    }
}
