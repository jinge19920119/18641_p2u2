package com.mortgage2.model;

import java.io.Serializable;

/**
 * Created by jinge on 7/12/15.
 */
public class Calculator implements Serializable{
    private double purchase;
    private double downPay;
    private int term;
    private double rate;
    private double tax;
    private double insurance;
    private String month;
    private int year;
    private double paymentMonthly;
    private double paymentTotal;

    /*
    default constructor
     */
    public Calculator(){

    }
    /*
    constructor with parameters
     */
    public Calculator(double purchase, double downPay, int term, double rate, double tax, double insurance,
                      String mon, int year){
        this.purchase= purchase;
        this.downPay= downPay;
        this.term= term;
        this.rate= rate;
        this.tax= tax;
        this.insurance= insurance;
        month= mon;
        this.year= year;
        update();
    }
    /*
    updata method which is used for calculating
     */
    private void update(){
        this.paymentMonthly= purchase-downPay*purchase/100;
        this.paymentMonthly*= (rate/1200);
        this.paymentMonthly/= (1-Math.pow((1+rate/1200),-12*term));
        this.paymentMonthly+= (tax+insurance)/12;
        this.paymentTotal= this.paymentMonthly*12*term;
        this.year+= term;
    }

    /*
    getters
     */
    public Double getPurchase(){ return this.purchase;}
    public Double getDownPay() { return this.downPay;}
    public Integer getTerm() { return this.term;}
    public Double getTax(){ return this.tax;}
    public Double getRate() { return this.rate;}
    public Double getInsurance(){ return this.insurance;}
    public Double getMonthly(){
        return this.paymentMonthly;
    }
    public Double getTotal() {
        return this.paymentTotal;
    }
    public String getDate(){
        return (this.month+" , "+this.year);
    }
}
