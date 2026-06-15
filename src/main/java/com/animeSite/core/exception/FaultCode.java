package com.animeSite.core.exception;

import java.io.Serializable;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public interface FaultCode extends Serializable {
    String getKey();
    ReloadableResourceBundleMessageSource getBundle();
}
