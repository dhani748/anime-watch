package com.animeSite.core.spec;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Specifications {
    public static <T> PredicateBuilder<T> and() {
        return new PredicateBuilder<>(PredicateBuilder.Operator.AND);
    }

    public static <T> PredicateBuilder<T> or() {
        return new PredicateBuilder<>(PredicateBuilder.Operator.OR);
    }
}
