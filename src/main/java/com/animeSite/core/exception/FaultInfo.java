package com.animeSite.core.exception;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class FaultInfo implements Serializable {
    private String errorClass;
    private String errorMessage;
    private String faultCode;
    private String message;
}
