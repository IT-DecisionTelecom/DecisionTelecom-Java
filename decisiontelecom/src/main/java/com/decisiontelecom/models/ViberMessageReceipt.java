package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Id and status of the particular Viber message
 */
public class ViberMessageReceipt {
    @SerializedName("message_id") private long viberMessageId;
    @SerializedName("status") ViberMessageStatus viberMessageStatus;
    
    /** 
     * Gets Id of the Viber message which status should be got (sent in the last 5 days)
     * 
     * @return Id of the Viber message
     */
    public long getViberMessageId() {
        return viberMessageId;
    }
    
    /** 
     * Gets Viber message status
     * 
     * @return Viber message status
     */
    public ViberMessageStatus getViberMessageStatus() {
        return viberMessageStatus;
    }
    
    /** 
     * Sets Viber message status
     * 
     * @param viberMessageStatus Viber message status
     */
    public void setViberMessageStatus(ViberMessageStatus viberMessageStatus) {
        this.viberMessageStatus = viberMessageStatus;
    }
    
    /** 
     * Sets Id of the Viber message which status should be got (sent in the last 5 days)
     * 
     * @param viberMessageId Id of the Viber message
     */
    public void setViberMessageId(long viberMessageId) {
        this.viberMessageId = viberMessageId;
    }
}
