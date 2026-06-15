package com.animeSite.core.spec;

import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;

public class Sorts {
    private final List<Sort.Order> orders = new ArrayList<>();

    private Sorts() {}

    public static Sorts builder() {
        return new Sorts();
    }

    public Sorts asc(String property) {
        return asc(true, property);
    }

    public Sorts asc(boolean condition, String property) {
        if (condition) orders.add(Sort.Order.asc(property));
        return this;
    }

    public Sorts desc(String property) {
        return desc(true, property);
    }

    public Sorts desc(boolean condition, String property) {
        if (condition) orders.add(Sort.Order.desc(property));
        return this;
    }

    public Sort build() {
        return Sort.by(orders);
    }

    public Sort buildIfAny() {
        return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
    }
}
