package ru.isaev.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Products.Status;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.ProductRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.NotYourProductException;
import ru.isaev.Service.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Service.Utilities.Exceptions.ProductNotFoundExceptions;
import ru.isaev.Service.Utilities.Exceptions.SubscriptionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByStatus(Status status) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        return productRepo.findByStatus(status);
    }

    @Override
    public List<Product> getProductsFollowedByUser(Long id) {
        List<Product> allProducts =  productRepo.findAll();
        List<Product> productsFollowedByUser = new ArrayList<>();

        for (Product p :
                allProducts) {
            List<User> subscribersList = p.getSubscribersList();

            for (User user:
                 subscribersList) {
                if (user.getId().equals(id)) {
                    productsFollowedByUser.add(p);
                    break;
                }
            }
        }

        return productsFollowedByUser;
    }

    @Override
    public List<Product> getProductsByTitle(String title) {
        return productRepo.findByTitle(title);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByTitleAndCategory(String title, String category) {
        return productRepo.findByTitleAndCategory(title, category);
    }


    @Override
    public void addProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        product.setStatus(Status.ON_MODERATION_FOR_PUBLISHING);

        product.setOwner(currentUser);
        List<Product> productsOfUser = currentUser.getProductsList();
        productsOfUser.add(product);
        currentUser.setProductsList(productsOfUser);

        productRepo.save(product);
        userRepo.save(currentUser);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );
    }

    @Override
    public void updateProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("Not your product with id = " + product.getId());

        product.setStatus(Status.ON_MODERATION_FOR_EDITING);
        productRepo.save(product);
    }

    @Override
    public void removeProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );

        if (!Objects.equals(currentUser.getId(), id)  && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + id);

        productRepo.deleteById(id);
    }

    @Override
    public Product subscribeOnProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (product.getOwner().getId().equals(currentUser.getId()))
            throw new SubscriptionException("You can't subscribe to your project");

        List<Product> productsFollowedByUserList = currentUser.getFollowedProductsList();
        List<User> subsbcribersOfProductList = product.getSubscribersList();

        productsFollowedByUserList.remove(product);
        subsbcribersOfProductList.remove(currentUser);

        userRepo.save(currentUser);
        productRepo.save(product);

        return product;
    }

    @Override
    public Product unsubscribeFromProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        return product;
    }

    @Override
    public Product approveOfPublishingOrEditingProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        product.setStatus(Status.APPROVED);
        productRepo.save(product);

        return product;
    }

    @Override
    public Product archiveProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        product.setStatus(Status.ARCHIVED);
        productRepo.save(product);

        return product;
    }
}
