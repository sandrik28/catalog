package ru.isaev.Domain.Products;

import jakarta.persistence.*;
import ru.isaev.Domain.Users.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subscribers_of_product",
            joinColumns = @JoinColumn(name = "prodcut_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> subscribersList = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    private Types type;

    private String emailOFSupport;

    private String linkToWebSite;

    private String description;

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

    public void setOwner(User owner) {
        this.owner = owner;
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

    public void setUser(User owner, Boolean add) {
        this.owner = owner;
        if (owner != null && add) {
            owner.addCat(this, false);
        }
    }
}
