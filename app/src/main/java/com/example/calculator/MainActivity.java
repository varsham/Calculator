package com.example.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String prevOperation = null;
    Double currCalc = null;
    String calcString = "";
    String textViewColor = "#bac1d1";
    String buttonColor = "#8277c9";
    TextView calcView;
    int[] buttonIDs = {R.id.allClear, R.id.negate, R.id.percent, R.id.divide, R.id.one, R.id.two, R.id.three, R.id.multiply, R.id.four, R.id.five, R.id.six, R.id.subtract, R.id.seven, R.id.eight, R.id.nine, R.id.add, R.id.zero, R.id.point, R.id.delete};
    String[] operations = {"/", "+", "*", "--", "%"};
    Button equalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        calcView = findViewById(R.id.calcView);
        equalButton = findViewById(R.id.equal);

        setInitialValues();
        onClickButtons();
    }

    public void setInitialValues() {
        // set the colors of everything
        calcView.setBackgroundColor(Color.parseColor(textViewColor));

        // clear the text view
        calcView.setText("");

        calcView.setTextColor(Color.parseColor("#211969"));

        calcString = "";

        currCalc = null;

        prevOperation = null;
    }

    public void onClickButtons() {
        for (int buttonID : buttonIDs) {
            Button button = findViewById(buttonID);
            button.setBackgroundColor(Color.parseColor(buttonColor));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (calcString.isEmpty() && (isOperation((String) button.getText()) || button.getText().toString().equals("del"))) {
                        // if the first button clicked is a symbol or delete button, do not allow it
                        Toast.makeText(view.getContext(), "cannot start with a symbol", Toast.LENGTH_SHORT).show();
                    } else if (!calcString.isEmpty() && button.getId() == R.id.delete) {
                        // if the delete button is clicked and the calcString is not empty, then delete a character
                        calcString = calcString.substring(0, calcString.length() - 1);
                    } else if (button.getId() == R.id.allClear) {
                        // if all clear is clicked, then clear the calcView
                        calcString = "";
                    } else if (!calcString.isEmpty() && isOperation(lastChar(calcString))) {
                        // if there are two symbols in a row, do not allow it
                        Toast.makeText(view.getContext(), "cannot have two symbols in a row", Toast.LENGTH_SHORT).show();
                    } else if (button.getId() == R.id.equal) {
                        // if the equal button is clicked, test if the equation is valid with the testEquals method
                        testEquals(calcString);
                    } else calcString += button.getText();

                    updateCalcView();
                }


            });
        }
    }

    public void testEquals(String calcString) {
        if (calcString.isEmpty() || calcString.endsWith(".") || isOperation(lastChar(calcString))) calcString = "";
        else toEquation(calcString);
        updateCalcView();
    }

    public void toEquation(String calcString) {
        // first character is going to be a "." (and we can expect a number afterwards) or a number
        
    }

    public void updateCalcView() {
        calcView.setText(calcString);
    }

    public boolean includes(String[] arr, String s) {
        for (String a : arr)
            if (s.equals(a)) return true;
        return false;
    }

    public boolean isOperation(String s) {
        return (includes(operations, s));
    }

    public String lastChar(String s) {
        return s.substring(s.length() - 1);
    }

}