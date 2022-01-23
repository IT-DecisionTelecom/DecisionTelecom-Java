package com.decisiontelecom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.decisiontelecom.exceptions.ViberException;
import com.decisiontelecom.models.ViberError;
import com.decisiontelecom.models.ViberMessage;
import com.decisiontelecom.models.ViberMessageReceipt;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Client to work with Viber messages
 */
public class ViberClient {
    protected final String BASE_URL = "https://web.it-decision.com/v1/api";
    protected final String MESSAGE_ID_PROPERTY_NAME = "message_id";

    private final String apiKey;

    /**
     * Creates a new instance of the ViberClient class
     * 
     * @param apiKey User access key
     */
    public ViberClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return User access key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sends Viber message
     * 
     * @param message Viber message to send
     * @return Id of the sent Viber message
     * @throws IOException    if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    public long sendMessage(ViberMessage message) throws IOException, ViberException {
        String url = String.format("%s/send-viber", BASE_URL);
        String responseContent = makeRequest(url, message);

        JsonObject jsonObject = JsonParser.parseString(responseContent).getAsJsonObject();
        return jsonObject.get(MESSAGE_ID_PROPERTY_NAME).getAsLong();
    }

    /**
     * Returns Viber message status
     * 
     * @param messageId Id of the Viber message (sent in the last 5 days)
     * @return Viber message receipt object
     * @throws IOException    if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    public ViberMessageReceipt getMessageStatus(long messageId) throws IOException, ViberException {
        String responseContent = getMessageStatusResponseContent(messageId);
        return new Gson().fromJson(responseContent, ViberMessageReceipt.class);
    }

    /**
     * @param messageId Id of the Viber message (sent in the last 5 days)
     * @return Content of the HTTP response
     * @throws IOException    if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    protected String getMessageStatusResponseContent(long messageId) throws IOException, ViberException {
        String url = String.format("%s/receive-viber", BASE_URL);

        Map<String, Long> request = new HashMap<String, Long>();
        request.put(MESSAGE_ID_PROPERTY_NAME, messageId);

        return makeRequest(url, request);
    }

    /**
     * @param url     Request URL
     * @param request Request body
     * @return Content of the HTTP response
     * @throws IOException    if I/O exception of some sort occurred
     * @throws ViberException if specific Viber error occurred
     */
    private String makeRequest(String url, Object request) throws IOException, ViberException {
        BufferedReader inputReader = null;
        HttpURLConnection connection = null;
        String accessKeyBase64 = Base64.getEncoder().encodeToString(apiKey.getBytes());

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + accessKeyBase64);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            Gson gson = new Gson();
            wr.writeBytes(gson.toJson(request));
            wr.close();

            // Process unsuccessful response status codes
            if (connection.getResponseCode() < 200 || connection.getResponseCode() >= 300) {
                throw new IOException(
                        String.format("An error occurred while processing request. Response code: %d (%s)",
                                connection.getResponseCode(),
                                connection.getResponseMessage()));
            }

            // Get Response
            inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = inputReader.readLine()) != null) {
                response.append(line);
            }

            String responseContent = response.toString();

            // If response contains "name", "message", "code" and "status" words, treat it
            // as a ViberError
            if (responseContent.contains("name") && responseContent.contains("message") &&
                    responseContent.contains("code") && responseContent.contains("status")) {
                ViberError error = new Gson().fromJson(responseContent, ViberError.class);
                throw new ViberException(error);
            }

            return responseContent;
        } finally {
            if (inputReader != null) {
                inputReader.close();
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
