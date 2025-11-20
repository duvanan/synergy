package org.example.synergy.service.impl;

import org.example.synergy.entity.WorkflowConfig;
import org.springframework.data.jpa.domain.Specification;

public class WorkflowConfigSpecification {

    public static Specification<WorkflowConfig> filter(String name, Long documentTypeId, Integer maxSla) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (name != null && !name.isEmpty()) {
                predicates = cb.and(predicates, cb.like(root.get("name"), "%" + name + "%"));
            }

            if (documentTypeId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("documentTypeId"), documentTypeId));
            }

            if (maxSla != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("maxSla"), maxSla));
            }

            return predicates;
        };
    }
}
