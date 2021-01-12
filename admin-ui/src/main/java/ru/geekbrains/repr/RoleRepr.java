package ru.geekbrains.repr;

import ru.geekbrains.entity.Role;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleRepr implements Serializable {
    private Long id;
    private String name;
    private Set<UserRepr> users;

    public RoleRepr() { }

    public RoleRepr(Role role) {
        this.id = role.getId();
        this.name = getName();
        this.users = role.getUsers().stream()
                .map(UserRepr::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRepr> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRepr> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name;
    }
}
