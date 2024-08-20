package ru.isaev.Service.ProductService;

import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Products.Status;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();

    public List<Product> getProductsByStatus(Status status);

    public List<Product> getProductsFollowedByUser(Long id);

    public List<Product> getProductsByTitle(String title);

    public List<Product> getProductsByCategory(String category);

    public List<Product> getProductsByTitleAndCategory(String title, String category);

    public void addProduct(Product product);

    public Product getProductById(Long id);

    public void updateProduct(Product product);

    public void removeProductById(Long id);

    public Product subscribeOnProductById(Long id);

    public Product unsubscribeFromProductById(Long productId);

    public Product approveOfPublishingOrEditingProductById(Long productId);

    public Product archiveProductById(Long productId);
}
