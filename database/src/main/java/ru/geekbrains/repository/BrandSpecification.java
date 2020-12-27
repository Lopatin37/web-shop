package ru.geekbrains.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entity.Brand;

public class BrandSpecification  {
    public static Specification<Brand> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal((true)));
    }

    public static Specification<Brand> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
}
