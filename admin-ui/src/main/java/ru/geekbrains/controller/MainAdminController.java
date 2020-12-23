package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Role;
import ru.geekbrains.entity.User;
import ru.geekbrains.repository.RoleRepository;
import ru.geekbrains.repository.RoleSpecification;
import ru.geekbrains.repository.UserRepository;
import ru.geekbrains.repository.UserSpecification;
import ru.geekbrains.security.PasswordEncoderGenerator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MainAdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    //TODO Сделать энкодер из спринга данного модуля
    @Autowired
    PasswordEncoderGenerator bCryptPasswordEncoder;

    @RequestMapping("/")
    public String homeAdminPage() {
        return "index";
    }

    @RequestMapping("/users")
    public String usersPage(Model model,
                            @RequestParam(value = "username", required = false) String username,
                            @RequestParam("page")Optional<Integer> page,
                            @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<User> userSpecification = UserSpecification.trueLiteral();
        if(username != null && !username.isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.usernameLike(username));
        }
        model.addAttribute("users", userRepository.findAll(userSpecification, pageRequest));
        return "users";
    }

    @RequestMapping("/roles")
    public String rolesPage(Model model,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam("page")Optional<Integer> page,
                            @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<Role> roleSpecification = RoleSpecification.trueLiteral();
        if(name != null && !name.isEmpty()) {
            roleSpecification = roleSpecification.and(RoleSpecification.nameLike(name));
        }
        model.addAttribute("roles", roleRepository.findAll(roleSpecification, pageRequest));
        return "roles";
    }

    @RequestMapping("/newuser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "newuser";
    }

    @RequestMapping("/newrole")
    public String newRole(Model model) {
        model.addAttribute("role", new Role());
        return "newrole";
    }

    @PostMapping("/insert")
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "users";
        user.setPassword(bCryptPasswordEncoder.StringToHash(user.getPassword()));
        userRepository.save(user);
        return "redirect:users";
    }

    @PostMapping("/insertrole")
    public String insertRole(@ModelAttribute("role") Role role) {
        roleRepository.save(role);
        return "redirect:roles";
    }

    @GetMapping("/edituser")
    public String editUser(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("user", userRepository.findByIdLike(id));
        return "edituser";
    }

    @GetMapping("/editrole")
    public String editRole(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("role", roleRepository.findByIdLike(id));
        return "editrole";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        User userFromDB = userRepository.findByIdLike(user.getId());
        if(!userFromDB.getUsername().equals(user.getUsername())) {
            userFromDB.setUsername(user.getUsername());
        }
        if(!userFromDB.getPassword().equals(user.getPassword())) {
            userFromDB.setPassword(bCryptPasswordEncoder.StringToHash(user.getPassword()));
        }
//        TODO Сделать возможность удаления ролей
        userFromDB.getRoles().addAll(user.getRoles());
        userRepository.save(userFromDB);
        return "redirect:users";
    }

    @PostMapping("/updateRole")
    public String updateRole(@ModelAttribute("role") Role role) {
        Role roleFromDB = roleRepository.findByIdLike(role.getId());
        if(!roleFromDB.getName().equals(role.getName())) {
            roleFromDB.setName(role.getName());
        }
        roleRepository.save(roleFromDB);
        return "redirect:roles";
    }

    @GetMapping("deleteuser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userRepository.delete(userRepository.findByIdLike(id));
        return "redirect:users";
    }

    @GetMapping("deleterole")
    public String deleteRole(@RequestParam(value = "id") Long id) {
        roleRepository.delete(roleRepository.findByIdLike(id));
        return "redirect:roles";
    }
}
