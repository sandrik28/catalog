package ru.isaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.Products.Status;
import ru.isaev.domain.Users.User;

import java.util.List;

@Repository
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
