package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Products.Status;
import ru.isaev.Domain.Users.User;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByStatus(Status status);

    List<Product> findByTitle(String title);

    List<Product> findByCategory(String category);

    List<Product> findByTitleAndCategory(String title, String category);

    public List<Product> getProductsByOwner(User owner);

    public List<Product> findProductsByOwnerAndStatus(User owner, Status status);

    public List<Product> findProductsByStatusAndCategory(Status status, String category);

    public List<Product> findProductsByStatusAndTitle(Status status, String title);

    public List<Product> findProductsByStatusAndTitleAndCategory(Status status, String title, String category);
}
