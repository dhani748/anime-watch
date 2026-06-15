package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotLikeSpecification<T> extends AbstractSpecification<T> {
    private final String field;
    private final String pattern;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String likePattern = pattern.contains("%") ? pattern : "%" + pattern + "%";
        return cb.notLike(cb.lower(getRoot(field, root).get(getProperty(field))), likePattern.toLowerCase());
    }
}
