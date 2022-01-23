package com.decisiontelecom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.decisiontelecom.exceptions.ViberException;
import com.decisiontelecom.models.ViberError;
import com.decisiontelecom.models.ViberMessage;
import com.decisiontelecom.models.ViberMessageReceipt;
import com.decisiontelecom.models.ViberMessageStatus;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for SmsClient.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ URL.class, ViberClient.class })
public class ViberClientTest {
    private ViberClient viberClient;

    @Before
    public void setUp() {
        viberClient = new ViberClient("apiKey");
    }

    @Test
    public void testSendMessageReturnsMessageId() throws Exception {
        int expectedMessageId = 429;
        String responseContent = String.format("{\"message_id\":%d}", expectedMessageId);

        setupHttpResponse(responseContent, 200, "OK");

        long messageId = viberClient.sendMessage(new ViberMessage());

        assertEquals(expectedMessageId, messageId);
    }

    @Test
    public void testSendMessageReturnsError() throws Exception {
        ViberError expectedError = new ViberError("Invalid Parameter: source_addr", "Empty parameter or parameter validation error", 1, 400);
        String responseContent = new Gson().toJson(expectedError);

        setupHttpResponse(responseContent, 200, "OK");

        try {
            viberClient.sendMessage(new ViberMessage());
        } catch (ViberException e) {
            ViberError error = e.getError();
            assertTrue(expectedError.getName().equalsIgnoreCase(error.getName()));
            assertTrue(expectedError.getMessage().equalsIgnoreCase(error.getMessage()));
            assertTrue(expectedError.getCode() == error.getCode());
            assertTrue(expectedError.getStatus() == error.getStatus());
        }
    }

    @Test
    public void testSendMessageReturnsNotSuccessStatusCode() throws Exception {
        String expectedError = "An error occurred while processing request. Response code: 401 (Unauthorized)";

        setupHttpResponse("General error message", 401, "Unauthorized");

        try {
            viberClient.sendMessage(new ViberMessage());
        } catch (Exception e) {
            assertTrue(expectedError.equalsIgnoreCase(e.getMessage()));
        }
    }

    @Test
    public void testGetMessageStatusAsyncReturnsMessageId() throws Exception {
        int messageId = 429;
        ViberMessageStatus messageStatus = ViberMessageStatus.DELIVERED;

        Map<String, Integer> response = new HashMap<String, Integer>();
        response.put("message_id", messageId);
        response.put("status", messageStatus.getStatus());

        String responseContent = new Gson().toJson(response);
        setupHttpResponse(responseContent, 200, "OK");

        ViberMessageReceipt receipt = viberClient.getMessageStatus(messageId);

        assertNotNull(receipt);
        assertEquals(messageId, receipt.getViberMessageId());
        assertEquals(messageStatus, receipt.getViberMessageStatus());        
    }

    @Test
    public void testGetMessageStatusAsyncReturnsError() throws Exception {
        ViberError expectedError = new ViberError("Invalid Parameter: source_addr", "Empty parameter or parameter validation error", 1, 400);
        String responseContent = new Gson().toJson(expectedError);

        setupHttpResponse(responseContent, 200, "OK");

        try {
            viberClient.getMessageStatus(429);
        } catch (ViberException e) {
            ViberError error = e.getError();
            assertTrue(expectedError.getName().equalsIgnoreCase(error.getName()));
            assertTrue(expectedError.getMessage().equalsIgnoreCase(error.getMessage()));
            assertTrue(expectedError.getCode() == error.getCode());
            assertTrue(expectedError.getStatus() == error.getStatus());
        }  
    }

    @Test
    public void testGetMessageStatusReturnsNotSuccessStatusCode() throws Exception {
        String expectedError = "An error occurred while processing request. Response code: 401 (Unauthorized)";

        setupHttpResponse("General error", 401, "Unauthorized");

        try {
            viberClient.getMessageStatus(429);
        } catch (Exception e) {
            assertTrue(expectedError.equalsIgnoreCase(e.getMessage()));
        }
    }

    private void setupHttpResponse(String responseContent, int responseCode, String responseMessage) throws Exception {
        URL url = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withArguments(Mockito.anyString()).thenReturn(url);

        HttpURLConnection connection = PowerMockito.mock(HttpURLConnection.class);
		PowerMockito.when(url.openConnection()).thenReturn(connection);
        PowerMockito.when(connection.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        PowerMockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(responseContent.getBytes()));
        PowerMockito.when(connection.getResponseCode()).thenReturn(responseCode);
        PowerMockito.when(connection.getResponseMessage()).thenReturn(responseMessage);
    }
}
