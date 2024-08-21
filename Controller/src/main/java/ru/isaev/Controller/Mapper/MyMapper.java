package ru.isaev.Controller.Mapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.isaev.Domain.NotificationDtos.NotificationDto;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.UserDtos.UserDto;
import ru.isaev.Domain.Users.User;
import ru.isaev.Service.NotificationService.INotificationService;
import ru.isaev.Service.ProductService.IProductService;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.UserService.IUserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyMapper implements IMyMapper {
    private final User currentUser;

    private final IProductService productService;

    private final IUserService userService;

    private final INotificationService notificationService;

    public MyMapper(IProductService productService, IUserService userService, INotificationService notificationService) {
        this.productService = productService;
        this.userService = userService;
        this.notificationService = notificationService;
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = currentPrincipal.getUser();
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setOwnerId(product.getOwner().getId());
        dto.setNameOfOwner(product.getOwner().getName());
        dto.setTitle(product.getTitle());
        dto.setType(product.getStatus());
        dto.setEmailOFSupport(product.getEmailOFSupport());
        dto.setLinkToWebSite(product.getLinkToWebSite());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());

        List<User> subscribersOfProduct = product.getSubscribersList();
        for (User subscriber :
                subscribersOfProduct) {
            if (subscriber.getId().equals(currentUser.getId())) {
                dto.setFollowedByCurrentUser(true);
                return dto;
            }
        }

        dto.setFollowedByCurrentUser(false);
        return dto;
    }

    @Override
    public ProductPreviewCardDto productToProductPreviewCardDto(Product product) {
        ProductPreviewCardDto dto = new ProductPreviewCardDto();
        dto.setId(product.getId());
        dto.setOwnerId(product.getOwner().getId());
        dto.setNameOfOwner(product.getOwner().getName());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());

        List<User> subscribersOfProduct = product.getSubscribersList();
        for (User subscriber :
                subscribersOfProduct) {
            if (subscriber.getId().equals(currentUser.getId())) {
                dto.setFollowedByCurrentUser(true);
                return dto;
            }
        }

        dto.setFollowedByCurrentUser(false);
        return dto;
    }

    @Override
    public UserDto userToUserDto(User user) {
       UserDto dto = new UserDto();
       dto.setId(user.getId());
       dto.setName(user.getName());
       dto.setEmail(user.getEmail());

       return dto;
    }

    @Override
    public NotificationDto notificationToNotificationDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setMessage(notification.getMessage());
        dto.setTimestamp(notification.getTimestamp());

        return dto;
    }

    @Override
    public List<NotificationDto> mapListOfNotificationsToListOfDtos(List<Notification> listOfNotifications) {
        List<NotificationDto> dtoList = new ArrayList<>();
        for (Notification notification :
                listOfNotifications) {
            NotificationDto dto = notificationToNotificationDto(notification);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<ProductDto> mapListOfProductsToListOfDtos(List<Product> listOfProducts) {
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product :
                listOfProducts) {
            ProductDto dto = productToProductDto(product);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<ProductPreviewCardDto> mapListOfProductsToListOfProductPreviewCardDtos(List<Product> listOfProducts) {
        List<ProductPreviewCardDto> dtoList = new ArrayList<>();
        for (Product product :
                listOfProducts) {
            ProductPreviewCardDto dto = productToProductPreviewCardDto(product);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<UserDto> mapListOfUsersToListOfDtos(List<User> listOfUsers) {
        List<UserDto> dtoList = new ArrayList<>();
        for (User user :
                listOfUsers) {
            UserDto dto = userToUserDto(user);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        User owner = userService.getUserById(productDto.getOwnerId());
        Product productSavedInDatabase = productService.getProductById(productDto.getId());

        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setOwner(owner);
        product.setTitle(productDto.getTitle());
        product.setStatus(productDto.getType());
        product.setEmailOFSupport(productDto.getEmailOFSupport());
        product.setLinkToWebSite(productDto.getLinkToWebSite());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());

        if (productSavedInDatabase == null)
            product.setSubscribersList(null);
        else
            product.setSubscribersList(productSavedInDatabase.getSubscribersList());

        if (productSavedInDatabase == null)
            product.setChildProduct(null);
        else
            product.setChildProduct(productSavedInDatabase.getChildProduct());

        if (productSavedInDatabase == null)
            product.setParentProduct(null);
        else
            product.setParentProduct(productSavedInDatabase.getParentProduct());

        return product;
    }

    @Override
    public Product productPreviewCardDtoToProduct(ProductPreviewCardDto productPreviewCardDto) {
        User owner = userService.getUserById(productPreviewCardDto.getOwnerId());
        Product productSavedInDatabase = productService.getProductById(productPreviewCardDto.getId());

        Product product = new Product();
        product.setId(productPreviewCardDto.getId());
        product.setTitle(productPreviewCardDto.getTitle());
        product.setOwner(owner);
        product.setTitle(productPreviewCardDto.getTitle());
        product.setStatus(productPreviewCardDto.getStatus());
        product.setDescription(productPreviewCardDto.getDescription());
        product.setCategory(productPreviewCardDto.getCategory());

        if (productSavedInDatabase == null)
            product.setSubscribersList(null);
        else
            product.setSubscribersList(productSavedInDatabase.getSubscribersList());

        if (productSavedInDatabase == null)
            product.setChildProduct(null);
        else
            product.setChildProduct(productSavedInDatabase.getChildProduct());

        if (productSavedInDatabase == null)
            product.setParentProduct(null);
        else
            product.setParentProduct(productSavedInDatabase.getParentProduct());

        if (productSavedInDatabase == null)
            product.setParentProduct(null);
        else
            product.setParentProduct(productSavedInDatabase.getParentProduct());

        if (productSavedInDatabase == null)
            product.setEmailOFSupport(null);
        else
            product.setEmailOFSupport(productSavedInDatabase.getEmailOFSupport());

        if (productSavedInDatabase == null)
            product.setLinkToWebSite(null);
        else
            product.setLinkToWebSite(productSavedInDatabase.getLinkToWebSite());

        return product;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        User user = new User();
        User userSavedInDatabase = userService.getUserById(userDto.getId());

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        if (userSavedInDatabase == null)
            user.setPassword(null);
        else
            user.setPassword(userSavedInDatabase.getPassword());

        if (userSavedInDatabase == null)
            user.setRole(null);
        else
            user.setRole(userSavedInDatabase.getRole());

        if (userSavedInDatabase == null)
            user.setProductsList(null);
        else
            user.setProductsList(userSavedInDatabase.getProductsList());

        if (userSavedInDatabase == null)
            user.setNotificationsList(null);
        else
            user.setNotificationsList(userSavedInDatabase.getNotificationsList());

        if (userSavedInDatabase == null)
            user.setFollowedProductsList(null);
        else
            user.setFollowedProductsList(userSavedInDatabase.getFollowedProductsList());

        return user;
    }

    @Override
    public Notification notificationDtoToNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        Notification notificationSavedInDatabase = notificationService.getNotificationById(notificationDto.getId());

        notification.setId(notification.getId());
        notification.setUserId(notificationDto.getUserId());
        notification.setMessage(notificationDto.getMessage());
        notification.setTimestamp(notificationDto.getTimestamp());

        return notification;
    }
}