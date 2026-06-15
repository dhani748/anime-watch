package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;

public class PredicateBuilder<T> {

    public enum Operator { AND, OR }

    private final List<Specification<T>> specs = new ArrayList<>();
    private final Operator operator;

    PredicateBuilder(Operator operator) {
        this.operator = operator;
    }

    public PredicateBuilder<T> eq(String field, Object value) {
        return eq(true, field, value);
    }

    public PredicateBuilder<T> eq(boolean condition, String field, Object value) {
        if (condition) specs.add(new EqualSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> ne(String field, Object value) {
        return ne(true, field, value);
    }

    public PredicateBuilder<T> ne(boolean condition, String field, Object value) {
        if (condition) specs.add(new NotEqualSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> gt(String field, Comparable<?> value) {
        return gt(true, field, value);
    }

    public PredicateBuilder<T> gt(boolean condition, String field, Comparable<?> value) {
        if (condition) specs.add(new GtSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> ge(String field, Comparable<?> value) {
        return ge(true, field, value);
    }

    public PredicateBuilder<T> ge(boolean condition, String field, Comparable<?> value) {
        if (condition) specs.add(new GeSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> lt(String field, Comparable<?> value) {
        return lt(true, field, value);
    }

    public PredicateBuilder<T> lt(boolean condition, String field, Comparable<?> value) {
        if (condition) specs.add(new LtSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> le(String field, Comparable<?> value) {
        return le(true, field, value);
    }

    public PredicateBuilder<T> le(boolean condition, String field, Comparable<?> value) {
        if (condition) specs.add(new LeSpecification<>(field, value));
        return this;
    }

    public PredicateBuilder<T> between(String field, Comparable<?> min, Comparable<?> max) {
        return between(true, field, min, max);
    }

    public PredicateBuilder<T> between(boolean condition, String field, Comparable<?> min, Comparable<?> max) {
        if (condition) specs.add(new BetweenSpecification<>(field, min, max));
        return this;
    }

    public PredicateBuilder<T> like(String field, String pattern) {
        return like(true, field, pattern);
    }

    public PredicateBuilder<T> like(boolean condition, String field, String pattern) {
        if (condition && pattern != null && !pattern.isEmpty()) specs.add(new LikeSpecification<>(field, pattern));
        return this;
    }

    public PredicateBuilder<T> notLike(String field, String pattern) {
        return notLike(true, field, pattern);
    }

    public PredicateBuilder<T> notLike(boolean condition, String field, String pattern) {
        if (condition) specs.add(new NotLikeSpecification<>(field, pattern));
        return this;
    }

    public PredicateBuilder<T> in(String field, Collection<?> values) {
        return in(true, field, values);
    }

    public PredicateBuilder<T> in(boolean condition, String field, Collection<?> values) {
        if (condition && values != null && !values.isEmpty()) specs.add(new InSpecification<>(field, values));
        return this;
    }

    public PredicateBuilder<T> notIn(String field, Collection<?> values) {
        return notIn(true, field, values);
    }

    public PredicateBuilder<T> notIn(boolean condition, String field, Collection<?> values) {
        if (condition && values != null && !values.isEmpty()) specs.add(new NotInSpecification<>(field, values));
        return this;
    }

    public PredicateBuilder<T> searchInFields(String text, String... fields) {
        if (text != null && !text.trim().isEmpty()) {
            List<Specification<T>> likeSpecs = new ArrayList<>();
            String pattern = "%" + text.toLowerCase() + "%";
            for (String field : fields) {
                likeSpecs.add((root, query, cb) -> cb.like(cb.lower(root.get(field)), pattern));
            }
            specs.add(Specification.where(likeSpecs.stream().reduce(Specification::or).orElse(null)));
        }
        return this;
    }

    public PredicateBuilder<T> predicate(Specification<T> spec) {
        if (spec != null) specs.add(spec);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Specification<T> build() {
        if (specs.isEmpty()) return Specification.where(null);
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            if (operator == Operator.AND) {
                result = result.and(specs.get(i));
            } else {
                Specification<T> finalResult = result;
                Specification<T> s = specs.get(i);
                result = (root, query, cb) -> cb.or(finalResult.toPredicate(root, query, cb), s.toPredicate(root, query, cb));
            }
        }
        return result;
    }
}
