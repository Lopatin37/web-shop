package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Category;
import ru.geekbrains.repository.CategoryRepository;
import ru.geekbrains.repository.CategorySpecification;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping("/all")
    public String all(Model model,
                                 @RequestParam(value = "name", required = false) String name,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<Category> categorySpecification = CategorySpecification.trueLiteral();
        if(name != null && !name.isEmpty()) {
            categorySpecification = categorySpecification.and(CategorySpecification.nameLike(name));
        }
        model.addAttribute("categories", categoryRepository.findAll(categorySpecification, pageRequest));
        return "categories";
    }

    @RequestMapping("/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        return "newcategory";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:all";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("category", categoryRepository.findByIdLike(id));
        return "editcategory";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("category") Category category) {
        Category categoryFromDB = categoryRepository.findByIdLike(category.getId());
        if(!categoryFromDB.getName().equals(category.getName())) {
            categoryFromDB.setName(category.getName());
        }
        categoryRepository.save(categoryFromDB);
        return "redirect:all";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(value = "id") Long id) {
        categoryRepository.delete(categoryRepository.findByIdLike(id));
        return "redirect:all";
    }
}
