package ru.isaev.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Service.Utilities.Exceptions.UserNotFoundException;


import java.util.List;
import java.util.Objects;


@Service
public class UserService implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User getUserById(Long id) {

        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("Not found user with id = " + id));

        return user;
    }

    @Override
    public List<User> getUserByRole(Roles role) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        return userRepo.findByRole(role);
    }

    public void updateUser(User user) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!Objects.equals(currentUser.getId(), user.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + user.getId());

        userRepo.save(user);
    }

    public void removeUserById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentOwner = currentPrincipal.getUser();

        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("No user with id = " + id));

        if (!Objects.equals(currentOwner.getId(), id)  && currentOwner.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + id);

        userRepo.deleteById(id);
    }
}
