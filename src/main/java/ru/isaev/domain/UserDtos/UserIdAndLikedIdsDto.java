package ru.isaev.domain.UserDtos;

import ru.isaev.domain.Users.Roles;

import java.util.List;

public class UserIdAndLikedIdsDto {
    Long userId;

    List<Long> idsOfFollowedProducts;

    Roles role;

    public List<Long> getIdsOfFollowedProducts() {
        return idsOfFollowedProducts;
    }

    public void setIdsOfFollowedProducts(List<Long> idsOfFollowedProducts) {
        this.idsOfFollowedProducts = idsOfFollowedProducts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
