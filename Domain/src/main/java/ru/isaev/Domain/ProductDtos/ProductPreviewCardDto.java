package ru.isaev.Domain.ProductDtos;

import ru.isaev.Domain.Products.Status;

import java.time.LocalDateTime;

public class ProductPreviewCardDto {
    private Long id;

    private Long ownerId;

    private String title;

    private String nameOfOwner;

    private String description;

    private String category;

    private String status;

    private Boolean isFollowedByCurrentUser;

    private String timeOfLastApproval;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeOfLastApproval() {
        return timeOfLastApproval;
    }

    public void setTimeOfLastApproval(String timeOfLastApproval) {
        this.timeOfLastApproval = timeOfLastApproval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getFollowedByCurrentUser() {
        return isFollowedByCurrentUser;
    }

    public void setFollowedByCurrentUser(Boolean followedByCurrentUser) {
        isFollowedByCurrentUser = followedByCurrentUser;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
