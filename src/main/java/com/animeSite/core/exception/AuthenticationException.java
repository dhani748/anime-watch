package com.animeSite.core.exception;

public class AuthenticationException extends AbstractException {
    public AuthenticationException(Throwable cause, FaultCode faultCode, Object... messageArgs) {
        super(cause, faultCode, messageArgs);
    }

    public AuthenticationException(FaultCode faultCode, Object... messageArgs) {
        super(faultCode, messageArgs);
    }
}
