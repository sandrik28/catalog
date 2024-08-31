package ru.isaev.service.mapper;

import ru.isaev.domain.notificationDtos.NotificationDto;
import ru.isaev.domain.notifications.Notification;
import ru.isaev.domain.productDtos.ProductDto;
import ru.isaev.domain.productDtos.ProductPreviewCardDto;
import ru.isaev.domain.products.Product;
import ru.isaev.domain.userDtos.UserDto;
import ru.isaev.domain.users.User;

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