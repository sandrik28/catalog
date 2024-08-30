package ru.isaev.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain.Users.Roles;
import ru.isaev.domain.Users.User;
import ru.isaev.repo.UserRepo;
import ru.isaev.service.Security.MyUserDetails;
import ru.isaev.service.Utilities.Exceptions.InvalidLoginAndPasswordException;
import ru.isaev.service.Utilities.Exceptions.InvalidProductOperationException;
import ru.isaev.service.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.service.Utilities.Exceptions.UserNotFoundException;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public IdsOfFollowedProductsDto login(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Not found user with email = " + email));

        if (!user.getPassword().equals(password))
            throw new InvalidLoginAndPasswordException("Invalid login and pwd");

        List<Long> idsOfFollowedProductsList = user.getFollowedProductsList().
                stream().
                map(p -> p.getId()).
                collect(Collectors.toList());

        IdsOfFollowedProductsDto dto = new IdsOfFollowedProductsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);

        return dto;
    }

    public User getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("Not found user with id = " + id));

        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Not found user with email = " + email));

        return user;
    }

    @Override
    public List<User> getUserByRole(Roles role) {
        return userRepo.findByRole(role);
    }

    public void updateUser(User user) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (user.getId() == null)
            throw new InvalidProductOperationException("You can't edit user who doesn't exist. No id provided");

        if (!Objects.equals(currentUser.getId(), user.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + user.getId());

        User userSavedInDatabase = this.getUserById(user.getId());
        if (user.getName() != null)
            userSavedInDatabase.setName(user.getName());
        if (user.getPassword() != null)
            userSavedInDatabase.setPassword(user.getPassword());
        if (user.getRole() != null)
            userSavedInDatabase.setRole(user.getRole());
        if (user.getEmail() != null)
            userSavedInDatabase.setEmail(user.getEmail());

        userRepo.save(userSavedInDatabase);
    }

    public void removeUserById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        User user = userRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("No user with id = " + id));

        if (!Objects.equals(currentUser.getId(), id)  && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + id);

        userRepo.deleteById(id);
    }
}
