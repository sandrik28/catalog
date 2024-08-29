package ru.isaev.service.Mapper;

import ru.isaev.domain.NotificationDtos.NotificationDto;
import ru.isaev.domain.Notifications.Notification;
import ru.isaev.domain.ProductDtos.ProductDto;
import ru.isaev.domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.UserDtos.UserDto;
import ru.isaev.domain.Users.User;

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