package com.example.currencyconverter;

import android.support.annotation.RequiresPermission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Retrofit;

public class CurrenciesRetrofitClient {

    private Retrofit mRetrofit;
    private static final String BASE_URL = "free.currencyconverterapi.com/api/v6/";

    public CurrenciesRetrofitClient(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }

    static class CurrencyTypeAdapter extends TypeAdapter<ArrayList<String>> {

        @Override
        public void write(JsonWriter out, ArrayList<String> value) throws IOException {

        }

        @Override
        public ArrayList<String> read(JsonReader in) throws IOException {
            ArrayList<String> currencies = new ArrayList<>();

            in.beginObject();
            while (in.hasNext()) {
                currencies.add(ReadCurrency(in));
            }
            return currencies;
        }

        private String ReadCurrency(JsonReader in) throws IOException {
            String currency = "";
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                if (name.equals("id")) {
                    currency = in.nextString();
                }
            }
            return currency;
        }
    }
}
