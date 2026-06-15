package com.animeSite.core.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
    private final FaultCode faultCode;
    private final Object[] messageArgs;
    private final FaultInfo faultInfo;

    public AbstractException(Throwable cause, FaultCode faultCode, Object... messageArgs) {
        super(cause);
        this.faultCode = faultCode;
        this.messageArgs = messageArgs;
        this.faultInfo = buildFaultInfo();
    }

    public AbstractException(FaultCode faultCode, Object... messageArgs) {
        super();
        this.faultCode = faultCode;
        this.messageArgs = messageArgs;
        this.faultInfo = buildFaultInfo();
    }

    private FaultInfo buildFaultInfo() {
        FaultInfo info = new FaultInfo();
        info.setFaultCode(faultCode.getKey().replace("_", "-"));
        info.setErrorMessage(getErrorMessage());
        info.setErrorClass(this.getClass().getSimpleName());
        info.setMessage(getMessage());
        return info;
    }

    @Override
    public String getMessage() {
        return ServiceCodeHelper.getMessage(faultCode, messageArgs);
    }

    public String getErrorMessage() {
        return getMessage();
    }

    public String getErrorCode() {
        return faultCode.getKey().replace("_", "-");
    }

    @Override
    public String toString() {
        return getErrorMessage();
    }
}
