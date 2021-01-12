package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Brand;
import ru.geekbrains.repository.BrandRepository;
import ru.geekbrains.repository.BrandSpecification;

import java.util.Optional;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private BrandRepository brandRepository;

    @Autowired
    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @RequestMapping("/all")
    public String all(Model model,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<Brand> brandSpecification = BrandSpecification.trueLiteral();
        if(name != null && !name.isEmpty()) {
            brandSpecification = brandSpecification.and(BrandSpecification.nameLike(name));
        }
        model.addAttribute("brands", brandRepository.findAll(brandSpecification, pageRequest));
        return "brands";
    }

    @RequestMapping("/new")
    public String newBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "newbrand";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("brand") Brand brand) {
        brandRepository.save(brand);
        return "redirect:all";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("brand", brandRepository.findByIdLike(id));
        return "editbrand";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("brand") Brand brand) {
        Brand brandFromDB = brandRepository.findByIdLike(brand.getId());
        if(!brandFromDB.getName().equals(brand.getName())) {
            brandFromDB.setName(brand.getName());
        }
        brandRepository.save(brandFromDB);
        return "redirect:all";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(value = "id") Long id) {
        brandRepository.delete(brandRepository.findByIdLike(id));
        return "redirect:all";
    }
}
