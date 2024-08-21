package ru.isaev.Service.UserService;

import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;

import java.util.List;

public interface IUserService {
    public void addUser(User user);

    public User getUserById(Long id);

    public List<User> getUserByRole(Roles role);

    public void updateUser(User user);

    public void removeUserById(Long id);

    public User getUserByEmail(String email);
}
