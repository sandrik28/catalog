package ru.isaev.Controller.Mapper;

import org.springframework.stereotype.Component;
import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.UserDtos.UserDto;
import ru.isaev.Domain.Users.User;

import java.util.List;

@Component
public class MyMapper implements IMyMapper {
    @Override
    public ProductDto productToProductDto(Product product) {
        return null;
    }

    @Override
    public ProductPreviewCardDto productToProductPreviewCardDto(Product product) {
        return null;
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public Product productPreviewCardDtoToProduct(ProductPreviewCardDto productPreviewCardDto) {
        return null;
    }

    @Override
    public List<ProductDto> mapListOfProductsToListOfDtos(List<Product> listOfProducts) {
        return null;
    }

    @Override
    public List<ProductPreviewCardDto> mapListOfProductsToListOfProductPreviewCardDtos(List<Product> listOfProducts) {
        return null;
    }

    @Override
    public UserDto userToUserDto(User user) {
        return null;
    }

    @Override
    public List<UserDto> mapListOfUsersToListOfDtos(List<User> listOfUsers) {
        return null;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        return null;
    }
}