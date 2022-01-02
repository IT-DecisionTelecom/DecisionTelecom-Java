package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Id and status of the particular Viber plus SMS message.
 */
public class ViberPlusSmsMessageReceipt extends ViberMessageReceipt {
    @SerializedName("sms_message_id") private long smsMessageId;
    @SerializedName("sms_message_status") private SmsMessageStatus smsMessageStatus;
    
    /** 
     * Gets SMS message Id (if available, only for transactional messages)
     * 
     * @return SMS message Id
     */
    public long getSmsMessageId() {
        return smsMessageId;
    }
    
    /** 
     * Gets SMS message status (if available, only for transactional messages) 
     * 
     * @return SMS message status
     */
    public SmsMessageStatus getSmsMessageStatus() {
        return smsMessageStatus;
    }
    
    /** 
     * Sets SMS message status (if available, only for transactional messages) 
     * 
     * @param smsMessageStatus SMS message status
     */
    public void setSmsMessageStatus(SmsMessageStatus smsMessageStatus) {
        this.smsMessageStatus = smsMessageStatus;
    }
    
    /** 
     * Sets SMS message Id (if available, only for transactional messages)
     * 
     * @param smsMessageId SMS message Id
     */
    public void setSmsMessageId(long smsMessageId) {
        this.smsMessageId = smsMessageId;
    }
}
