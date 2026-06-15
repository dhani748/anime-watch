package com.animeSite.core.exception;

import lombok.experimental.UtilityClass;
import java.util.Locale;

@UtilityClass
public class ServiceCodeHelper {
    private static final String CODE_MISSING_MSG = "Pair (Service code/message) missing in related bundle...";

    public static String getMessage(FaultCode faultCode, Object... args) {
        try {
            if (faultCode.getBundle() != null) {
                String msg = faultCode.getBundle().getMessage(faultCode.getKey(), args, Locale.getDefault());
                return msg != null ? msg : faultCode.getKey();
            }
        } catch (Exception ignored) {}
        return faultCode.getKey();
    }
}
