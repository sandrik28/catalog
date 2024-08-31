package ru.isaev.service_made_by_isaev.userService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.domain_made_by_isaev.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.IUserRepo;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.InvalidLoginAndPasswordException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.InvalidProductOperationException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.NotYourProfileException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.UserNotFoundException;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void addUser(User user) {
        logger.info("UserService - Adding new user: {}", user);
        userRepo.save(user);
        logger.info("UserService - User added successfully: {}", user);
    }

    @Override
    public UserIdAndLikedIdsDto login(String email, String hashedPassword) {
        logger.info("UserService - Attempting login for email: {}", email);
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> {
                    logger.error("UserService - User not found with email: {}", email);
                    return new UserNotFoundException("Not found user with email = " + email);
                });

        if (!passwordEncoder.matches(user.getPassword(), hashedPassword)) {
            logger.error("UserService - Invalid login and password for email: {}", email);
            throw new InvalidLoginAndPasswordException("Invalid login and pwd");
        }

        List<Long> idsOfFollowedProductsList = user.getFollowedProductsList().stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        UserIdAndLikedIdsDto dto = new UserIdAndLikedIdsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);
        dto.setUserId(user.getId());
        dto.setRole(user.getRole());

        String stringToCode = email + ":" + user.getPassword();
        byte[] inputBytes = stringToCode.getBytes();
        String encodedString = new String(Base64.getEncoder().encode(inputBytes));
        String base64EncodedString = "Basic " + encodedString;

        dto.setBase64EncodedString(base64EncodedString);

        logger.info("UserService - Login successful for email: {}", email);
        return dto;
    }

    @Override
    public User getUserById(Long id) {
        logger.info("UserService - Fetching user by ID: {}", id);
        User user = userRepo.findById(id).orElseThrow(
                () -> {
                    logger.error("UserService - User not found with ID: {}", id);
                    return new UserNotFoundException("Not found user with id = " + id);
                });
        logger.info("UserService - User with ID {} fetched successfully", id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info("UserService - Fetching user by email: {}", email);
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> {
                    logger.error("UserService - User not found with email: {}", email);
                    return new UserNotFoundException("Not found user with email = " + email);
                });
        logger.info("UserService - User with email {} fetched successfully", email);
        return user;
    }

    @Override
    public List<User> getUserByRole(Roles role) {
        logger.info("UserService - Fetching users by role: {}", role);
        List<User> users = userRepo.findByRole(role);
        logger.info("UserService - Users with role {} fetched successfully", role);
        return users;
    }

    @Override
    public void updateUser(User user) {
        logger.info("UserService - Updating user: {}", user);
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (user.getId() == null) {
            logger.error("UserService - Invalid user update attempt: No ID provided");
            throw new InvalidProductOperationException("You can't edit user who doesn't exist. No id provided");
        }

        if (!Objects.equals(currentUser.getId(), user.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("UserService - Attempt to update user with ID {} by non-admin user with ID {}", user.getId(), currentUser.getId());
            throw new NotYourProfileException("Not your profile with id = " + user.getId());
        }

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
        logger.info("UserService - User updated successfully: {}", user);
    }

    @Override
    public void removeUserById(Long id) {
        logger.info("UserService - Removing user by ID: {}", id);
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        User user = userRepo.findById(id).orElseThrow(
                () -> {
                    logger.error("UserService - User not found with ID: {}", id);
                    return new UserNotFoundException("No user with id = " + id);
                });

        if (!Objects.equals(currentUser.getId(), id) && currentUser.getRole() != Roles.ROLE_ADMIN) {
            logger.error("UserService - Attempt to delete user with ID {} by non-admin user with ID {}", id, currentUser.getId());
            throw new NotYourProfileException("Not your profile with id = " + id);
        }

        userRepo.deleteById(id);
        logger.info("UserService - User with ID {} deleted successfully", id);
    }
}