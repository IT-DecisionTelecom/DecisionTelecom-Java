package com.decisiontelecom;

import java.io.*;
import java.net.*;

import com.decisiontelecom.exceptions.SmsException;
import com.decisiontelecom.models.SmsBalance;
import com.decisiontelecom.models.SmsErrorCode;
import com.decisiontelecom.models.SmsMessage;
import com.decisiontelecom.models.SmsMessageStatus;
import com.google.gson.Gson;

/**
 * Client to work with SMS messages
 */
public class SmsClient {
    private final String baseUrl = "https://web.it-decision.com/ru/js";
    private final String errorPropertyName = "error";
    private final String messageIdPropertyName = "msgid";
    private final String statusPropertyName = "status";

    private final String login;
    private final String password;

    /**
     * @return String User login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return String User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Creates new instance of the SmsClient class
     * 
     * @param login    Login in the system
     * @param password Password in the system
     */
    public SmsClient(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Sends SMS message
     * 
     * @param message SMS message to send
     * @return The Id of the submitted SMS message
     * @throws IOException  if I/O exception of some sort occurred
     * @throws SmsException if specific SMS error occurred
     */
    public long sendMessage(SmsMessage message) throws IOException, SmsException {
        String url = String.format("%s/send?login=%s&password=%s&phone=%s&sender=%s&text=%s&dlr=%d",
                baseUrl, login, password,
                URLEncoder.encode(message.getReceiverPhone(), "UTF-8"),
                URLEncoder.encode(message.getSender(), "UTF-8"),
                URLEncoder.encode(message.getText(), "UTF-8"),
                message.isDelivery() ? 1 : 0);

        String response = makeRequest(url);
        String msgIdValue = getValueFromListResponseContent(response, messageIdPropertyName);
        return Long.parseLong(msgIdValue);
    }

    /**
     * Returns SMS message delivery status
     * 
     * @param messageId The Id of the submitted SMS message
     * @return SMS message delivery status
     * @throws IOException  if I/O exception of some sort occurred
     * @throws SmsException if specific SMS error occurred
     */
    public SmsMessageStatus getMessageStatus(long messageId) throws IOException, SmsException {
        String url = String.format("%s/state?login=%s&password=%s&msgid=%d", baseUrl, login, password, messageId);
        String response = makeRequest(url);

        String statusValue = getValueFromListResponseContent(response, statusPropertyName);
        return statusValue == null || statusValue.trim().isEmpty()
                ? SmsMessageStatus.UNKNOWN
                : SmsMessageStatus.parse(Integer.parseInt(statusValue));
    }

    /**
     * Returns balance information
     * 
     * @return User SMS balance information
     * @throws IOException  if I/O exception of some sort occurred
     * @throws SmsException if specific SMS error occurred
     */
    public SmsBalance getBalance() throws IOException, SmsException {
        String requestUrl = String.format("%s/balance?login=%s&password=%s", baseUrl, login, password);
        String response = makeRequest(requestUrl);

        // replace symbols in the response body so it's possible to parse it as json
        // regexp removes quotation marks ("") around the numbers, so they could be
        // parsed as float
        String regex = "\"([-+]?[0-9]*\\.?[0-9]+)\"";
        String replacedContent = response.replace("[", "{").replace("]", "}").replaceAll(regex, "$1");

        Gson gson = new Gson();
        return gson.fromJson(replacedContent, SmsBalance.class);
    }

    /**
     * @param url
     * @return String
     * @throws IOException  if I/O exception of some sort occurred
     * @throws SmsException if specific SMS error occurred
     */
    private String makeRequest(String url) throws IOException, SmsException {
        BufferedReader inputReader = null;
        HttpURLConnection connection = null;

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");

            inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer responseContent = new StringBuffer();
            while ((inputLine = inputReader.readLine()) != null) {
                responseContent.append(inputLine);
            }

            String responseString = responseContent.toString();

            if (responseString.contains(errorPropertyName)) {
                int errorCode = Integer.parseInt(getValueFromListResponseContent(responseString, errorPropertyName));
                throw new SmsException(SmsErrorCode.parse(errorCode));
            }

            return responseString;
        } finally {
            if (inputReader != null) {
                inputReader.close();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * @param responseContent
     * @param keyPropertyName
     * @return String
     */
    private static String getValueFromListResponseContent(String responseContent, String keyPropertyName) {
        String[] split = responseContent.replace("[", "").replace("]", "").split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replace("\"", "");
        }

        if (!split[0].equalsIgnoreCase(keyPropertyName)) {
            throw new IllegalArgumentException(String.format("Invalid response: unknown key '%s'", split[0]));
        }

        return split[1];
    }
}
