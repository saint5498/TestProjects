package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyconverter.model.Currency;

public class ConverterActivity extends AppCompatActivity {


    private Currency currency;
    private TextView textConvertedResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        findViewById(R.id.textConvertResultHelper).setVisibility(View.INVISIBLE);
        textConvertedResult = findViewById(R.id.textConvertResultHelper);

        this.currency = getIntent().getParcelableExtra("currency for convert");

        TextView charCode = findViewById(R.id.textCurrencyCode);
        charCode.setText(currency.getCharCode());

        TextView value = findViewById(R.id.textCurrencyValue);
        value.setText(String.valueOf(currency.getValue()));

        TextView nominal = findViewById(R.id.nominal);
        nominal.setText("Номинал: " + currency.getNominal());
    }

    public void convert(View view) {
        EditText editText = findViewById(R.id.editTextConvertAmount);
        String inputAmount = editText.getText().toString().trim();
        Double amount = 0.0;
        try {
            amount = Double.parseDouble(inputAmount);
        } catch (Exception e) {
            Toast.makeText(this, "Need value", Toast.LENGTH_SHORT).show();
            return;
        }
        textConvertedResult.setVisibility(View.VISIBLE);
        double result = Math.ceil(amount / currency.getValue() * currency.getNominal() * 100) / 100;
        TextView editTextView = findViewById(R.id.textConvertedResult);
        editTextView.setText(String.valueOf(result));
        editTextView.setVisibility(View.VISIBLE);


    }
}