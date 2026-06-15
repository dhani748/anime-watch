package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final Comparable<?> value;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(getRoot(field, root).get(getProperty(field)), (Comparable) value);
    }
}
