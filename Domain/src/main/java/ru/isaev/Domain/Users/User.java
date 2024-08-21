package ru.isaev.Domain.Users;

import jakarta.persistence.*;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.Products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private Roles role;

    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_of_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "notifications_of_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private List<Notification> notificationsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "followed_products_of_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_product_id")
    )
    private List<Product> followedProductsList = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public List<Product> getFollowedProductsList() {
        return followedProductsList;
    }

    public void setFollowedProductsList(List<Product> followedProductsList) {
        this.followedProductsList = followedProductsList;
    }

    public void addProduct(Product product, Boolean set) {
        if (product != null) {
            productsList.add(product);
        }
        if (set) {
            product.setUser(this, false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && role == user.role && Objects.equals(email, user.email) && Objects.equals(productsList, user.productsList) && Objects.equals(notificationsList, user.notificationsList) && Objects.equals(followedProductsList, user.followedProductsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role, email, productsList, notificationsList, followedProductsList);
    }
}
