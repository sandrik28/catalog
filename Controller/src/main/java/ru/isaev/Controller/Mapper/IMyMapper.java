package ru.isaev.Controller.Mapper;

import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.UserDtos.UserDto;
import ru.isaev.Domain.Users.User;

import java.util.List;

public interface IMyMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<ProductDto> mapListOfProductsToListOfDtos(List<Product> listOfProducts);

    UserDto userToUserDto(User user);

    List<UserDto> mapListOfUsersToListOfDtos(List<User> listOfUsers);

    User userDtoToUser(UserDto userDto);
}