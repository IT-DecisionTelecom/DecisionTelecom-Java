package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Viber plus SMS message
 */
public class ViberPlusSmsMessage extends ViberMessage{
    @SerializedName("text_sms") private String smsText;
    
    /** 
     * Gets an alternative SMS message text for cases when Viber message is not sent
     * 
     * @return SMS message text
     */
    public String getSmsText() {
        return smsText;
    }
    
    /** 
     * Sets an alternative SMS message text for cases when Viber message is not sent
     * 
     * @param smsText SMS message text
     */
    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }
}
