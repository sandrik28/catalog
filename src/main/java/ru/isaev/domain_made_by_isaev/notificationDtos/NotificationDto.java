package ru.isaev.domain_made_by_isaev.notificationDtos;

import ru.isaev.domain_made_by_isaev.notifications.NotificationMessage;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long id;

    private Long userId;

    private Long productId;

    private String titleOfProduct;

    private NotificationMessage message;

    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public NotificationMessage getMessage() {
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitleOfProduct() {
        return titleOfProduct;
    }

    public void setTitleOfProduct(String titleOfProduct) {
        this.titleOfProduct = titleOfProduct;
    }
}
