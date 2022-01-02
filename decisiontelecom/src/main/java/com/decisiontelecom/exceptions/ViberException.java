package com.decisiontelecom.exceptions;

import com.decisiontelecom.models.ViberError;

public class ViberException extends Exception {
    private final ViberError error;

    public ViberException(ViberError error) {
        this.error = error;
    }

    public ViberException(String message, ViberError error) {
        super(message);
        this.error = error;
    }

    public ViberException(Throwable cause, ViberError error) {
        super(cause);
        this.error = error;
    }

    public ViberException(String message, Throwable cause, ViberError error) {
        super(message, cause);
        this.error = error;
    }

    public ViberError getError() {
        return error;
    }
}
