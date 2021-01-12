package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.User;
import ru.geekbrains.repository.RoleRepository;
import ru.geekbrains.repository.UserRepository;
import ru.geekbrains.repository.UserSpecification;
import ru.geekbrains.security.PasswordEncoderGenerator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    //TODO Сделать энкодер из спринга данного модуля
    private PasswordEncoderGenerator bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoderGenerator bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/all")
    public String all(Model model,
                            @RequestParam(value = "username", required = false) String username,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size")Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), Sort.by(Sort.Direction.ASC, "id"));
        Specification<User> userSpecification = UserSpecification.trueLiteral();
        if(username != null && !username.isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.usernameLike(username));
        }
        model.addAttribute("users", userRepository.findAll(userSpecification, pageRequest));
        return "users";
    }

    @RequestMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "newuser";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "users";
        user.setPassword(bCryptPasswordEncoder.StringToHash(user.getPassword()));
        userRepository.save(user);
        return "redirect:all";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("user", userRepository.findByIdLike(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "edituser";
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
        userFromDB.setRoles(user.getRoles());
        userRepository.save(userFromDB);
        return "redirect:all";
    }

    @GetMapping("delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userRepository.delete(userRepository.findByIdLike(id));
        return "redirect:all";
    }
}
