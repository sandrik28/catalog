package ru.isaev.Domain.Products;

import jakarta.persistence.*;
import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.Users.User;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscribers_of_product",
            joinColumns = @JoinColumn(name = "prodcut_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> subscribersList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @OneToOne(fetch = FetchType.EAGER)
    private Product childProduct;

    @OneToOne(fetch = FetchType.EAGER)
    private Product parentProduct;

    private Status status;

    private String emailOFSupport;

    private String linkToWebSite;

    private String description;

    private String category;

    public Product() {
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
        return Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(subscribersList, product.subscribersList) && Objects.equals(owner, product.owner) && status == product.status && Objects.equals(emailOFSupport, product.emailOFSupport) && Objects.equals(linkToWebSite, product.linkToWebSite) && Objects.equals(description, product.description) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subscribersList, owner, status, emailOFSupport, linkToWebSite, description, category);
    }
}
