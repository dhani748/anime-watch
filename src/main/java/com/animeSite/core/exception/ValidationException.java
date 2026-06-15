package com.animeSite.core.exception;

public class ValidationException extends AbstractException {
    public ValidationException(Throwable cause, FaultCode faultCode, Object... messageArgs) {
        super(cause, faultCode, messageArgs);
    }

    public ValidationException(FaultCode faultCode, Object... messageArgs) {
        super(faultCode, messageArgs);
    }
}
