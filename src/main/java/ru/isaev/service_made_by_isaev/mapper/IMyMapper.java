package ru.isaev.service_made_by_isaev.mapper;

import ru.isaev.domain_made_by_isaev.notificationDtos.NotificationDto;
import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.productDtos.ProductDto;
import ru.isaev.domain_made_by_isaev.productDtos.ProductPreviewCardDto;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.userDtos.UserDto;
import ru.isaev.domain_made_by_isaev.users.User;

import java.util.List;


public interface IMyMapper {
    ProductDto productToProductDto(Product product);

    ProductPreviewCardDto productToProductPreviewCardDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> mapListOfProductsToListOfDtos(List<Product> listOfProducts);

    List<ProductPreviewCardDto> mapListOfProductsToListOfProductPreviewCardDtos(List<Product> listOfProducts);

    UserDto userToUserDto(User user);

    List<UserDto> mapListOfUsersToListOfDtos(List<User> listOfUsers);

    User userDtoToUser(UserDto userDto);

    NotificationDto notificationToNotificationDto(Notification notification);

    List<NotificationDto> mapListOfNotificationsToListOfDtos(List<Notification> listOfNotifications);
}