package ru.isaev.Domain.ProductDtos;

import ru.isaev.Domain.Products.Status;

public class ProductPreviewCardDto {
    private Long id;

    private Long ownerId;

    private String nameOfOwner;

    private String description;

    private String category;

    private Status status;

    public ProductPreviewCardDto(Long id, Long ownerId, String nameOfOwner, String description, String category, Status status) {
        this.id = id;
        this.ownerId = ownerId;
        this.nameOfOwner = nameOfOwner;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
