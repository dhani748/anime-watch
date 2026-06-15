package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotEqualSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final Object value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.notEqual(getRoot(field, root).get(getProperty(field)), value);
    }
}
