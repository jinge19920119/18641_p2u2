package com.mortgage2.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by jinge on 7/12/15.
 */
public class DatabaseConnector {
    private static final String DATABASE_NAME = "MortgageData";
    private SQLiteDatabase database; // database object
    private DatabaseOpenHelper databaseOpenHelper; // database helper

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context)

    {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }
/*
open the connection
 */
    public void open()
    {
        database = databaseOpenHelper.getWritableDatabase();
    }

    /*
    close the connection
     */
    public void close()
    {
        if (database != null)
            database.close();
    }

    /*
    insert all the values of a calculator object to the database
     */
    public void insertData(Double price, Double down, Integer term, Double rate,
                           Double tax, Double insurance, Double monthly, Double total) {
        ContentValues newData= new ContentValues();
        newData.put("purchase", price.toString());
        newData.put("downpay", down.toString());
        newData.put("term", term.toString());
        newData.put("rate",rate.toString());
        newData.put("tax", tax.toString());
        newData.put("insurance", insurance.toString());
        newData.put("monthly", monthly.toString());
        newData.put("total", total.toString());
        open();
        database.insert("calculation", null, newData);
        close();
    }

    /*
    get the specific one from the databse with the purchase value
     */
    public Cursor getOne (Double purchase){

        Cursor cursor= database.query("calculation",null,"purchase ="+purchase.toString(),null,null,null,null);

        return cursor;
    }

    /*
    inner class which is used as database openHelper
     */
    private class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // query to create a new table named contacts
            String createQuery = "CREATE TABLE calculation" +
                    "(_id integer primary key autoincrement, purchase TEXT, downpay TEXT, term TEXT," +
                    "rate TEXT, tax TEXT, insurance TEXT, monthly TEXT, total TEXT);";

            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
        }
    }



}
