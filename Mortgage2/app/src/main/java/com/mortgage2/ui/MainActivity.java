package com.mortgage2.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mortgage2.R;
import com.mortgage2.model.Calculator;
import com.mortgage2.util.DatabaseConnector;

import java.util.Date;


public class MainActivity extends Activity {


    private double purchase;
    private double downPay;
    private int term;
    private double rate;
    private double tax;
    private double insurance;
    private double totalMonthly;
    private double total;
    private Calculator cal;
    private DatabaseConnector connector;

    private EditText purchaseEditText;
    private EditText downEditText;
    private EditText termEditText;
    private EditText rateEditText;
    private EditText taxEditText;
    private EditText insuranceEditText;
    private Spinner spin1;
    private Spinner spin2;

    private EditText totalMonthlyEditText;
    private EditText totalEditText;
    private EditText payoffDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        purchaseEditText= (EditText) findViewById(R.id.purchaseEditText);//find all the values
        downEditText= (EditText) findViewById(R.id.downEditText);
        termEditText= (EditText) findViewById(R.id.termEditText);
        rateEditText= (EditText) findViewById(R.id.rateEditText);
        taxEditText= (EditText) findViewById(R.id.taxEditText);
        insuranceEditText= (EditText) findViewById(R.id.insuranceEditText);
        spin1= (Spinner) findViewById(R.id.spinner);
        spin2= (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter adpMon= ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        ArrayAdapter adpYear= ArrayAdapter.createFromResource(this, R.array.years,android.R.layout.simple_spinner_item);
        spin1.setAdapter(adpMon);
        spin2.setAdapter(adpYear);

        totalMonthlyEditText= (EditText) findViewById(R.id.totalMonthlyEditText);
        totalEditText= (EditText) findViewById(R.id.totalEditText);
        payoffDateEditText= (EditText) findViewById(R.id.payoffDateEditText);
    }

    /*
    update method, making reading and setting values.
     */
    private void update(){
        purchase= Double.parseDouble(purchaseEditText.getText().toString());
        downPay= Double.parseDouble(downEditText.getText().toString());
        term= Integer.parseInt(termEditText.getText().toString());
        rate= Double.parseDouble(rateEditText.getText().toString());
        tax= Double.parseDouble(taxEditText.getText().toString());
        insurance= Double.parseDouble(insuranceEditText.getText().toString());
        String mon= spin1.getSelectedItem().toString();
        int year= Integer.parseInt(spin2.getSelectedItem().toString());
        cal= new Calculator(purchase,downPay,term,rate,tax,insurance,mon,year);

        totalMonthly= cal.getMonthly();
        total= cal.getTotal();
        totalEditText.setText(String.format("%.02f",total));
        totalMonthlyEditText.setText(String.format("%.02f", totalMonthly));
        payoffDateEditText.setText(cal.getDate());

    }

    /*
    used when click 'calculate'
     */
    public void buttonOnClick(View v){
        update();
    }
    /*
    used when click 'save'
     */
    public void buttonOnClickSave(View v){
        Intent intent= new Intent(this,ShowActivity.class);
        intent.putExtra("123321",cal.getPurchase().toString());//send the purchase value
        saveToDatabase();
        startActivity(intent);//jump to another activity
    }
    /*
    save all the values into database
     */
    private void saveToDatabase(){
        connector= new DatabaseConnector(this);
        connector.insertData(purchase, downPay, term, rate, tax, insurance, totalMonthly, total);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
