package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int numberOfCoffees=1;
    boolean hasWhippedCream=false;
    boolean hasChocolate=false;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view){
        if(numberOfCoffees<100)
            numberOfCoffees++;
        if(numberOfCoffees==100){
            Toast upperBoundError=Toast.makeText(this,"Your order cannot be more than 100",Toast.LENGTH_SHORT);
            upperBoundError.show();
        }
        displayQuantity(numberOfCoffees);
    }
    public void decrement(View view){
        if(numberOfCoffees>1)
            numberOfCoffees--;
        if(numberOfCoffees==1){
            Toast lowerBoundError=Toast.makeText(this,"Your order cannot be less than 1",Toast.LENGTH_SHORT);
            lowerBoundError.show();
        }
        displayQuantity(numberOfCoffees);
    }
    public void setWhippedCreamCheckedState(View v){
        CheckBox whippedCream=(CheckBox) findViewById(R.id.whipped_cream_check);
        hasWhippedCream=whippedCream.isChecked();
    }
    public void setChocolateCheckedState(View v){
        CheckBox chocolate=(CheckBox) findViewById(R.id.chocolate_check);
        hasChocolate=chocolate.isChecked();
    }
    public void submitOrder(View view){
        EditText nameField=(EditText)findViewById(R.id.name_text_field);
        name=nameField.getText().toString();
        sendOrderSummary(calculatePrice(numberOfCoffees));

    }
    private void displayQuantity(int number){
        TextView quantityTextView= (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }
    private double calculatePrice(int numCoffee){
        double costOfCoffee=5.0;
        double costOfWhippedCream=0.25;
        double costOfChocolate=0.25;
        double cost=0.0;
        if(hasWhippedCream==true&&hasChocolate==true){
            cost=costOfCoffee+costOfWhippedCream+costOfChocolate;
        }
        else if(hasWhippedCream==true&&hasChocolate==false){
            cost=costOfCoffee+costOfWhippedCream;
        }
        else if(hasWhippedCream==false&&hasChocolate==true){
            cost=costOfCoffee+costOfChocolate;
        }
        if(hasWhippedCream==false&&hasChocolate==false){
            cost=costOfCoffee;
        }
        return(cost*numCoffee);
    }
    private void sendOrderSummary(double price){
        Intent email=new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        String subject=String.format("JustJava order for %s",name);
        email.putExtra(Intent.EXTRA_SUBJECT,subject);
        String body=String.format("Name: %s\nAdd whipped cream? %b\nAdd chocolate? %b\nQuantity: %d\nTotal: $%.2f\nThank you!",name,hasWhippedCream,hasChocolate,numberOfCoffees,price);
        email.putExtra(Intent.EXTRA_TEXT,body);
        if(email.resolveActivity(getPackageManager())!=null){
            startActivity(email);
        }
    }
}
