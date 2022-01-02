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
import com.decisiontelecom.models.ViberMessageStatus;
import com.decisiontelecom.models.ViberPlusSmsMessage;
import com.decisiontelecom.models.ViberPlusSmsMessageReceipt;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ URL.class, ViberPlusSmsClient.class })
public class ViberPlusSmsClientTest {
    private ViberPlusSmsClient viberPlusSmsClient;

    @Before
    public void setUp() {
        viberPlusSmsClient = new ViberPlusSmsClient("apiKey");
    }

    @Test
    public void testSendMessageReturnsMessageId() throws Exception {
        int expectedMessageId = 429;
        String responseContent = String.format("{\"message_id\":%d}", expectedMessageId);

        setupHttpResponse(responseContent, 200, "OK");

        long messageId = viberPlusSmsClient.sendMessage(new ViberPlusSmsMessage());

        assertEquals(expectedMessageId, messageId);
    }

    @Test
    public void testSendMessageReturnsError() throws Exception {
        ViberError expectedError = new ViberError("Invalid Parameter: source_addr", "Empty parameter or parameter validation error", 1, 400);
        String responseContent = new Gson().toJson(expectedError);

        setupHttpResponse(responseContent, 200, "OK");

        try {
            viberPlusSmsClient.sendMessage(new ViberPlusSmsMessage());
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
        ViberError expectedError = new ViberError("Unauthorized", 401);
        String responseContent = new Gson().toJson(expectedError);

        setupHttpResponse(responseContent, 401, "Unauthorized");

        try {
            viberPlusSmsClient.sendMessage(new ViberPlusSmsMessage());
        } catch (ViberException e) {
            ViberError error = e.getError();
            assertTrue(expectedError.getName().equalsIgnoreCase(error.getName()));
            assertTrue(expectedError.getStatus() == error.getStatus());
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

        ViberPlusSmsMessageReceipt receipt = viberPlusSmsClient.getMessageStatus(messageId);

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
            viberPlusSmsClient.getMessageStatus(429);
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
        ViberError expectedError = new ViberError("Unauthorized", 401);
        String responseContent = new Gson().toJson(expectedError);

        setupHttpResponse(responseContent, 401, "Unauthorized");

        try {
            viberPlusSmsClient.getMessageStatus(429);
        } catch (ViberException e) {
            ViberError error = e.getError();
            assertTrue(expectedError.getName().equalsIgnoreCase(error.getName()));
            assertTrue(expectedError.getStatus() == error.getStatus());
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
