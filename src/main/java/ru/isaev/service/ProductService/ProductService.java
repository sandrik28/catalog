package ru.isaev.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.domain.Notifications.Notification;
import ru.isaev.domain.Notifications.NotificationMessage;
import ru.isaev.domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.Products.Status;
import ru.isaev.domain.Users.Roles;
import ru.isaev.domain.Users.User;
import ru.isaev.repo.ProductRepo;
import ru.isaev.repo.UserRepo;
import ru.isaev.service.NotificationService.NotificationService;
import ru.isaev.service.Security.MyUserDetails;
import ru.isaev.service.Utilities.Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<Product> getAllApprovedProducts() {
        return productRepo.findByStatus(Status.APPROVED);
    }

    @Override
    public List<Product> getAllProductsByUser(User user) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to all products of user with id = " + user.getId() +
                    "because they contain products on moderation");

        return productRepo.getProductsByOwner(user);
    }

    @Override
    public List<Product> getProductsByUserIdAndStatus(Long userId, Status status) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        User user = userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Not found user with id = " + userId));


        if ((status.equals(Status.ON_MODERATION) || status.equals(Status.MODERATION_DENIED)) &&
        !currentUser.getId().equals(userId) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to products on moderation of user with id = " + userId);

        return productRepo.findProductsByOwnerAndStatus(user, status);
    }

    @Override
    public List<Product> getAllProductsByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Not found user with id = " + userId));

        return productRepo.getProductsByOwner(user);
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUser(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);

        List<Product> allProducts =  productRepo.findByStatus(Status.APPROVED);
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
    public List<Product> getAllApprovedProductsFollowedByUserByTitle(Long id, String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);

        List<Product> allProducts =  productRepo.findByStatus(Status.APPROVED);
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

        List<Product> filteredResponse = new ArrayList<>();

        for (Product p :
                productsFollowedByUser) {
            if (p.getTitle().toLowerCase().contains(title.toLowerCase()))
                filteredResponse.add(p);
        }

        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUserByCategory(Long id, String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);

        List<Product> allProducts =  productRepo.findByStatus(Status.APPROVED);
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

        return productsFollowedByUser.
                stream().
                filter(product -> product.getCategory().equals(category)).
                collect(Collectors.toList());
    }


    @Override
    public List<Product> getAllApprovedProductsFollowedByUserByTitleAndCategory(Long id, String title, String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);

        List<Product> allProducts =  productRepo.findByStatus(Status.APPROVED);
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

        return productsFollowedByUser.
                stream().
                filter(product -> product.getCategory().equals(category)).
                filter(product -> product.getTitle().equals(title)).
                collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        List<Product> response = productRepo.findByNameContainingIgnoreCase(title.toLowerCase());
        List<Product> filteredResponse = new ArrayList<>();

        for (Product p :
                response) {
            if ((p.getStatus().equals(Status.ON_MODERATION) || p.getStatus().equals(Status.MODERATION_DENIED)) &&
                    !p.getId().equals(currentUser.getId()))
                continue;
            else
                filteredResponse.add(p);
        }

        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsByCategory(String category) {
        return productRepo.findProductsByStatusAndCategory(Status.APPROVED, category);
    }

    public List<Product> getAllApprovedProductsByTitleAndCategory(String title, String category) {
        List<Product> response = productRepo.findProductsByStatusAndTitleAndCategory(Status.APPROVED, title, category);

        List<Product> filteredResponse = new ArrayList<>();

        for (Product p :
                response) {
            if (p.getTitle().toLowerCase().contains(title.toLowerCase()))
                filteredResponse.add(p);
        }

        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to products on moderation");

        return productRepo.findProductsByStatusAndTitle(Status.APPROVED, title);
    }

    @Override
    public List<Product> getProductsForModeratorByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to products on moderation");

        List<Product> productsOnModerationList = this.getProductsByTitle(title).
                stream().
                filter(product -> product.getStatus() == Status.ON_MODERATION).
                collect(Collectors.toList());

        return productsOnModerationList;
    }

    @Override
    public List<Product> getProductsForModeratorByCategory(String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourProductException("You do not have access to products on moderation");

        List<Product> productsOnModerationList = productRepo.findByCategory(category).
                stream().
                filter(product -> product.getStatus() == Status.ON_MODERATION).
                collect(Collectors.toList());

        return productsOnModerationList;
    }

    @Override
    public List<Product> getProductsForModeratorByTitleAndCategory(String title, String category) {
        List<Product> productsOnModerationList = productRepo.findByTitleAndCategory(title, category).
                stream().
                filter(product -> product.getStatus() == Status.ON_MODERATION).
                collect(Collectors.toList());

        return productsOnModerationList;
    }

    @Override
    public void addProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new InvalidProductOperationException("Moderators can't create products");

        product.setOwner(currentUser);

        product.setOwner(currentUser);
        List<Product> productsOfUser = currentUser.getProductsList();
        productsOfUser.add(product);
        currentUser.setProductsList(productsOfUser);

        productRepo.save(product);
        userRepo.save(currentUser);

        product.setStatus(Status.ON_MODERATION);
        Notification notification = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_SET_ON_MODERATION
        );

        notificationService.addNotification(notification);
    }

    @Override
    public Product getProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );

        if ((product.getStatus().equals(Status.MODERATION_DENIED) || product.getStatus().equals(Status.ON_MODERATION)) && product.getOwner().getId().equals(currentUser.getId())) {
            throw new InvalidProductOperationException("You can't view this product with id = " + product.getId());
        }

        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (product.getId() == null)
            throw new InvalidProductOperationException("You can't edit product which doesn't exist. No id provided");

        Product productSavedInDatabase = getProductById(product.getId());
        if (!Objects.equals(productSavedInDatabase.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("Not your product with id = " + productSavedInDatabase.getId());

        if (productSavedInDatabase.getStatus().equals(Status.ARCHIVED))
            throw new InvalidProductOperationException("You can't edit archived product. Product id = " + product.getId());

        if (productSavedInDatabase.getStatus().equals(Status.APPROVED) ||
                productSavedInDatabase.getStatus().equals(Status.MODERATION_DENIED)) {
            productSavedInDatabase.setStatus(Status.ON_MODERATION);
            if (product.getTitle() != null)
                productSavedInDatabase.setTitle(product.getTitle());
            if (product.getCategory() != null)
                productSavedInDatabase.setCategory(product.getCategory());
            if (product.getLinkToWebSite() != null)
                productSavedInDatabase.setLinkToWebSite(product.getLinkToWebSite());
            if (product.getDescription() != null)
                productSavedInDatabase.setDescription(product.getDescription());
            if (product.getEmailOFSupport() != null)
                productSavedInDatabase.setEmailOFSupport(product.getEmailOFSupport());

            Notification notificationToOwner = new Notification(
                    productSavedInDatabase.getOwner().getId(),
                    productSavedInDatabase.getId(),
                    productSavedInDatabase.getCategory(),
                    NotificationMessage.PRODUCT_WAS_SET_ON_MODERATION
            );
            notificationService.addNotification(notificationToOwner);
        }

        if (productSavedInDatabase.getStatus().equals(Status.ON_MODERATION)) {
            if (product.getTitle() != null)
                productSavedInDatabase.setTitle(product.getTitle());
            if (product.getCategory() != null)
                productSavedInDatabase.setCategory(product.getCategory());
            if (product.getLinkToWebSite() != null)
                productSavedInDatabase.setLinkToWebSite(product.getLinkToWebSite());
            if (product.getDescription() != null)
                productSavedInDatabase.setDescription(product.getDescription());
            if (product.getEmailOFSupport() != null)
                productSavedInDatabase.setEmailOFSupport(product.getEmailOFSupport());

            Notification notificationToOwner = new Notification(
                    productSavedInDatabase.getOwner().getId(),
                    productSavedInDatabase.getId(),
                    productSavedInDatabase.getCategory(),
                    NotificationMessage.PRODUCT_ON_MODERATION_WAS_EDITED
            );
            notificationService.addNotification(notificationToOwner);
        }

        return productRepo.save(productSavedInDatabase);
    }

    @Override
    public void removeProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("You can't archive product with id= " + id + " as it does not belong to you");

        List<Product> productList = currentUser.getProductsList();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(id))
                productList.remove(i);
        }
        currentUser.setProductsList(productList);
        product.setOwner(null);

        productRepo.save(product);
        userRepo.save(currentUser);

        productRepo.deleteById(id);
    }

    @Override
    public IdsOfFollowedProductsDto subscribeOnProductById(Long productId) {
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
        for (int i = 0; i < productsFollowedByUserList.size(); i++) {
            if (productsFollowedByUserList.get(i).getId().equals(productId)) {
                List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().
                        stream().
                        map(p -> p.getId()).
                        collect(Collectors.toList());

                IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
                dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);
                return dto;
            }
        }
        List<User> subsbcribersOfProductList = product.getSubscribersList();

        productsFollowedByUserList.add(product);
        subsbcribersOfProductList.add(currentUser);

        currentUser.setFollowedProductsList(productsFollowedByUserList);
        product.setSubscribersList(subsbcribersOfProductList);

        userRepo.save(currentUser);
        productRepo.save(product);

        List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().
                stream().
                map(p -> p.getId()).
                collect(Collectors.toList());

        IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);

        return dto;
    }

    @Override
    public IdsOfFollowedProductsDto unsubscribeFromProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (!(product.getStatus().equals(Status.APPROVED) || product.getStatus().equals(Status.ARCHIVED))) {
            throw new SubscriptionException("You can't unsubscribe from project on moderation");
        }

        List<Product> productsFollowedByUserList = currentUser.getFollowedProductsList();
        List<User> subsbcribersOfProductList = product.getSubscribersList();


        for (int i = 0; i < productsFollowedByUserList.size(); i++) {
            if (productsFollowedByUserList.get(i).getId().equals(productId)) {
                productsFollowedByUserList.remove(i);
            }
        }

        for (int i = 0; i < subsbcribersOfProductList.size(); i++) {
            if (subsbcribersOfProductList.get(i).getId().equals(currentUser.getId())) {
                subsbcribersOfProductList.remove(i);
            }
        }

        currentUser.setFollowedProductsList(productsFollowedByUserList);
        product.setSubscribersList(subsbcribersOfProductList);

        userRepo.save(currentUser);
        productRepo.save(product);

        List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().
                stream().
                map(p -> p.getId()).
                collect(Collectors.toList());

        IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);

        return dto;
    }

    @Override
    public Product approveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new InvalidProductOperationException("Only moderators hava access to products on moderation");

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (!product.getStatus().equals(Status.ON_MODERATION)) {
            throw new InvalidProductOperationException("You can't approve product which is not on moderation. Product id = " + product.getId());
        }

        product.setStatus(Status.APPROVED);
        Notification notificationToOwner = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_PUBLISHED
        );
        notificationService.addNotification(notificationToOwner);

        productRepo.save(product);

        return product;
    }

    @Override
    public Product declineOfModerationByProductId(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new InvalidProductOperationException("Only moderators can decline products on moderation");

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (!product.getStatus().equals(Status.ON_MODERATION)) {
            throw new InvalidProductOperationException("You can't decline moderation of product which is not on moderation. Product id = " + product.getId());
        }

        product.setStatus(Status.MODERATION_DENIED);
        Notification notificationToOwner = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_DECLINED_OF_MODERATION
        );
        notificationService.addNotification(notificationToOwner);

        productRepo.save(product);

        return product;
    }

    @Override
    public void archiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("You can't archive product with id= " + productId + " as it does not belong to you");

        if (product.getStatus().equals(Status.ARCHIVED)) {
            this.removeProductById(productId);

            Notification notification = new Notification(
                    currentUser.getId(),
                    product.getId(),
                    product.getCategory(),
                    NotificationMessage.PRODUCT_WAS_DELETED
            );
            notificationService.addNotification(notification);
            notificationService.addNotificationToSubscribersOfProduct(notification, product, product.getSubscribersList());

            return;
        }

        Status oldStatus = product.getStatus();

        product.setStatus(Status.ARCHIVED);
        Notification notification = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_ARCHIVED
        );
        notificationService.addNotification(notification);

        if (oldStatus.equals(Status.APPROVED))
            notificationService.addNotificationToSubscribersOfProduct(notification, product, product.getSubscribersList());
        productRepo.save(product);
    }

    @Override
    public Product unarchiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Product product = productRepo.findById(productId).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + productId)
        );

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("You can't unarchive product with id= " + productId + " as it does not belong to you");

        if (!product.getStatus().equals(Status.ARCHIVED))
            throw new InvalidProductOperationException("You can't unarchive product which hasn't been archived. Product id = " + product.getId());

        product.setStatus(Status.ON_MODERATION);
        Notification notification = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_SET_ON_MODERATION
        );
        notificationService.addNotification(notification);
        productRepo.save(product);

        return product;
    }
}
