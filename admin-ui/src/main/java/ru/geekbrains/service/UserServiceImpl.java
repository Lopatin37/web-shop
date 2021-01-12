package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.repository.UserRepository;
import ru.geekbrains.repr.UserRepr;

import java.io.Serializable;

@Service
public class UserServiceImpl implements UserService, Serializable {
    private UserRepository userRepository;

    public UserServiceImpl() { }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
