package ru.isaev.service.userService;

import ru.isaev.domain.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain.users.Roles;
import ru.isaev.domain.users.User;

import java.util.List;

public interface IUserService {
    public void addUser(User user);

    public UserIdAndLikedIdsDto login(String email, String hashedPassword);

    public User getUserById(Long id);

    public List<User> getUserByRole(Roles role);

    public void updateUser(User user);

    public void removeUserById(Long id);

    public User getUserByEmail(String email);
}
