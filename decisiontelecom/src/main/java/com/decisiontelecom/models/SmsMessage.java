package com.decisiontelecom.models;

/**
 * Represents SMS message
 */
public class SmsMessage {
    private final String receiverPhone;
    private final String sender;
    private final String text;
    private final boolean delivery;

    public SmsMessage(String receiverPhone, String sender, String text, boolean delivery) {
        this.receiverPhone = receiverPhone;
        this.sender = sender;
        this.text = text;
        this.delivery = delivery;
    }

    /**
     * Gets delivery flag value. Returns true if a caller needs to obtain the
     * delivery receipt in the future (by message id)
     * 
     * @return delivery flag value
     */
    public boolean isDelivery() {
        return delivery;
    }

    /**
     * Gets message body
     * 
     * @return meesage body
     */
    public String getText() {
        return text;
    }

    /**
     * Gets message sender. Could be a mobile phone number (including a country
     * code) or an alphanumeric string.
     * 
     * @return message sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets message receiver phone number (MSISDN Destination)
     * 
     * @return receiver phone number
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }
}
