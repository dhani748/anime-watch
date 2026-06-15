package com.animeSite.core.exception;

public class BusinessException extends AbstractException {
    public BusinessException(Throwable cause, FaultCode faultCode, Object... messageArgs) {
        super(cause, faultCode, messageArgs);
    }

    public BusinessException(FaultCode faultCode, Object... messageArgs) {
        super(faultCode, messageArgs);
    }
}
