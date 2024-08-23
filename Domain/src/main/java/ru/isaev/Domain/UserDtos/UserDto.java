package ru.isaev.Domain.UserDtos;

import java.util.List;

public class UserDto {
    private Long id;

    private String name;

    private String email;

    private List<Long> idOfFollowedProductsList;

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

    public void setIdOfFollowedProductsList(List<Long> idOfFollowedProductsList) {
        this.idOfFollowedProductsList = idOfFollowedProductsList;
    }
}
