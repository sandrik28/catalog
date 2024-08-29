package ru.isaev.service.ProductService;

import ru.isaev.domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.Products.Status;
import ru.isaev.domain.Users.User;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();

    public List<Product> getAllApprovedProducts();

    public List<Product> getAllProductsByUser(User user);

    public List<Product> getProductsByUserIdAndStatus(Long userId, Status status);

    public List<Product> getAllProductsByUserId(Long userId);

//    public List<Product> getProductsFollowedByUser(Long id);

    public List<Product> getProductsByTitle(String title);

    public List<Product> getAllApprovedProductsFollowedByUser(Long userId);

    public List<Product> getAllApprovedProductsFollowedByUserByTitle(Long id, String title);

    public List<Product> getAllApprovedProductsFollowedByUserByCategory(Long id, String category);

    public List<Product> getAllApprovedProductsFollowedByUserByTitleAndCategory(Long id, String title, String category);

//    public List<Product> getProductsByCategory(String category);

    public List<Product> getAllApprovedProductsByTitle(String title);

//    public List<Product> getProductsByTitleAndCategory(String title, String category);

    public List<Product> getAllApprovedProductsByTitleAndCategory(String title, String category);

    public List<Product> getProductsForModeratorByTitle(String title);

    public List<Product> getProductsForModeratorByCategory(String category);

    public List<Product> getProductsForModeratorByTitleAndCategory(String title, String category);

    public void addProduct(Product product);

    public Product getProductById(Long id);

    public Product updateProduct(Product product);

    public List<Product> getAllApprovedProductsByCategory(String category);

    public void removeProductById(Long id);

    public IdsOfFollowedProductsDto subscribeOnProductById(Long id);

    public IdsOfFollowedProductsDto unsubscribeFromProductById(Long productId);

    public Product approveProductById(Long productId);

    public Product declineOfModerationByProductId(Long productId);

    public void archiveProductById(Long productId);

    public Product unarchiveProductById(Long productId);
}
