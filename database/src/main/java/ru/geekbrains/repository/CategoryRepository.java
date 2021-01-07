package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
    Category findByIdLike(Long id);
}
