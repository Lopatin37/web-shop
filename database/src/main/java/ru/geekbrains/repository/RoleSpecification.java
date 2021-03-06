package ru.geekbrains.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Role;

public class RoleSpecification {
    public static Specification<Role> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal((true)));
    }

    public static Specification<Role> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
}
