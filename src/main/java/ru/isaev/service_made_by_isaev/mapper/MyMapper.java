package ru.isaev.service_made_by_isaev.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.isaev.domain_made_by_isaev.notificationDtos.NotificationDto;
import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.productDtos.ProductDto;
import ru.isaev.domain_made_by_isaev.productDtos.ProductPreviewCardDto;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.products.Status;
import ru.isaev.domain_made_by_isaev.userDtos.UserDto;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.service_made_by_isaev.productService.IProductService;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.userService.IUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyMapper implements IMyMapper {
    private final IProductService productService;

    private final IUserService userService;

    @Autowired
    public MyMapper(IProductService productService, IUserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setOwnerId(product.getOwner().getId());
        dto.setNameOfOwner(product.getOwner().getName());
        dto.setTitle(product.getTitle());
        dto.setStatus(product.getStatus());
        dto.setEmailOFSupport(product.getEmailOFSupport());
        dto.setLinkToWebSite(product.getLinkToWebSite());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());

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
        dto.setStatus(product.getStatus());
        dto.setTimeOfLastApproval(product.getTimeOfLastApproval());

        return dto;
    }

    @Override
    public UserDto userToUserDto(User user) {
       UserDto dto = new UserDto();
       dto.setId(user.getId());
       dto.setName(user.getName());
       dto.setEmail(user.getEmail());
       dto.setRole(user.getRole());

       List<Long> idsOfFollowedProductsList = user.getFollowedProductsList().stream().map(p -> p.getId()).collect(Collectors.toList());
       dto.setIdOfFollowedProductsList(idsOfFollowedProductsList);

       return dto;
    }

    @Override
    public NotificationDto notificationToNotificationDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setProductId(notification.getProductId());
        dto.setTitleOfProduct(notification.getTitleOfProduct());
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
        Product product = new Product();

        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        if (productDto.getStatus() != null)
            product.setStatus(Status.valueOf(productDto.getStatus().toString()));
        product.setEmailOFSupport(productDto.getEmailOFSupport());
        product.setLinkToWebSite(productDto.getLinkToWebSite());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        if (productDto.getTimeOfLastApproval() != null)
            product.setTimeOfLastApproval(LocalDateTime.parse(productDto.getTimeOfLastApproval().toString()));

        return product;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(Roles.valueOf(userDto.getRole().toString()));
        user.setPassword(user.getPassword());
        user.setRole(userDto.getRole());

        if (userDto.getId() != null) {
            List<Product> updatedListOfProductsFollowedByUser = userDto.
                    getIdOfFollowedProductsList().
                    stream().
                    map(id -> productService.getProductById(id)).
                    collect(Collectors.toList());
            user.setFollowedProductsList(updatedListOfProductsFollowedByUser);
            user.setProductsList(productService.getAllProductsByUser(currentUser));
        }

        return user;
    }
}