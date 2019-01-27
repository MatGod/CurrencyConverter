package com.example.currencyconverter;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrenciesApi {

    @GET("currencies/")
    Single<MarketsList> getMarkets();


    //q = USD_PHP,PHP_USD
    //compact = ultra
    @GET("convert/")
    Single<MarketsPair> getRank(@Query("q") String currencies, @Query("compact") String compact);

}
