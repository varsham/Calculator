package com.example.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String prevOperation = null;
    Double currCalc = null;
    String calcString = "";
    String textViewColor = "#bac1d1";
    String buttonColor = "#8277c9";
    TextView calcView;
    int[] buttonIDs = {R.id.equal, R.id.allClear, R.id.parens, R.id.percent, R.id.divide, R.id.one, R.id.two, R.id.three, R.id.multiply, R.id.four, R.id.five, R.id.six, R.id.subtract, R.id.seven, R.id.eight, R.id.nine, R.id.add, R.id.zero, R.id.point, R.id.delete};
    String[] strictOperations = {"/", "+", "*", "-", "%"};

    String[] operations = {"/", "+", "*", "%", "-"};
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
                    if (calcString.isEmpty() && button.getText().toString().equals("-")) {
                        calcString += button.getText();
                    } else if (calcString.isEmpty() && (isOperation((String) button.getText()) || button.getText().toString().equals("del"))) {
                        // if the first button clicked is a symbol or delete button, do not allow it
                        Toast.makeText(view.getContext(), "cannot start with a symbol", Toast.LENGTH_SHORT).show();
                    } else if (!calcString.isEmpty() && button.getId() == R.id.delete) {
                        // if the delete button is clicked and the calcString is not empty, then delete a character
                        calcString = calcString.substring(0, calcString.length() - 1);
                    } else if (button.getId() == R.id.allClear) {
                        // if all clear is clicked, then clear the calcView
                        calcString = "";
                    } else if (!calcString.isEmpty() && (isOperation(lastChar(calcString))) && isOperation(button.getText().toString())) {
                        // if there are two symbols in a row, do not allow it
                        Toast.makeText(view.getContext(), "cannot have two symbols in a row", Toast.LENGTH_SHORT).show();
                    } else if (button.getId() == R.id.equal) {
                        // if the equal button is clicked, test if the equation is valid with the testEquals method
                        testEquals();
                    } else calcString += button.getText();

                    updateCalcView();
                }


            });
        }
    }

    public void testEquals() {

        toEquation();

        updateCalcView();
    }

    public void toEquation() {
        ArrayList<String> currEquation = new ArrayList<>();

        // currEquation includes an array of operations
        currEquation = separateByOperation(calcString);

        evalEquations(currEquation);

        updateCalcView();
    }

    public ArrayList<String> separateByOperation(String s) {
        // separate a string of numbers + operations into separate elements
        ArrayList<String> sep = new ArrayList<>();
        String currElement = "";
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            String currChar = s.substring(i, i + 1);
            if (i != 0 && isOperation(currChar)) {
                sep.add(count, currElement);
                currElement = "";
                count++;
                sep.add(count, currChar);
                count++;
            } else {
                currElement += currChar;
            }
        }

        sep.add(count, currElement);

        return sep;
    }

    public void evalEquations(ArrayList<String> eq) {
        Double value = 0.0;
        // look for multiplication first
        int multIndex = eq.indexOf("*");
        while (multIndex != -1) {
            // look for the numbers on either side of the multIndex
            double num1 = Double.parseDouble(eq.get(multIndex - 1));
            double num2 = Double.parseDouble(eq.get(multIndex + 1));
            eq.set(multIndex, String.valueOf(num1 * num2));
            value = num1 * num2;
            // eq.remove(multIndex - 1);
            // eq.remove(multIndex + 1);

            multIndex = eq.indexOf("*");
        }

        calcString = String.valueOf(value);
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