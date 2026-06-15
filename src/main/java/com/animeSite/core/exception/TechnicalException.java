package com.animeSite.core.exception;

import lombok.Getter;

@Getter
public class TechnicalException extends AbstractException {
    private final String detailMessage;

    public TechnicalException(Throwable cause, FaultCode faultCode, Object... messageArgs) {
        super(cause, faultCode, messageArgs);
        this.detailMessage = null;
    }

    public TechnicalException(FaultCode faultCode, Object... messageArgs) {
        super(faultCode, messageArgs);
        this.detailMessage = null;
    }

    public TechnicalException(String message) {
        super(ErrorCode.GEN_0001);
        this.detailMessage = message;
    }

    public TechnicalException(String message, Throwable cause) {
        super(cause, ErrorCode.GEN_0001);
        this.detailMessage = message;
    }

    @Override
    public String getMessage() {
        return detailMessage != null ? detailMessage : super.getMessage();
    }
}
