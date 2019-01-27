package com.example.currencyconverter;

import android.arch.persistence.room.Room;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private EditText fromCurrencyField;
    private EditText toCurrencyField;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private final int digitsAfterZero = 2;
    private String[] currencies;
    private CurrencyBank mCurrencyBank = new CurrencyBank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencies = new String[3];
        currencies[0] = "RUB";
        currencies[1] = "USD";
        currencies[2] = "EUR";

        fromCurrencyField = findViewById(R.id.from_field);
        toCurrencyField = findViewById(R.id.to_field);

        fromCurrencyField.setFilters(new InputFilter[] {new CurrencyInputFilter(digitsAfterZero)});
        toCurrencyField.setFilters(new InputFilter[] {new CurrencyInputFilter(digitsAfterZero)});

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner = findViewById(R.id.from_spinner);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(0);

        toSpinner = findViewById(R.id.to_spinner);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);

        fromCurrencyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (fromCurrencyField.getText().toString().equals(".")) {
                    fromCurrencyField.setText("0.");
                    fromCurrencyField.setSelection(2);
                }
                setToCurrencyValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        toCurrencyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (toCurrencyField.getText().toString().equals(".")) {
                    toCurrencyField.setText("0.");
                    toCurrencyField.setSelection(2);
                }
                setFromCurrencyValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setToCurrencyValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setToCurrencyValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Market market1 = new Market("USD", "RUB", 1);
        Market market2 = new Market("RUB", "USD", 2);

        MarketDao db =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").build().marketDao();

        Completable actionInsert = Completable.fromAction(() -> db.insert(market1));

        Disposable dispose = actionInsert.subscribeOn(Schedulers.io())
                .andThen((SingleSource<List<Market>>) observer -> db.getAll())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(markets ->
                        Log.d("TAG", String.valueOf(markets.size()))
                        , Throwable::printStackTrace);

    }

    void setToCurrencyValue() {
        Double toValue;
        if (fromCurrencyField.getText().toString().length() == 0) {
            toValue = 0.0;
        } else {
            toValue = Double.parseDouble(fromCurrencyField.getText().toString()) *
                    mCurrencyBank.getExchangeRate(fromSpinner.getSelectedItem().toString(),
                            toSpinner.getSelectedItem().toString());
        }
        toCurrencyField.setText(String.format(Locale.getDefault(),"%.2f", toValue));
    }

    void setFromCurrencyValue() {
        Double fromValue;
        if (fromCurrencyField.getText().toString().length() == 0) {
            fromValue = 0.0;
        } else {
            fromValue = Double.parseDouble(toCurrencyField.getText().toString()) *
                    mCurrencyBank.getExchangeRate(fromSpinner.getSelectedItem().toString(),
                            toSpinner.getSelectedItem().toString());
        }
        toCurrencyField.setText(String.format(Locale.getDefault(),"%.2f", fromValue));
    }
}
