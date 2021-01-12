package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Role;
import ru.geekbrains.repository.RoleRepository;
import ru.geekbrains.repository.RoleSpecification;
import java.util.Optional;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping("/all")
    public String all(Model model,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<Role> roleSpecification = RoleSpecification.trueLiteral();
        if(name != null && !name.isEmpty()) {
            roleSpecification = roleSpecification.and(RoleSpecification.nameLike(name));
        }
        model.addAttribute("roles", roleRepository.findAll(roleSpecification, pageRequest));
        return "roles";
    }

    @RequestMapping("/new")
    public String newRole(Model model) {
        model.addAttribute("role", new Role());
        return "newrole";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("role") Role role) {
        roleRepository.save(role);
        return "redirect:all";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("role", roleRepository.findByIdLike(id));
        return "editrole";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("role") Role role) {
        Role roleFromDB = roleRepository.findByIdLike(role.getId());
        if(!roleFromDB.getName().equals(role.getName())) {
            roleFromDB.setName(role.getName());
        }
        roleRepository.save(roleFromDB);
        return "redirect:all";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(value = "id") Long id) {
        roleRepository.delete(roleRepository.findByIdLike(id));
        return "redirect:all";
    }
}
