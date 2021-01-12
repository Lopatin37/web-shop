package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.*;
import ru.geekbrains.repository.*;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.ProductService;
import java.io.IOException;
import java.util.Optional;

@Controller
public class MainAdminController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PictureRepository pictureRepository;

    @RequestMapping("/")
    public String homeAdminPage() {
        return "index";
    }
}
