package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents user money balance
 */
public class SmsBalance {
    @SerializedName("balance") private double balanceAmount;
    @SerializedName("credit") private double creditAmount;
    @SerializedName("currency") private String currency;
    
    /** 
     * Gets the current balance amount
     * 
     * @return Current balance amount
     */
    public double getBalanceAmount() {
        return balanceAmount;
    }
    
    /** 
     * Gets the current credit line amount (if opened)
     * 
     * @return Current credit amount
     */
    public double getCreditAmount() {
        return creditAmount;
    }

    
    /** 
     * Gets balance currency
     * 
     * @return Balance currency
     */
    public String getCurrency() {
        return currency;
    }
    
    
    /** 
     * Sets balance currency
     * 
     * @param currency balance currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    
    /** 
     * Sets balance credit amount
     * 
     * @param creditAmount balance credit amount
     */
    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }
    
    
    /** 
     * Sets balance amount
     * 
     * @param balanceAmount balance amount
     */
    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
