package com.example.brittanyshannon.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int price = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     *
     * @param view increases quantity of coffees when button is pressed
     */
    public void increment(View view) {

        // If quantity is more than 100, run lines of code under it
        if (quantity == 100){
            //Show error message as a toast
            Toast.makeText(this,"You cannot order more than 100 coffees",Toast.LENGTH_SHORT).show();
            //Exit method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        displayPrice(calculatePrice());
    }

    /**
     *
     * @param view decreases quantity of coffees when button is pressed
     */
    public void decrement(View view) {
        //if quantity gets below 1, run lines of code under it
        if (quantity == 0){
            //Shows error message as a toast
            Toast.makeText(this,"You cannot order less than 1 coffee",Toast.LENGTH_SHORT).show();
            //Exits method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
        displayPrice(calculatePrice());
    }

    /**
     *
     * This method is called when the order button is clicked.
     */
    public boolean whippedCreamBox(){
        CheckBox wcBox = findViewById(R.id.wcBox);
        boolean hasWhippedCream = wcBox.isChecked();
        return hasWhippedCream;
    }
    public boolean chocolateBox() {
        CheckBox chocBox = findViewById(R.id.chocBox);
        boolean hasChocolate = chocBox.isChecked();
        return hasChocolate;
    }
    public void submitOrder (View view) {

        CheckBox wcBox = findViewById(R.id.wcBox);
        boolean addWhippedCream = wcBox.isChecked();
        CheckBox chocBox = findViewById(R.id.chocBox);
        boolean addChocolate = chocBox.isChecked();
        String message = createOrderSummary(addChocolate, addWhippedCream);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order " + returnName());
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //showMessage(message);
    }




    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }
    /**
     * This method displays the given price on the screen.
     * @param number takes in the total price to display
     */
    private void displayPrice(int number) {
        TextView priceTextView =  findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        priceTextView.setTextSize(18);

    }

    /**
     *
     * @param message what displays in submit order summary view
     */
    private void showMessage(String message){
        TextView thanksTextView = findViewById(R.id.order_summary_text_view);
        thanksTextView.setText(message);
        thanksTextView.setTextSize(16);
    }

    /**
     * Calculates the price of the order.
     //*@param hasWhippedCream is whether or not the customer wants whipped cream
     //*@param hasChocolate is whether or not the customer wants chocolate
     *@return total price
     */
    private int calculatePrice() {
        price = 5;
        //Add $1 to price per cup if the customer wants whipped cream or chocolate
        if (chocolateBox() | whippedCreamBox()) {
            price =6;
        }
        //Add $2 to price per cup if the customer wants whipped cream and chocolate
        if (chocolateBox() && whippedCreamBox()) {
            price = 7;
        }
        //Returns total price
        return quantity *  price;
    }

    /**
     * @return name customer entered
     */

    public String returnName(){
       EditText nameText = findViewById(R.id.name);

       //Gets name customer entered in name field and turns it into a string called name
       String name = nameText.getEditableText().toString();
       //Returns name entered
       return name;


    }
    /**
     *
     * @return message displayed after order button is pressed
     */
    private String createOrderSummary(boolean hasWC, boolean hasChoc){
        String message = getString(R.string.name_input,returnName());
        message += "\n" + getString(R.string.wc_final) + hasWC;
        message += "\n" + getString(R.string.choc_final) + hasChoc;
        message += "\n" + getString(R.string.order_quantity) + quantity;
        message += "\n" +getString(R.string.total)+ calculatePrice();
        message += "\n" + getString(R.string.thank_you);
        return message;
    }
}