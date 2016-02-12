package com.mortgage2.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.mortgage2.R;
import com.mortgage2.model.Calculator;
import com.mortgage2.util.DatabaseConnector;


public class ShowActivity extends Activity {


    private TextView purchaseTextView;
    private TextView downTextView;
    private TextView termTextView;
    private TextView rateTextView;
    private TextView taxTextView;
    private TextView insuranceTextView;
    private TextView monthlyTextView;
    private TextView totalTextView;

    private String purchase;
    private String downpay;
    private String term;
    private String rate;
    private String tax;
    private String insurance;
    private String monthly;
    private String total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        purchaseTextView= (TextView) findViewById(R.id.purchaseTextView);//find all the values
        downTextView= (TextView) findViewById(R.id.downTextView);
        termTextView= (TextView) findViewById(R.id.termTextView);
        rateTextView= (TextView) findViewById(R.id.rateTextView);
        taxTextView= (TextView) findViewById(R.id.taxTextView);
        insuranceTextView= (TextView) findViewById(R.id.insuranceTextView);
        monthlyTextView= (TextView) findViewById(R.id.monthlyTextView);
        totalTextView= (TextView) findViewById(R.id.totalTextView);
        Intent intent= getIntent();
        double purch= Double.parseDouble(intent.getStringExtra("123321"));//receive the contents that have been sent
        DatabaseConnector connector= new DatabaseConnector(this);
        connector.open();
        Cursor cursor= connector.getOne(purch);//read it into a cursor
        cursor.moveToFirst();
        int purchaseIndex= cursor.getColumnIndex("purchase");//get all the index
        int downIndex= cursor.getColumnIndex("downpay");
        int termIndex= cursor.getColumnIndex("term");
        int rateIndex= cursor.getColumnIndex("rate");
        int taxIndex= cursor.getColumnIndex("tax");
        int insuranceIndex= cursor.getColumnIndex("insurance");
        int monthlyIndex= cursor.getColumnIndex("monthly");
        int totalIndex= cursor.getColumnIndex("total");

        purchase= cursor.getString(purchaseIndex);//get all the values from the database using index
        downpay= cursor.getString(downIndex);
        term= cursor.getString(termIndex);
        rate= cursor.getString(rateIndex);
        tax= cursor.getString(taxIndex);
        insurance= cursor.getString(insuranceIndex);
        monthly= cursor.getString(monthlyIndex);
        total= cursor.getString(totalIndex);

        update();
        cursor.close();
        connector.close();
    }
    /*
    update method, used for setting values
     */
    private void update(){
        purchaseTextView.setText(purchase);
        downTextView.setText(downpay);
        termTextView.setText(term);
        rateTextView.setText(rate);
        taxTextView.setText(tax);
        insuranceTextView.setText(insurance);
        monthlyTextView.setText(String.format("%.02f",Double.parseDouble(monthly)));
        totalTextView.setText(String.format("%.02f",Double.parseDouble(total)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
