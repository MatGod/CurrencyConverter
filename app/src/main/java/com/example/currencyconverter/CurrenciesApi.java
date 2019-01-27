package com.example.currencyconverter;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CurrenciesApi {

    @GET("currencies/")
    Single<MarketsList> getMarkets();

}
