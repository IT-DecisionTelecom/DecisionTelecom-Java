package com.decisiontelecom.models;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents SMS message status
 */
public enum SmsMessageStatus {
    UNKNOWN (0),
    DELIVERED (2),
    EXPIRED (3),
    UNDELIVERABLE (5),
    ACCEPTED (6);

    private final int status;

    private SmsMessageStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static SmsMessageStatus parse(int status) {
        Optional<SmsMessageStatus> messageStatus = Arrays.stream(SmsMessageStatus.values())
            .filter(c -> c.status == status)
            .findFirst();

        return messageStatus.get();
    }
}