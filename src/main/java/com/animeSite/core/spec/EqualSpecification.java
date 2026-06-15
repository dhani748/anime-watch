package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EqualSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final Object value;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (value == null) return cb.isNull(getRoot(field, root).get(getProperty(field)));
        return cb.equal(getRoot(field, root).get(getProperty(field)), value);
    }
}
