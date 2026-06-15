package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import java.util.Collection;

@RequiredArgsConstructor
public class InSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final Collection<?> values;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return getRoot(field, root).get(getProperty(field)).in(values);
    }
}
