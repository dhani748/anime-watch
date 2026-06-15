package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BetweenSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final Comparable<?> min;
    private final Comparable<?> max;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.between(getRoot(field, root).get(getProperty(field)), (Comparable) min, (Comparable) max);
    }
}
