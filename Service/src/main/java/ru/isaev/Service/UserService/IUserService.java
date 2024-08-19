package ru.isaev.Service.UserService;

import ru.isaev.Domain.Users.User;

public interface IUserService {
    public void addUser(User user);

    public User getUserById(Long id);

    public void updateUser(User user);

    public void removeUserById(Long id);




}
