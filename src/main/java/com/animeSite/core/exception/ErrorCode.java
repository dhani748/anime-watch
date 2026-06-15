package com.animeSite.core.exception;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public enum ErrorCode implements FaultCode {
    AUTH_0001, AUTH_0002, AUTH_0003, AUTH_0004, AUTH_0005,
    AUTH_0006, AUTH_0007, AUTH_0008, AUTH_0009, AUTH_0010,
    USER_0001, USER_0002, USER_0003,
    ANIME_0001, ANIME_0002, ANIME_0003,
    REVIEW_0001, REVIEW_0002, REVIEW_0003,
    NEWS_0001, NEWS_0002,
    GEN_0001, GEN_0002, GEN_0003;

    private static final ReloadableResourceBundleMessageSource RESOURCE_BUNDLE;

    static {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/error-messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        source.setFallbackToSystemLocale(false);
        RESOURCE_BUNDLE = source;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public ReloadableResourceBundleMessageSource getBundle() {
        return RESOURCE_BUNDLE;
    }
}
