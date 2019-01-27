package com.example.currencyconverter;

import java.util.ArrayList;

public class CurrencyBank {

    private ArrayList<String> mCurrenciesNames;
    private Double[][] mExchangeRates;

    public ArrayList<String> getCurrenciesNames() {
        return mCurrenciesNames;
    }

    public Double getExchangeRate(String from, String to) {
        return mExchangeRates[mCurrenciesNames.indexOf(from)][mCurrenciesNames.indexOf(to)];
    }

    CurrencyBank() {
        mCurrenciesNames = new ArrayList<>();
        mCurrenciesNames.add("RUB");
        mCurrenciesNames.add("USD");
        mCurrenciesNames.add("EUR");
        mExchangeRates = new Double[mCurrenciesNames.size()][mCurrenciesNames.size()];

        for (int i = 0; i < mCurrenciesNames.size(); i++) {
            for (int j = 0; j < mCurrenciesNames.size(); j++) {
                if (i == j) {
                    mExchangeRates[i][j] = 1.0;
                }
                else {
                    mExchangeRates[i][j] = 66.5;
                }
            }
        }
    }
}