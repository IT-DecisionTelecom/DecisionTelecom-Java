package com.decisiontelecom.exceptions;

import com.decisiontelecom.models.SmsErrorCode;

public class SmsException extends Exception {
    private final SmsErrorCode errorCode;

    public SmsException(SmsErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SmsException(String message, SmsErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SmsException(Throwable cause, SmsErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SmsException(String message, Throwable cause, SmsErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SmsErrorCode getErrorCode() {
        return errorCode;
    }
}
