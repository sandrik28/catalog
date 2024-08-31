package ru.isaev.domain_made_by_isaev.notifications;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private String titleOfProduct;

    private NotificationMessage message;

    private LocalDateTime timestamp;

    public Notification(Long userId, Long productId, String titleOfProduct, NotificationMessage message) {
        this.titleOfProduct = titleOfProduct;
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.message = message;
        timestamp = LocalDateTime.now();
    }

    public Notification() {
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

    public String getTitleOfProduct() {
        return titleOfProduct;
    }

    public void setTitleOfProduct(String titleOfProduct) {
        this.titleOfProduct = titleOfProduct;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
