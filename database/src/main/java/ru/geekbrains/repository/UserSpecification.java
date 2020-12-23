package ru.geekbrains.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.User;

public class UserSpecification {
    public static Specification<User> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal((true)));
    }

    public static Specification<User> usernameLike(String username) {
        return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
    }
}
