package com.example.ahmedfareed.simplecaculator;

import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String display = "";
    TextView screen;
    String currentOperator = "";
    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
    }

    public void updateScreen() {
        screen.setText(display);
    }

    public void onClickNumber(View view) {
        Button b = (Button) view;

        if (result != "") {
            clear();
            updateScreen();
        }
        display += b.getText();
        updateScreen();
    }


    public boolean isOperate(char op) {
        switch (op) {
            case '+':
            case '-':
            case 'x':
            case '÷':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View v) {
        if (display == "") return;
        Button b = (Button) v;

        if (result != "") {
            String _result = result;
            clear();
            display = _result;
        }

        if (currentOperator != "") {

            char ops = (char) ((char) display.charAt(display.length() - 1));

            if (isOperate(ops)) {
                display = display.replace(ops, b.getText().charAt(0));
                updateScreen();
            } else {

                getResult();
                display = result;
                result = "";

            }
        }
        currentOperator = ((Button) v).getText().toString();
        display += currentOperator;
        updateScreen();

    }

    public void clear() {
        display = "";
        currentOperator = "";
    }

    public void onClickClear(View v) {
        clear();
        updateScreen();
    }



    public double operate(String a, String b, String op) {

        switch (op) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            case "÷":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            default:
                return -1;
        }
    }

    public boolean getResult() {
        if(currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v) {

        //Log.i("tapped", "it's tapped!");

        if (result == "") return;
        if(!getResult()) return;
        screen.setText(display + "\n" + String.valueOf(result));
    }

}
