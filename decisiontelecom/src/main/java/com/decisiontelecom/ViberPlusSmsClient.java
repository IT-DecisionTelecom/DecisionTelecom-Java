package com.decisiontelecom;

import java.io.IOException;

import com.decisiontelecom.exceptions.ViberException;
import com.decisiontelecom.models.ViberPlusSmsMessage;
import com.decisiontelecom.models.ViberPlusSmsMessageReceipt;
import com.google.gson.Gson;

public class ViberPlusSmsClient extends ViberClient{

    /**
     * Creates a new instance of the ViberPlusSmsClient class
     * @param apiKey User access key
     */
    public ViberPlusSmsClient(String apiKey) {
        super(apiKey);
    }

    /** 
     * Sends Viber message or SMS message when transactional Viber message was not delivered
     * 
     * @param message Viber plus SMS message to send
     * @return Id of the sent Viber plus SMS message
     * @throws IOException if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    public long sendMessage(ViberPlusSmsMessage message) throws IOException, ViberException {
        return super.sendMessage(message);
    }
    
    /** 
     * Returns Viber message status
     * 
     * @param messageId Id of the Viber message (sent in the last 5 days)
     * @return Viber plus SMS message receipt object
     * @throws IOException if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    public ViberPlusSmsMessageReceipt getMessageStatus(long messageId) throws IOException, ViberException {
        String responseContent = getMessageStatusResponseContent(messageId);
        return new Gson().fromJson(responseContent, ViberPlusSmsMessageReceipt.class);
    }
}
