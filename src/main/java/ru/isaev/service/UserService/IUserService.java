package ru.isaev.service.UserService;

import ru.isaev.domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain.Users.Roles;
import ru.isaev.domain.Users.User;

import java.util.List;

public interface IUserService {
    public void addUser(User user);

    public IdsOfFollowedProductsDto login(String email, String password);

    public User getUserById(Long id);

    public List<User> getUserByRole(Roles role);

    public void updateUser(User user);

    public void removeUserById(Long id);

    public User getUserByEmail(String email);
}
