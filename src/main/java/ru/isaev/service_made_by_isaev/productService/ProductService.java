package ru.isaev.service_made_by_isaev.productService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.notifications.NotificationMessage;
import ru.isaev.domain_made_by_isaev.productDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.products.Status;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.ProductRepo;
import ru.isaev.repo_made_by_isaev.IUserRepo;
import ru.isaev.service_made_by_isaev.notificationService.NotificationService;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    private final ProductRepo productRepo;
    private final IUserRepo userRepo;
    private final NotificationService notificationService;

    @Autowired
    public ProductService(ProductRepo productRepo, IUserRepo userRepo, NotificationService notificationService) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    @Override
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepo.findAll();
        logger.info("All products fetched successfully");
        return products;
    }

    @Override
    public List<Product> getAllApprovedProducts() {
        logger.info("Fetching all approved products");
        List<Product> products = productRepo.findByStatus(Status.APPROVED);
        logger.info("All approved products fetched successfully");
        return products;
    }

    @Override
    public List<Product> getAllProductsByUser(User user) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all products for user with ID: {}", user.getId());
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {}", currentUser.getId());
            throw new NotYourProductException("You do not have access to all products of user with id = " + user.getId() +
                    "because they contain products on moderation");
        }

        List<Product> products = productRepo.getProductsByOwner(user);
        logger.info("All products for user with ID {} fetched successfully", user.getId());
        return products;
    }

    @Override
    public List<Product> getProductsByUserIdAndStatus(Long userId, Status status) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching products for user with ID: {} and status: {}", userId, status);
        User user = userRepo.findById(userId).orElseThrow(
                () -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException("Not found user with id = " + userId);
                });

        if ((status.equals(Status.ON_MODERATION) || status.equals(Status.MODERATION_DENIED)) &&
                !currentUser.getId().equals(userId) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to products on moderation of user with ID: {}", currentUser.getId(), userId);
            throw new NotYourProductException("You do not have access to products on moderation of user with id = " + userId);
        }

        List<Product> products = productRepo.findProductsByOwnerAndStatus(user, status);
        logger.info("Products for user with ID {} and status {} fetched successfully", userId, status);
        return products;
    }

    @Override
    public List<Product> getAllProductsByUserId(Long userId) {
        logger.info("Fetching all products for user with ID: {}", userId);
        User user = userRepo.findById(userId).orElseThrow(
                () -> {
                    logger.error("User not found with ID: {}", userId);
                    return new UserNotFoundException("Not found user with id = " + userId);
                });

        List<Product> products = productRepo.getProductsByOwner(user);
        logger.info("All products for user with ID {} fetched successfully", userId);
        return products;
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUser(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all approved products followed by user with ID: {}", id);
        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to followed products of user with ID: {}", currentUser.getId(), id);
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);
        }

        List<Product> allProducts = productRepo.findByStatus(Status.APPROVED);
        List<Product> productsFollowedByUser = new ArrayList<>();

        for (Product p : allProducts) {
            List<User> subscribersList = p.getSubscribersList();
            for (User user : subscribersList) {
                if (user.getId().equals(id)) {
                    productsFollowedByUser.add(p);
                    break;
                }
            }
        }

        logger.info("All approved products followed by user with ID {} fetched successfully", id);
        return productsFollowedByUser;
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUserByTitle(Long id, String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all approved products followed by user with ID: {} and title: {}", id, title);
        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to followed products of user with ID: {}", currentUser.getId(), id);
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);
        }

        List<Product> allProducts = productRepo.findByStatus(Status.APPROVED);
        List<Product> productsFollowedByUser = new ArrayList<>();

        for (Product p : allProducts) {
            List<User> subscribersList = p.getSubscribersList();
            for (User user : subscribersList) {
                if (user.getId().equals(id)) {
                    productsFollowedByUser.add(p);
                    break;
                }
            }
        }

        List<Product> filteredResponse = new ArrayList<>();

        for (Product p : productsFollowedByUser) {
            if (p.getTitle().toLowerCase().contains(title.toLowerCase()))
                filteredResponse.add(p);
        }

        logger.info("All approved products followed by user with ID {} and title {} fetched successfully", id, title);
        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUserByCategory(Long id, String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all approved products followed by user with ID: {} and category: {}", id, category);
        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to followed products of user with ID: {}", currentUser.getId(), id);
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);
        }

        List<Product> allProducts = productRepo.findByStatus(Status.APPROVED);
        List<Product> productsFollowedByUser = new ArrayList<>();

        for (Product p : allProducts) {
            List<User> subscribersList = p.getSubscribersList();
            for (User user : subscribersList) {
                if (user.getId().equals(id)) {
                    productsFollowedByUser.add(p);
                    break;
                }
            }
        }

        List<Product> filteredResponse = productsFollowedByUser.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());

        logger.info("All approved products followed by user with ID {} and category {} fetched successfully", id, category);
        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsFollowedByUserByTitleAndCategory(Long id, String title, String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all approved products followed by user with ID: {}, title: {} and category: {}", id, title, category);
        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to followed products of user with ID: {}", currentUser.getId(), id);
            throw new NotYourProductException("You do not have access to followed products of user with id = " + id);
        }

        List<Product> allProducts = productRepo.findByStatus(Status.APPROVED);
        List<Product> productsFollowedByUser = new ArrayList<>();

        for (Product p : allProducts) {
            List<User> subscribersList = p.getSubscribersList();
            for (User user : subscribersList) {
                if (user.getId().equals(id)) {
                    productsFollowedByUser.add(p);
                    break;
                }
            }
        }

        List<Product> filteredResponse = productsFollowedByUser.stream()
                .filter(product -> product.getCategory().equals(category))
                .filter(product -> product.getTitle().equals(title))
                .collect(Collectors.toList());

        logger.info("All approved products followed by user with ID {}, title {} and category {} fetched successfully", id, title, category);
        return filteredResponse;
    }

    @Override
    public List<Product> getProductsByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching products by title: {}", title);
        List<Product> response = productRepo.findByNameContainingIgnoreCase(title.toLowerCase());
        List<Product> filteredResponse = new ArrayList<>();

        for (Product p : response) {
            if ((p.getStatus().equals(Status.ON_MODERATION) || p.getStatus().equals(Status.MODERATION_DENIED)) &&
                    !p.getId().equals(currentUser.getId()))
                continue;
            else
                filteredResponse.add(p);
        }

        logger.info("Products by title {} fetched successfully", title);
        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsByCategory(String category) {
        logger.info("Fetching all approved products by category: {}", category);
        List<Product> products = productRepo.findProductsByStatusAndCategory(Status.APPROVED, category);
        logger.info("All approved products by category {} fetched successfully", category);
        return products;
    }

    public List<Product> getAllApprovedProductsByTitleAndCategory(String title, String category) {
        logger.info("Fetching all approved products by title: {} and category: {}", title, category);
        List<Product> response = productRepo.findProductsByStatusAndTitleAndCategory(Status.APPROVED, title, category);

        List<Product> filteredResponse = new ArrayList<>();

        for (Product p : response) {
            if (p.getTitle().toLowerCase().contains(title.toLowerCase()))
                filteredResponse.add(p);
        }

        logger.info("All approved products by title {} and category {} fetched successfully", title, category);
        return filteredResponse;
    }

    @Override
    public List<Product> getAllApprovedProductsByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching all approved products by title: {}", title);
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to products on moderation", currentUser.getId());
            throw new NotYourProductException("You do not have access to products on moderation");
        }

        List<Product> products = productRepo.findProductsByStatusAndTitle(Status.APPROVED, title);
        logger.info("All approved products by title {} fetched successfully", title);
        return products;
    }

    @Override
    public List<Product> getProductsForModeratorByTitle(String title) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching products for moderator by title: {}", title);
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to products on moderation", currentUser.getId());
            throw new NotYourProductException("You do not have access to products on moderation");
        }

        List<Product> productsOnModerationList = this.getProductsByTitle(title).stream()
                .filter(product -> product.getStatus() == Status.ON_MODERATION)
                .collect(Collectors.toList());

        logger.info("Products for moderator by title {} fetched successfully", title);
        return productsOnModerationList;
    }

    @Override
    public List<Product> getProductsForModeratorByCategory(String category) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching products for moderator by category: {}", category);
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to products on moderation", currentUser.getId());
            throw new NotYourProductException("You do not have access to products on moderation");
        }

        List<Product> productsOnModerationList = productRepo.findByCategory(category).stream()
                .filter(product -> product.getStatus() == Status.ON_MODERATION)
                .collect(Collectors.toList());

        logger.info("Products for moderator by category {} fetched successfully", category);
        return productsOnModerationList;
    }

    @Override
    public List<Product> getProductsForModeratorByTitleAndCategory(String title, String category) {
        logger.info("Fetching products for moderator by title: {} and category: {}", title, category);
        List<Product> productsOnModerationList = productRepo.findByTitleAndCategory(title, category).stream()
                .filter(product -> product.getStatus() == Status.ON_MODERATION)
                .collect(Collectors.toList());

        logger.info("Products for moderator by title {} and category {} fetched successfully", title, category);
        return productsOnModerationList;
    }

    @Override
    public void addProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Adding new product: {}", product);
        if (currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Moderators can't create products");
            throw new InvalidProductOperationException("Moderators can't create products");
        }

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
        logger.info("Product added successfully: {}", product);
    }

    @Override
    public Product getProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Fetching product by ID: {}", id);
        Product product = productRepo.findById(id).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ProductNotFoundExceptions("Not found product with id = " + id);
                });

        if ((product.getStatus().equals(Status.MODERATION_DENIED) || product.getStatus().equals(Status.ON_MODERATION)) && product.getOwner().getId().equals(currentUser.getId())) {
            logger.error("Access denied for user with ID: {} to view product with ID: {}", currentUser.getId(), product.getId());
            throw new InvalidProductOperationException("You can't view this product with id = " + product.getId());
        }

        logger.info("Product with ID {} fetched successfully", id);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Updating product: {}", product);
        if (product.getId() == null) {
            logger.error("Invalid product update attempt: No ID provided");
            throw new InvalidProductOperationException("You can't edit product which doesn't exist. No id provided");
        }

        Product productSavedInDatabase = getProductById(product.getId());
        if (!Objects.equals(productSavedInDatabase.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("Attempt to update product with ID {} by non-admin user with ID {}", product.getId(), currentUser.getId());
            throw new NotYourProductException("Not your product with id = " + productSavedInDatabase.getId());
        }

        if (productSavedInDatabase.getStatus().equals(Status.ARCHIVED)) {
            logger.error("Attempt to edit archived product with ID: {}", product.getId());
            throw new InvalidProductOperationException("You can't edit archived product. Product id = " + product.getId());
        }

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

        logger.info("Product updated successfully: {}", product);
        return productRepo.save(productSavedInDatabase);
    }

    @Override
    public void removeProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Removing product by ID: {}", id);
        Product product = productRepo.findById(id).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", id);
                    return new ProductNotFoundExceptions("Not found product with id = " + id);
                });

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("Attempt to delete product with ID {} by non-admin user with ID {}", id, currentUser.getId());
            throw new NotYourProductException("You can't archive product with id= " + id + " as it does not belong to you");
        }

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
        logger.info("Product with ID {} deleted successfully", id);
    }

    @Override
    public IdsOfFollowedProductsDto subscribeOnProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Subscribing to product by ID: {}", productId);
        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (product.getOwner().getId().equals(currentUser.getId())) {
            logger.error("Attempt to subscribe to own product by user with ID: {}", currentUser.getId());
            throw new SubscriptionException("You can't subscribe to your project");
        }

        if (!(product.getStatus().equals(Status.APPROVED) || product.getStatus().equals(Status.ARCHIVED))) {
            logger.error("Attempt to subscribe to product on moderation by user with ID: {}", currentUser.getId());
            throw new SubscriptionException("You can't subscribe to project on moderation");
        }

        List<Product> productsFollowedByUserList = currentUser.getFollowedProductsList();
        for (int i = 0; i < productsFollowedByUserList.size(); i++) {
            if (productsFollowedByUserList.get(i).getId().equals(productId)) {
                List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().stream()
                        .map(p -> p.getId())
                        .collect(Collectors.toList());

                IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
                dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);
                logger.info("User with ID {} already subscribed to product with ID {}", currentUser.getId(), productId);
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

        List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);

        logger.info("User with ID {} subscribed to product with ID {} successfully", currentUser.getId(), productId);
        return dto;
    }

    @Override
    public IdsOfFollowedProductsDto unsubscribeFromProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Unsubscribing from product by ID: {}", productId);
        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (!(product.getStatus().equals(Status.APPROVED) || product.getStatus().equals(Status.ARCHIVED))) {
            logger.error("Attempt to unsubscribe from product on moderation by user with ID: {}", currentUser.getId());
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

        List<Long> idsOfFollowedProductsList = currentUser.getFollowedProductsList().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);

        logger.info("User with ID {} unsubscribed from product with ID {} successfully", currentUser.getId(), productId);
        return dto;
    }

    @Override
    public Product approveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Approving product by ID: {}", productId);
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to approve products", currentUser.getId());
            throw new InvalidProductOperationException("Only moderators have access to products on moderation");
        }

        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (!product.getStatus().equals(Status.ON_MODERATION)) {
            logger.error("Attempt to approve product with ID {} which is not on moderation", product.getId());
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
        logger.info("Product with ID {} approved successfully", productId);
        return product;
    }

    @Override
    public Product declineOfModerationByProductId(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Declining moderation for product by ID: {}", productId);
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("Access denied for user with ID: {} to decline products on moderation", currentUser.getId());
            throw new InvalidProductOperationException("Only moderators can decline products on moderation");
        }

        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (!product.getStatus().equals(Status.ON_MODERATION)) {
            logger.error("Attempt to decline moderation for product with ID {} which is not on moderation", product.getId());
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
        logger.info("Moderation for product with ID {} declined successfully", productId);
        return product;
    }

    @Override
    public void archiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Archiving product by ID: {}", productId);
        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("Attempt to archive product with ID {} by non-admin user with ID {}", productId, currentUser.getId());
            throw new NotYourProductException("You can't archive product with id= " + productId + " as it does not belong to you");
        }

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

            logger.info("Product with ID {} was already archived and now deleted", productId);
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
        logger.info("Product with ID {} archived successfully", productId);
    }

    @Override
    public Product unarchiveProductById(Long productId) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        logger.info("Unarchiving product by ID: {}", productId);
        Product product = productRepo.findById(productId).orElseThrow(
                () -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundExceptions("Not found product with id = " + productId);
                });

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("Attempt to unarchive product with ID {} by non-admin user with ID {}", productId, currentUser.getId());
            throw new NotYourProductException("You can't unarchive product with id= " + productId + " as it does not belong to you");
        }

        if (!product.getStatus().equals(Status.ARCHIVED)) {
            logger.error("Attempt to unarchive product with ID {} which hasn't been archived", product.getId());
            throw new InvalidProductOperationException("You can't unarchive product which hasn't been archived. Product id = " + product.getId());
        }

        product.setStatus(Status.ON_MODERATION);
        Notification notification = new Notification(
                product.getOwner().getId(),
                product.getId(),
                product.getCategory(),
                NotificationMessage.PRODUCT_WAS_SET_ON_MODERATION
        );
        notificationService.addNotification(notification);
        productRepo.save(product);

        logger.info("Product with ID {} unarchived successfully", productId);
        return product;
    }
}
