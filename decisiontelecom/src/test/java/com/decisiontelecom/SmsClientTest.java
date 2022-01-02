package com.decisiontelecom;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.decisiontelecom.exceptions.SmsException;
import com.decisiontelecom.models.SmsBalance;
import com.decisiontelecom.models.SmsErrorCode;
import com.decisiontelecom.models.SmsMessage;
import com.decisiontelecom.models.SmsMessageStatus;

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
@PrepareForTest({ URL.class, SmsClient.class })
public class SmsClientTest 
{
    private SmsClient smsClient;

    @Before
    public void setUp() {
        smsClient = new SmsClient("login", "password");
    }

    @Test
    public void testSendMessageReturnsMessageId() throws Exception {
        long expectedMessageId = 31885463l;
        String responseContent = String.format("[\"msgid\",\"%d\"]", expectedMessageId);

        setupHttpResponse(responseContent);

        long messageId = smsClient.sendMessage(new SmsMessage("receiverPhone", "sender", "text", true));

        assertEquals(expectedMessageId, messageId);
    }

    @Test
    public void testSendMessageReturnsError() throws Exception {
        int expectedErrorCode = SmsErrorCode.INCORRECT_SENDER.getCode();
        String responseContent = String.format("[\"error\",%d]", expectedErrorCode);

        setupHttpResponse(responseContent);

        try {
            smsClient.sendMessage(new SmsMessage("receiverPhone", "sender", "text", true));
        } 
        catch (SmsException ex) {
            assertEquals(expectedErrorCode, ex.getErrorCode().getCode());
        }
    }

    @Test
    public void testGetMessageStatusReturnsStatusCode() throws Exception {
        SmsMessageStatus expectedStatus = SmsMessageStatus.DELIVERED;
        String responseContent = String.format("[\"status\",%d]", expectedStatus.getStatus());

        setupHttpResponse(responseContent);

        SmsMessageStatus messageStatus = smsClient.getMessageStatus(123l);

        assertEquals(expectedStatus, messageStatus);
    }

    @Test
    public void testGetMessageStatusReturnsStatusWithoutCode() throws Exception {
        SmsMessageStatus expectedStatus = SmsMessageStatus.UNKNOWN;
        String responseContent = String.format("[\"status\",\"\"]");

        setupHttpResponse(responseContent);

        SmsMessageStatus messageStatus = smsClient.getMessageStatus(123l);

        assertEquals(expectedStatus, messageStatus);
    }

    @Test
    public void testGetMessageStatusReturnsError() throws Exception {
        int expectedErrorCode = SmsErrorCode.INCORRECT_SENDER.getCode();
        String responseContent = String.format("[\"error\",%d]", expectedErrorCode);

        setupHttpResponse(responseContent);

        try {
            smsClient.getMessageStatus(123);
        } 
        catch (SmsException ex) {
            assertEquals(expectedErrorCode, ex.getErrorCode().getCode());
        }
    }

    @Test
    public void TestGetBalanceReturnsData() throws Exception {
        double expectedBalance = -791.8391870;
        double expectedCredit = 1000;
        String expectedCurrency = "EUR";

        String responseContent = String.format("[\"balance\":\"%f\",\"credit\":\"%f\",\"currency\":\"%s\"]", 
            expectedBalance, expectedCredit, expectedCurrency);
        setupHttpResponse(responseContent);

        SmsBalance balance = smsClient.getBalance();

        assertEquals(expectedBalance, balance.getBalanceAmount(), 0.00000001);
        assertEquals(expectedCredit, balance.getCreditAmount(), 0.00000001);
        assertEquals(expectedCurrency, balance.getCurrency());
    }

    @Test
    public void testGetBalanceReturnsError() throws Exception {
        int expectedErrorCode = SmsErrorCode.INCORRECT_SENDER.getCode();
        String resposneContent = String.format("[\"error\",%d]", expectedErrorCode);

        setupHttpResponse(resposneContent);

        try {
            smsClient.getBalance();
        } 
        catch (SmsException ex) {
            assertEquals(expectedErrorCode, ex.getErrorCode().getCode());
        }
    }

    private void setupHttpResponse(String responseContent) throws Exception {
        URL url = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withArguments(Mockito.anyString()).thenReturn(url);

        HttpURLConnection connection = PowerMockito.mock(HttpURLConnection.class);
		PowerMockito.when(url.openConnection()).thenReturn(connection);
        PowerMockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(responseContent.getBytes()));
    }
}
