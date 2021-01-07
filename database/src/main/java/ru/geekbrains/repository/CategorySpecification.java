package ru.geekbrains.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Category;

public class CategorySpecification {
    public static Specification<Category> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal((true)));
    }

    public static Specification<Category> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
}
