package ru.isaev.Service.ProductService;

import ru.isaev.Domain.Products.Product;

public interface IProductService {
    public void addProduct(Product product);

    public Product getProductById(Long id);

    public void updateProduct(Product product);

    public void removeProductById(Long id);
}
