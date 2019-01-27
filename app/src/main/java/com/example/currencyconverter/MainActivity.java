package com.example.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

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
                Double toValue;
                if (fromCurrencyField.getText().toString().equals(".")) {
                    fromCurrencyField.setText("0.");
                    fromCurrencyField.setSelection(2);
                }
                if (fromCurrencyField.getText().toString().length() == 0) {
                    toValue = 0.0;
                } else {
                    toValue = Double.parseDouble(fromCurrencyField.getText().toString()) *
                            mCurrencyBank.getExchangeRate(fromSpinner.getSelectedItem().toString(),
                                    toSpinner.getSelectedItem().toString());
                }
                toCurrencyField.setText(String.format(Locale.getDefault(),"%.2f", toValue));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}