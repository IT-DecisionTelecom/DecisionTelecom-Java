package com.decisiontelecom.models;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents SMS error code
 */
public enum SmsErrorCode {
    INVALID_NUMBER(40),
    INCORRECT_SENDER(41),
    INVALID_MESSAGE_ID(42),
    INCORRECT_JSON(43),
    INVALID_LOGIN_OR_PASSWORD(44),
    USER_LOCKED(45),
    EMPTY_TEXT(46),
    EMPTY_LOGIN(47),
    EMPTY_PASSWORD(48),
    NOT_ENOUGH_MONEY(49),
    AUTHORIZATION_ERROR(50),
    INVALID_PHONE_NUMBER(51);

    private final int code;

    private SmsErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SmsErrorCode parse(int code) {
        Optional<SmsErrorCode> errorCode = Arrays.stream(SmsErrorCode.values())
                .filter(c -> c.code == code)
                .findFirst();

        return errorCode.get();
    }
}
