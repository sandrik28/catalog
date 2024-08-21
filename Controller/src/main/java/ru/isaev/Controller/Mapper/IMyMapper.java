package ru.isaev.Controller.Mapper;

import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.UserDtos.UserDto;
import ru.isaev.Domain.Users.User;

import java.util.List;

public interface IMyMapper {
    ProductDto productToProductDto(Product product);

    ProductPreviewCardDto productToProductPreviewCardDto(Product product);

    Product productDtoToProduct(ProductDto productDto);
    Product productPreviewCardDtoToProduct(ProductPreviewCardDto productPreviewCardDto);

    List<ProductDto> mapListOfProductsToListOfDtos(List<Product> listOfProducts);

    List<ProductPreviewCardDto> mapListOfProductsToListOfProductPreviewCardDtos(List<Product> listOfProducts);

    UserDto userToUserDto(User user);

    List<UserDto> mapListOfUsersToListOfDtos(List<User> listOfUsers);

    User userDtoToUser(UserDto userDto);
}