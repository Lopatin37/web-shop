package ru.geekbrains.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Product;

public class ProductSpecification {
    public static Specification<Product> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal((true)));
    }

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
}
