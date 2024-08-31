package ru.isaev.service_made_by_isaev.userService;

import ru.isaev.domain_made_by_isaev.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;

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
