package ru.isaev.repo_made_by_isaev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.products.Status;
import ru.isaev.domain_made_by_isaev.users.User;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByStatus(Status status);

    @Query("SELECT e FROM Product e WHERE LOWER(e.title) LIKE %:title%")
    List<Product> findByNameContainingIgnoreCase(@Param("title") String title);

    List<Product> findByTitle(String title);

    List<Product> findByCategory(String category);

    List<Product> findByTitleAndCategory(String title, String category);

    public List<Product> getProductsByOwner(User owner);

    public List<Product> findProductsByOwnerAndStatus(User owner, Status status);

    public List<Product> findProductsByStatusAndCategory(Status status, String category);

    public List<Product> findProductsByStatusAndTitle(Status status, String title);

    public List<Product> findProductsByStatusAndTitleAndCategory(Status status, String title, String category);
}
