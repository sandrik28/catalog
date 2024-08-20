package ru.isaev.Service.ProductService;

import ru.isaev.Domain.Products.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public void addProduct(Product product);

    public Product getProductById(Long id);

    public void updateProduct(Product product);

    public void removeProductById(Long id);
}
