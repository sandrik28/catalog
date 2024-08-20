package ru.isaev.Domain.ProductDtos;

import ru.isaev.Domain.Products.Types;
import ru.isaev.Domain.Users.User;

public class ProductDto {
    private Long ownerId;

    private Types type;

    private String emailOFSupport;

    private String linkToWebSite;

    private String description;

    private String category;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
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

    public void setCategory(String category) {
        this.category = category;
    }
}
