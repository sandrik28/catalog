package ru.isaev.Domain.ProductDtos;

import ru.isaev.Domain.Products.Status;

import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private Long ownerId;

    private String nameOfOwner;

    private String title;

    private Status type;

    private String emailOFSupport;

    private String linkToWebSite;

    private String description;

    private String category;

    private Boolean isFollowedByCurrentUser;

    private String timeOfLastApproval;

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

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public Boolean getFollowedByCurrentUser() {
        return isFollowedByCurrentUser;
    }

    public void setFollowedByCurrentUser(Boolean followedByCurrentUser) {
        isFollowedByCurrentUser = followedByCurrentUser;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public String getEmailOFSupport() {
        return emailOFSupport;
    }

    public void setEmailOFSupport(String emailOFSupport) {
        this.emailOFSupport = emailOFSupport;
    }

    public String getLinkToWebSite() {
        return linkToWebSite;
    }

    public void setLinkToWebSite(String linkToWebSite) {
        this.linkToWebSite = linkToWebSite;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
