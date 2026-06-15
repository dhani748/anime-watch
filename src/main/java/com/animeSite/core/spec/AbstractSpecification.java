package com.animeSite.core.spec;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.io.Serializable;

public abstract class AbstractSpecification<T> implements Specification<T>, Serializable {

    protected String getProperty(String property) {
        int dot = property.lastIndexOf('.');
        return dot > -1 ? property.substring(dot + 1) : property;
    }

    protected From<?, ?> getRoot(String property, Root<T> root) {
        From<?, ?> from = root;
        if (property.contains(".")) {
            String[] parts = property.split("\\.");
            for (int i = 0; i < parts.length - 1; i++) {
                from = from.join(parts[i], JoinType.LEFT);
            }
        }
        return from;
    }
}
