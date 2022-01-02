package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Viber message
 */
public class ViberMessage {
    @SerializedName("source_addr")
    private String sender;
    @SerializedName("destination_addr")
    private String receiver;
    @SerializedName("message_type")
    private ViberMessageType messageType;
    @SerializedName("text")
    private String text;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("button_caption")
    private String buttonCaption;
    @SerializedName("button_action")
    private String buttonAction;
    @SerializedName("source_type")
    private ViberMessageSourceType sourceType;
    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("validity_period")
    private int validityPeriod;

    /**
     * Gets message sender (from whom message is sent)
     * 
     * @return Message sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets message receiver (to whom message is sent)
     * 
     * @return Message receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets Viber message type
     * 
     * @return Message type
     */
    public ViberMessageType getMessageType() {
        return messageType;
    }

    /**
     * Gets message in the UTF8 format
     * 
     * @return Message text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets image URL for promotional message with button caption and button action
     * 
     * @return Image URL for promotional message
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Gets button caption in the UTF8 format
     * 
     * @return Button caption
     */
    public String getButtonCaption() {
        return buttonCaption;
    }

    /**
     * Gets an URL for transition when the button is pressed
     * 
     * @return URL for transition when the button is pressed
     */
    public String getButtonAction() {
        return buttonAction;
    }

    /**
     * Gets message sending procedure
     * 
     * @return Message sending procedure
     */
    public ViberMessageSourceType getSourceType() {
        return sourceType;
    }

    /**
     * Gets URL for message status callback
     * 
     * @return URL for message status callback
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * Gets life time of a message (in seconds)
     * 
     * @return Life time of a message (in seconds)
     */
    public int getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * Sets message sender (from whom message is sent)
     * 
     * @param sender message sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Sets message receiver (to whom message is sent)
     * 
     * @param receiver Message receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Sets Viber message type
     * 
     * @param messageType Message type
     */
    public void setMessageType(ViberMessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * Sets message in the UTF8 format
     * 
     * @param text Message text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets image URL for promotional message with button caption and button action
     * 
     * @param imageUrl Image URL for promotional message
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Sets button caption in the UTF8 format
     * 
     * @param buttonCaption Button caption
     */
    public void setButtonCaption(String buttonCaption) {
        this.buttonCaption = buttonCaption;
    }

    /**
     * Sets an URL for transition when the button is pressed
     * 
     * @param buttonAction URL for transition when the button is pressed
     */
    public void setButtonAction(String buttonAction) {
        this.buttonAction = buttonAction;
    }

    /**
     * Sets message sending procedure
     * 
     * @param sourceType Message sending procedure
     */
    public void setSourceType(ViberMessageSourceType sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * Sets URL for message status callback
     * 
     * @param callbackUrl URL for message status callback
     */
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * Sets life time of a message (in seconds)
     * 
     * @param validityPeriod Life time of a message (in seconds)
     */
    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
