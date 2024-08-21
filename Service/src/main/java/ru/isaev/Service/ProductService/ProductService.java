package ru.isaev.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Products.Status;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.ProductRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.NotificationService.NotificationService;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    private final NotificationService notificationService;

    @Autowired
    public ProductService(ProductRepo productRepo, UserRepo userRepo, NotificationService notificationService) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
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
        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("You requested publication of product with id= " + product.getId());
        notificationService.addNotification(notification);

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

        if (product.getStatus().equals(Status.ON_MODERATION_FOR_EDITING) ||
                product.getStatus().equals(Status.ON_MODERATION_FOR_PUBLISHING)) {
            productRepo.save(product);
            return;
        }

        if (product.getStatus().equals(Status.ARCHIVED)) {
            throw new InvalidProductOperationException("You can't edit archived product with id = " + product.getId());
        }

        Product oldVersionOfProduct = productRepo.findById(product.getId()).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + product.getId())
        );
        oldVersionOfProduct.setChildProduct(product);
        product.setParentProduct(oldVersionOfProduct);

        product.setStatus(Status.ON_MODERATION_FOR_EDITING);

        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("You requested editing of product with id= " + oldVersionOfProduct.getId() + " was approved");
        notificationService.addNotification(notification);

        productRepo.save(oldVersionOfProduct);
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

        if (!(product.getStatus().equals(Status.APPROVED) || product.getStatus().equals(Status.ARCHIVED))) {
            throw new SubscriptionException("You can't subscribe to project on moderation");
        }

        List<Product> productsFollowedByUserList = currentUser.getFollowedProductsList();
        List<User> subsbcribersOfProductList = product.getSubscribersList();

        productsFollowedByUserList.add(product);
        subsbcribersOfProductList.add(currentUser);

        currentUser.setFollowedProductsList(productsFollowedByUserList);
        product.setSubscribersList(subsbcribersOfProductList);

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

        List<Product> productsFollowedByUserList = currentUser.getFollowedProductsList();
        List<User> subsbcribersOfProductList = product.getSubscribersList();

        productsFollowedByUserList.remove(product);
        subsbcribersOfProductList.remove(currentUser);

        currentUser.setFollowedProductsList(productsFollowedByUserList);
        product.setSubscribersList(subsbcribersOfProductList);

        userRepo.save(currentUser);
        productRepo.save(product);

        return product;
    }

    @Override
    public Product approveOfPublishingOrEditingProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (product.getStatus().equals(Status.ON_MODERATION_FOR_PUBLISHING)) {
            product.setStatus(Status.APPROVED);
            productRepo.save(product);

            Notification notification = new Notification();
            notification.setUserId(currentUser.getId());
            notification.setMessage("Publishing of product with id= " + productId + " was approved");
            notificationService.addNotification(notification);
            return product;
        }

        Product parentProduct = product.getParentProduct();

        parentProduct.setChildProduct(null);
        parentProduct.setTitle(product.getTitle());
        parentProduct.setEmailOFSupport(product.getEmailOFSupport());
        parentProduct.setLinkToWebSite(product.getLinkToWebSite());
        parentProduct.setDescription(product.getDescription());
        parentProduct.setCategory(product.getCategory());

        productRepo.deleteById(productId);
        productRepo.save(parentProduct);

        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("Editing of product with id= " + parentProduct.getId() + " was approved");
        notificationService.addNotification(notification);

        for (User user :
                parentProduct.getSubscribersList()) {
            Notification notificationForSubscriber = new Notification(
                    user.getId(),
                    "Subscription notification: Product with id= " + parentProduct.getId() + " was updated");
            notificationService.addNotification(notificationForSubscriber);
        }
        return product;
    }

    @Override
    public void rejectOfPublishingOrEditingProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (product.getStatus().equals(Status.ON_MODERATION_FOR_PUBLISHING)) {
            Notification notification = new Notification();
            notification.setUserId(currentUser.getId());
            notification.setMessage("Publishing of product with id= " + productId + " was rejected");
            notificationService.addNotification(notification);
        }

        Product parentProduct = product.getParentProduct();
        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("Editing of product with id= " + parentProduct.getId() + " was rejected");
        notificationService.addNotification(notification);
    }

    @Override
    public Product archiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (product.getStatus().equals(Status.ON_MODERATION_FOR_PUBLISHING)) {

            Notification notification = new Notification();
            notification.setUserId(currentUser.getId());
            notification.setMessage("You you have removed the product with id= " + product.getId() + " from moderation for publishing");
            notificationService.addNotification(notification);

            productRepo.deleteById(productId);
            return product;
        }

        if (product.getChildProduct() != null && product.getChildProduct().getStatus().equals(Status.ON_MODERATION_FOR_EDITING)) {
            Product childProduct = product.getChildProduct();
            productRepo.deleteById(childProduct.getId());
            Notification notification1 = new Notification();
            notification1.setUserId(currentUser.getId());
            notification1.setMessage("You you have removed the product with id= " + product.getId() + " from moderation for publishing");
            notificationService.addNotification(notification1);

            product.setStatus(Status.ARCHIVED);
            productRepo.save(product);
            Notification notification = new Notification();
            notification.setUserId(currentUser.getId());
            notification.setMessage("Archivation of product with id= " + product.getId());
            notificationService.addNotification(notification);

            for (User user :
                    product.getSubscribersList()) {
                Notification notificationForSubscriber = new Notification(
                        user.getId(),
                        "Subscription notification: Product with id= " + product.getId() + " was archived");
                notificationService.addNotification(notificationForSubscriber);
            }
            return product;
        }

        if (product.getParentProduct() != null && product.getStatus().equals(Status.ON_MODERATION_FOR_EDITING)) {
            Product parentProduct = product.getParentProduct();
            productRepo.deleteById(productId);
            parentProduct.setChildProduct(null);

            Notification notification = new Notification();
            notification.setUserId(currentUser.getId());
            notification.setMessage("You you have removed the product with id= " + parentProduct.getId() + " from moderation for editing");
            notificationService.addNotification(notification);

            return product;
        }

        product.setStatus(Status.ARCHIVED);
        productRepo.save(product);

        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("Archivation of product with id= " + product.getId());
        notificationService.addNotification(notification);

        for (User user :
                product.getSubscribersList()) {
            Notification notificationForSubscriber = new Notification(
                    user.getId(),
                    "Subscription notification: Product with id= " + product.getId() + " was archived");
            notificationService.addNotification(notificationForSubscriber);
        }

        return product;
    }

    @Override
    public Product unarchiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        product.setStatus(Status.APPROVED);
        productRepo.save(product);

        Notification notification = new Notification();
        notification.setUserId(currentUser.getId());
        notification.setMessage("Archivation of product with id= " + product.getId());
        notificationService.addNotification(notification);

        for (User user :
                product.getSubscribersList()) {
            Notification notificationForSubscriber = new Notification(
                    user.getId(),
                    "Subscription notification: Product with id= " + product.getId() + " was unarchived");
            notificationService.addNotification(notificationForSubscriber);
        }

        return product;
    }
}
