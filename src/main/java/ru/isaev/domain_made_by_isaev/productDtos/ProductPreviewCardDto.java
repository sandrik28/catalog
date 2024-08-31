package ru.isaev.domain_made_by_isaev.productDtos;

import ru.isaev.domain_made_by_isaev.products.Status;

import java.time.LocalDateTime;

public class ProductPreviewCardDto {
    private Long id;

    private Long ownerId;

    private String title;

    private String nameOfOwner;

    private String description;

    private String category;

    private Status status;

    private LocalDateTime timeOfLastApproval;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTimeOfLastApproval() {
        return timeOfLastApproval;
    }

    public void setTimeOfLastApproval(LocalDateTime timeOfLastApproval) {
        this.timeOfLastApproval = timeOfLastApproval;
    }
}
