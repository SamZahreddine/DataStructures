package com.example.datastructuresproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Declaration of variables:
    private EditText submission;
    private TextView counter;
    private final String[] symbols = {"+", "-", "*", "/", "^", "%"};
    private Button submit;
    private  String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialization of variables:
        submission = findViewById(R.id.submission);
        counter = findViewById(R.id.counter);
        submit = findViewById(R.id.btnSubmit);

        submit.setOnClickListener(this::onClickSubmit);
        submission.addTextChangedListener(submissionWatcher);
    }

    private final TextWatcher submissionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //Logic for counter:
            text = charSequence.toString().replaceAll(" ","");
            if (text.isEmpty())
                counter.setText("0");

            else
                counter.setText(String.valueOf(text.length()));

            if (text.length() > 15)
                counter.setTextColor(getColor(R.color.red));

            else
                counter.setTextColor(getColor(R.color.white));
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };
    //------------------------------ SYMBOL CHECK ------------------------------//
    public boolean containsSymbol (String input) {
        return Arrays.stream(symbols).anyMatch(input::contains);
    }

    //------------------------------ SUBMIT ------------------------------//

    public void onClickSubmit(View v) {
        String submissionText = submission.getText().toString().toUpperCase();

        //If the user did not type anything or did not put any symbols:
        if ( submissionText.isEmpty() || !containsSymbol(submissionText) ) {
            Toast.makeText(getApplicationContext(), "Please Enter a valid argument!", Toast.LENGTH_SHORT).show();
        }
        else {
            char begin = submissionText.charAt(0);
            for (int i = 1; i<submissionText.length(); i++) {
                char position = submissionText.charAt(i);
                char before = submissionText.charAt(i-1);
                //If two letters or numbers are next to each other without any operators between:
                if (Character.isLetterOrDigit(position) && Character.isLetterOrDigit(before) ) {
                    Toast.makeText(getApplicationContext(), "Please Enter a valid argument!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            //If it begins with an operator:
            if ( begin == '+' || begin == '-' || begin == '*' || begin == '/' || begin == '^' || begin == '%') {
                Toast.makeText(getApplicationContext(), "Please Enter the expression in infix format!", Toast.LENGTH_LONG).show();
            }

            else if (text.length() > 15) {
                Toast.makeText(getApplicationContext(), "Expression too long!", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(getApplicationContext(), options.class);
                i.putExtra("expression", submissionText);
                startActivity(i);
                finish();
            }
        }
    }

    //------------------------------ BACK TO EXIT ------------------------------//
    private long backPressedTime = 0;

    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 4000) {
            backPressedTime = t;
            Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }

}