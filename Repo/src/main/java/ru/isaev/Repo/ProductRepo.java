package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Products.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
