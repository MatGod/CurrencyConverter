package com.example.currencyconverter;

import android.util.Pair;

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

    static class CurrencyListAdapter extends TypeAdapter<ArrayList<String>> {

        @Override
        public void write(JsonWriter out, ArrayList<String> value) throws IOException {

        }

        @Override
        public ArrayList<String> read(JsonReader in) throws IOException {
            ArrayList<String> currencies = new ArrayList<>();

            in.beginObject();
            while (in.hasNext()) {
                currencies.add(readCurrency(in));
            }
            in.endObject();

            return currencies;
        }

        private String readCurrency(JsonReader in) throws IOException {
            String currency = "";

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                if (name.equals("id")) {
                    currency = in.nextString();
                }
            }
            in.endObject();

            return currency;
        }
    }

    static class CurrencyRankAdapter extends TypeAdapter<Pair<Market, Market>> {

        @Override
        public void write(JsonWriter out, Pair<Market, Market> value) throws IOException {

        }

        @Override
        public Pair<Market, Market> read(JsonReader in) throws IOException {
            Market fromTo = null;
            Market toFrom = null;

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                fromTo = new Market(name.substring(0, 2), name.substring(4, 6), in.nextDouble());
                name = in.nextName();
                toFrom = new Market(name.substring(0, 2), name.substring(4, 6), in.nextDouble());
            }

            return Pair.create(fromTo, toFrom);
        }
    }
}
