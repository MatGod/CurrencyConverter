package com.example.currencyconverter;

import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrenciesRetrofitClient {

    private Retrofit mRetrofit;
    private static final String BASE_URL = "https://free.currencyconverterapi.com/api/v6/";

    public CurrenciesRetrofitClient(){

        CurrencyListAdapter currencyListAdapter = new CurrencyListAdapter();
        CurrencyRankAdapter currencyRankAdapter = new CurrencyRankAdapter();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MarketsList.class, currencyListAdapter)
                .registerTypeAdapter(MarketsPair.class, currencyRankAdapter).create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public CurrenciesApi provideApi(){
        return mRetrofit.create(CurrenciesApi.class);
    }

    static class CurrencyListAdapter extends TypeAdapter<MarketsList> {

        @Override
        public void write(JsonWriter out, MarketsList value) throws IOException {

        }

        @Override
        public MarketsList read(JsonReader in) throws IOException {
            MarketsList currencies = new MarketsList();

            in.beginObject();
            while (in.hasNext()) {
                in.nextName();
                currencies.add(readCurrency(in));
            }
            in.endObject();

            return currencies;
        }

        private String readCurrency(JsonReader in) throws IOException {
            String currency = "";

            in.beginObject();
            while (in.hasNext()) {
                in.nextName();
                in.beginObject();
                String name = in.nextName();
                if (name.equals("currencyName")) {
                    in.nextString();
                    name = in.nextName();
                }
                if (name.equals("currencySymbol")) {
                    in.nextString();
                    name = in.nextName();
                }
                if (name.equals("id")) {
                    currency = in.nextString();
                }
                in.endObject();
            }
            in.endObject();

            return currency;
        }
    }

    static class CurrencyRankAdapter extends TypeAdapter<MarketsPair> {

        @Override
        public void write(JsonWriter out, MarketsPair value) throws IOException {

        }

        @Override
        public MarketsPair read(JsonReader in) throws IOException {
            Market fromTo = null;
            Market toFrom = null;

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                fromTo = new Market(name.substring(0, 2), name.substring(4, 6), in.nextDouble());
                name = in.nextName();
                toFrom = new Market(name.substring(0, 2), name.substring(4, 6), in.nextDouble());
            }

            return new MarketsPair(fromTo, toFrom);
        }
    }
}
