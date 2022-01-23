package examples;

import java.io.IOException;

import com.decisiontelecom.exceptions.SmsException;
import com.decisiontelecom.exceptions.ViberException;
import com.decisiontelecom.models.SmsBalance;
import com.decisiontelecom.models.SmsMessage;
import com.decisiontelecom.models.SmsMessageStatus;
import com.decisiontelecom.models.ViberError;
import com.decisiontelecom.models.ViberMessage;
import com.decisiontelecom.models.ViberMessageReceipt;
import com.decisiontelecom.models.ViberMessageSourceType;
import com.decisiontelecom.models.ViberMessageType;
import com.decisiontelecom.models.ViberPlusSmsMessage;
import com.decisiontelecom.models.ViberPlusSmsMessageReceipt;

import com.decisiontelecom.SmsClient;
import com.decisiontelecom.ViberClient;
import com.decisiontelecom.ViberPlusSmsClient;

public class App {
    public static void main(String[] args) throws IOException {
        smsSendMessage();
        smsGetMessageStatus();
        smsGetBalance();

        viberSendTransactionalMessage();
        viberSendPromotionalMessage();
        viberGetMessageStatus();

        viberPlusSmsSendTransactionalMessage();
        viberPlusSmsGetMessageStatus();
    }

    private static void smsSendMessage() {
        try {
            // Create new instance of the SMS client.
            SmsClient smsClient = new SmsClient("<YOUR_LOGIN>", "<YOUR_PASSWORD>");

            // Create SMS message object
            SmsMessage smsMessage = new SmsMessage("380504444444", "380504444444", "SomeMessage", true);

            // Call client SendMessage method to send SMS message.
            long messageId = smsClient.sendMessage(smsMessage);

            // SendMessage method should return Id of the sent SMS message.
            System.out.println(messageId);
        } catch (SmsException e) {
            // SmsException contains specific DecisionTelecom error with the code of what went wrong during the operation.
            System.out.printf("Error while sending SMS message. Error code: %d (%s)\n",
                    e.getErrorCode().getCode(), e.getErrorCode().toString());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while sending SMS message.");
            e.printStackTrace();
        }
    }

    private static void smsGetMessageStatus() {
        try {
            // Create new instance of the SMS client.
            SmsClient smsClient = new SmsClient("<YOUR_LOGIN>", "<YOUR_PASSWORD>");

            // Call client GetMessageStatus method to get SMS message status.
            SmsMessageStatus messageStatus = smsClient.getMessageStatus(31885463);

            // GetMessageStatus method should return status of the sent SMS message.
            System.out.printf("Message status: %d (%s)\n", messageStatus.getStatus(), messageStatus.name());
        } catch (SmsException e) {
            // SmsException contains specific DecisionTelecom error with the code of what went wrong during the operation.
            System.out.printf("Error while getting SMS message status. Error code: %d (%s)\n",
                    e.getErrorCode().getCode(), e.getErrorCode().toString());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while getting SMS message status.");
            e.printStackTrace();
        }
    }

    private static void smsGetBalance() {
        try {
            // Create new instance of the SMS client.
            SmsClient smsClient = new SmsClient("<YOUR_LOGIN>", "<YOUR_PASSWORD>");

            // Call client GetBalance method to get SMS balance information.
            SmsBalance balance = smsClient.getBalance();

            // GetBalance method should return SMS balance information.
            System.out.printf("Balance information: Balance: %f, Credit: %f, Currency: %s\n",
                    balance.getBalanceAmount(), balance.getCreditAmount(), balance.getCurrency());
        } catch (SmsException e) {
            // SmsException contains specific DecisionTelecom error with the code of what went wrong during the operation.
            System.out.printf("Error while getting SMS balance information. Error code: %d (%s)\n",
                    e.getErrorCode().getCode(), e.getErrorCode().toString());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while getting SMS balance information.");
            e.printStackTrace();
        }
    }

    private static void viberSendTransactionalMessage() {
        try {
            // Create new instance of the viber client.
            ViberClient viberClient = new ViberClient("<YOUR_ACCESS_KEY>");

            // Create viber message object. This one will be transactional message with
            // message text only.
            ViberMessage viberMessage = new ViberMessage();
            viberMessage.setSender("380504444444");
            viberMessage.setReceiver("380504444444");
            viberMessage.setText("Viber message");
            viberMessage.setMessageType(ViberMessageType.TEXT_ONLY);
            viberMessage.setSourceType(ViberMessageSourceType.TRANSACTIONAL);
            viberMessage.setValidityPeriod(3600);

            // Call client SendMessage method to send viber message.
            long messageId = viberClient.sendMessage(viberMessage);

            // SendMessage method should return Id of the sent Viber message.
            System.out.printf("Message Id: %d\n", messageId);
        } catch (ViberException e) {
            // ViberException contains specific DecisionTelecom error with details of what
            // went wrong during the operation.
            ViberError error = e.getError();

            System.out.printf(
                    "Error while sending Viber message.\nError name: %s\nError message: %s\nError code: %d\nError status: %d\n",
                    error.getName(), error.getMessage(), error.getCode(), error.getStatus());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while sending Viber message.");
            e.printStackTrace();
        }
    }

    private static void viberSendPromotionalMessage() {
        try {
            // Create new instance of the viber client.
            ViberClient viberClient = new ViberClient("<YOUR_ACCESS_KEY>");

            // Create viber plus SMS message object. This one will be promotional message
            // with message text, image and button..
            ViberMessage viberMessage = new ViberMessage();
            viberMessage.setSender("380504444444");
            viberMessage.setReceiver("380504444444");
            viberMessage.setText("Viber message");
            viberMessage.setMessageType(ViberMessageType.TEXT_IMAGE_BUTTON);
            viberMessage.setSourceType(ViberMessageSourceType.PROMOTIONAL);
            viberMessage.setImageUrl("https://yourdomain.com/images/image.jpg");
            viberMessage.setButtonCaption("Join Us");
            viberMessage.setButtonAction("https://yourdomain.com/join-us");
            viberMessage.setValidityPeriod(3600);

            // Call client SendMessage method to send viber message.
            long messageId = viberClient.sendMessage(viberMessage);

            // SendMessage method should return Id of the sent Viber message.
            System.out.printf("Message Id: %d\n", messageId);
        } catch (ViberException e) {
            // ViberException contains specific DecisionTelecom error with details of what
            // went wrong during the operation.
            ViberError error = e.getError();

            System.out.printf(
                    "Error while sending Viber message.\nError name: %s\nError message: %s\nError code: %d\nError status: %d\n",
                    error.getName(), error.getMessage(), error.getCode(), error.getStatus());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while sending Viber message.");
            e.printStackTrace();
        }
    }

    private static void viberGetMessageStatus() {
        try {
            // Create new instance of the viber client.
            ViberClient viberClient = new ViberClient("<YOUR_ACCESS_KEY>");

            // Call client GetMessageStatus method to get viber message status.
            ViberMessageReceipt receipt = viberClient.getMessageStatus(380752);

            // GetMessageStatus method should return status of the sent Viber message.
            System.out.printf("Viber message status: %d (%s)\n", receipt.getViberMessageStatus().getStatus(),
                    receipt.getViberMessageStatus().name());
        } catch (ViberException e) {
            // ViberException contains specific DecisionTelecom error with details of what
            // went wrong during the operation.
            ViberError error = e.getError();
            System.out.printf(
                    "Error while getting Viber message status.\nError name: %s\nError message: %s\nError code: %d\nError status: %d\n",
                    error.getName(), error.getMessage(), error.getCode(), error.getStatus());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while getting Viber message status.");
            e.printStackTrace();
        }
    }

    private static void viberPlusSmsSendTransactionalMessage() {
        try {
            // Create new instance of the viber plus SMS client.
            ViberPlusSmsClient viberPlusSmsClient = new ViberPlusSmsClient("<YOUR_ACCESS_KEY>");

            // Create viber plus SMS message object. This one will be transactional message
            // with message text only.
            ViberPlusSmsMessage message = new ViberPlusSmsMessage();
            message.setSender("380504444444");
            message.setReceiver("380504444444");
            message.setText("Viber message");
            message.setMessageType(ViberMessageType.TEXT_ONLY);
            message.setSourceType(ViberMessageSourceType.TRANSACTIONAL);
            message.setValidityPeriod(3600);
            message.setSmsText("SMS Text");

            // Call client SendMessage method to send viber plus SMS message.
            long messageId = viberPlusSmsClient.sendMessage(message);

            // SendMessage method should return Id of the sent Viber plus SMS message.
            System.out.printf("Message Id: %d\n", messageId);
        } catch (ViberException e) {
            // ViberException contains specific DecisionTelecom error with details of what
            // went wrong during the operation.
            ViberError error = e.getError();

            System.out.printf(
                    "Error while sending Viber plus SMS message.\nError name: %s\nError message: %s\nError code: %d\nError status: %d\n",
                    error.getName(), error.getMessage(), error.getCode(), error.getStatus());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while sending Viber plus SMS message.");
            e.printStackTrace();
        }
    }

    private static void viberPlusSmsGetMessageStatus() {
        try {
            // Create new instance of the viber plus SMS client.
            ViberPlusSmsClient viberPlusSmsClient = new ViberPlusSmsClient("<YOUR_ACCESS_KEY>");

            // Call client GetMessageStatus method to get viber plus SMS message status.
            ViberPlusSmsMessageReceipt receipt = viberPlusSmsClient.getMessageStatus(380752);

            // GetMessageStatus method should return status of the sent Viber plus SMS
            // message.
            System.out.printf("Viber message Id: %d\nviber message status: %d (%s)\n",
                    receipt.getViberMessageId(), receipt.getViberMessageStatus().getStatus(),
                    receipt.getViberMessageStatus().name());

            if (receipt.getSmsMessageStatus() != null) {
                System.out.printf("SMS message Id: %d\nSMS message status: %d (%s)\n",
                        receipt.getSmsMessageId(), receipt.getSmsMessageStatus().getStatus(),
                        receipt.getSmsMessageStatus().name());
            }
        } catch (ViberException e) {
            // ViberException contains specific DecisionTelecom error with details of what
            // went wrong during the operation.
            ViberError error = e.getError();
            System.out.printf(
                    "Error while getting Viber message status.\nError name: %s\nError message: %s\nError code: %d\nError status: %d\n",
                    error.getName(), error.getMessage(), error.getCode(), error.getStatus());
        } catch (Exception e) {
            // A non-DecisionTelecom error occurred during the operation (like connection
            // error).
            System.out.println("Error while getting Viber message status.");
            e.printStackTrace();
        }
    }
}
