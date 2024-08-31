package ru.isaev.domain_made_by_isaev.products;

import jakarta.persistence.*;
import ru.isaev.domain_made_by_isaev.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    @ManyToMany(
            mappedBy = "followedProductsList",
            fetch = FetchType.EAGER
    )
    private List<User> subscribersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    private Status status;

    private String emailOfSupport;

    private String linkToWebSite;

    private String description;

    private String category;

    private LocalDateTime timeOfLastApproval;

    public Product() {
    }

    public LocalDateTime getTimeOfLastApproval() {
        return timeOfLastApproval;
    }

    public void setTimeOfLastApproval(LocalDateTime timeOfLastApproval) {
        this.timeOfLastApproval = timeOfLastApproval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getSubscribersList() {
        return subscribersList;
    }

    public void setSubscribersList(List<User> subscribersList) {
        this.subscribersList = subscribersList;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailOFSupport() {
        return emailOfSupport;
    }

    public void setEmailOFSupport(String emailOFSupport) {
        this.emailOfSupport = emailOFSupport;
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

    public void setUser(User owner, Boolean add) {
        this.owner = owner;
        if (owner != null && add) {
            owner.addProduct(this, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(subscribersList, product.subscribersList) && Objects.equals(owner, product.owner) && status == product.status && Objects.equals(emailOfSupport, product.emailOfSupport) && Objects.equals(linkToWebSite, product.linkToWebSite) && Objects.equals(description, product.description) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subscribersList, owner, status, emailOfSupport, linkToWebSite, description, category);
    }
}
