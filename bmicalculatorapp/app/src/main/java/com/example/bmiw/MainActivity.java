package com.example.bmiw;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtWeight, edtHeightft, edtHeightin;
        Button btcCalculate;
        TextView Result;
        View mainLayout;

        edtWeight = findViewById(R.id.edtWeight);
        edtHeightft = findViewById(R.id.edtHeightft);
        edtHeightin = findViewById(R.id.edtHeightin);
        btcCalculate = findViewById(R.id.btnCalculate);
        Result = findViewById(R.id.txtResult);
        mainLayout = findViewById(R.id.main);

        btcCalculate.setOnClickListener(v -> {
            // Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null && getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            String wtStr = edtWeight.getText().toString();
            String ftStr = edtHeightft.getText().toString();
            String inStr = edtHeightin.getText().toString();

            if (!wtStr.isEmpty() && !ftStr.isEmpty() && !inStr.isEmpty()) {
                try {
                    double wt = Double.parseDouble(wtStr);
                    double ft = Double.parseDouble(ftStr);
                    double in = Double.parseDouble(inStr);

                    double totalIn = ft * 12 + in;
                    double totalCm = totalIn * 2.54;
                    double totalM = totalCm / 100;

                    if (totalM > 0) {
                        double bmi = wt / (totalM * totalM);
                        String bmiFormatted = String.format(Locale.US, "%.2f", bmi);

                        if (bmi > 25) {
                            Result.setText(getString(R.string.res_overweight, bmiFormatted));
                            mainLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorOW));
                        } else if (bmi < 18.5) {
                            Result.setText(getString(R.string.res_underweight, bmiFormatted));
                            mainLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorUW));
                        } else {
                            Result.setText(getString(R.string.res_healthy, bmiFormatted));
                            mainLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorH));
                        }
                    } else {
                        Result.setText(R.string.err_height);
                    }
                } catch (NumberFormatException e) {
                    Result.setText(R.string.err_invalid);
                }
            } else {
                Result.setText(R.string.err_missing);
            }
        });



    }
}