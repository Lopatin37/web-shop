package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.entity.Product;
import ru.geekbrains.repository.*;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.ProductService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ProductService productService;
    private PictureRepository pictureRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, BrandRepository brandRepository,
                             CategoryRepository categoryRepository, ProductService productService, PictureRepository pictureRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.pictureRepository = pictureRepository;
    }

    @RequestMapping("/all")
    public String all(Model model,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<Product> productSpecification = ProductSpecification.trueLiteral();
        if(name != null && !name.isEmpty()) {
            productSpecification = productSpecification.and(ProductSpecification.nameLike(name));
        }
        model.addAttribute("products", productRepository.findAll(productSpecification, pageRequest));
        return "products";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductRepr());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "newproduct";
    }

    @PostMapping("/insert")
    public String insert(ProductRepr product){
        try {
            productService.save(product);
        } catch (Exception ex) {
            return "redirect:new";
        }
        return "redirect:all";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("product", productService.findById(id).get());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "editproduct";
    }

    @PostMapping("/update")
    public String update(ProductRepr product) {
        try {
            productService.save(product);
        } catch (Exception ex) {
            return "redirect:edit";
        }
        return "redirect:all";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(value = "id") Long id) {
        productRepository.delete(productRepository.findByIdLike(id));
        return "redirect:all";
    }

    @RequestMapping("product")
    public String product(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("product", productService.findById(id).get());
        return "product";
    }

    @RequestMapping("productimg")
    public String productImg(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("picture", pictureRepository.findById(id).get());
        return "productimg";
    }
}
