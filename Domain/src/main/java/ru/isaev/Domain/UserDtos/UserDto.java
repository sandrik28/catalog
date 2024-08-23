package ru.isaev.Domain.UserDtos;

import ru.isaev.Domain.Users.Roles;

import java.util.List;

public class UserDto {
    private Long id;

    private String name;

    private String password;

    private String email;

    private List<Long> idOfFollowedProductsList;

    private Roles role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getIdOfFollowedProductsList() {
        return idOfFollowedProductsList;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdOfFollowedProductsList(List<Long> idOfFollowedProductsList) {
        this.idOfFollowedProductsList = idOfFollowedProductsList;
    }
}
