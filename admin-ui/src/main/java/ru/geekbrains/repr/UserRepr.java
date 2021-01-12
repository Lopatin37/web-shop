package ru.geekbrains.repr;

import ru.geekbrains.entity.User;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserRepr implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Set<RoleRepr> roles;

    public UserRepr() {}

    public UserRepr(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream()
                .map(RoleRepr::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleRepr> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleRepr> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return username;
    }
}
