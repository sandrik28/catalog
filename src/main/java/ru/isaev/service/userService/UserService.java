package ru.isaev.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isaev.domain.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain.users.Roles;
import ru.isaev.domain.users.User;
import ru.isaev.repo.IUserRepo;
import ru.isaev.service.security.MyUserDetails;
import ru.isaev.service.utilities.Exceptions.InvalidLoginAndPasswordException;
import ru.isaev.service.utilities.Exceptions.InvalidProductOperationException;
import ru.isaev.service.utilities.Exceptions.NotYourProfileException;
import ru.isaev.service.utilities.Exceptions.UserNotFoundException;


import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {

    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void addUser(User user) {
        userRepo.save(user);
    }

    @Override
    public UserIdAndLikedIdsDto login(String email, String hashedPassword) {
        User user = userRepo.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Not found user with email = " + email));

        if (!passwordEncoder.matches(user.getPassword(), hashedPassword))
            throw new InvalidLoginAndPasswordException("Invalid login and pwd");

        List<Long> idsOfFollowedProductsList = user.getFollowedProductsList().
                stream().
                map(p -> p.getId()).
                collect(Collectors.toList());

        UserIdAndLikedIdsDto dto = new UserIdAndLikedIdsDto();
        dto.setIdsOfFollowedProducts(idsOfFollowedProductsList);
        dto.setUserId(user.getId());
        dto.setRole(user.getRole());

        String stringToCode = email + ":" + user.getPassword();
        byte[] inputBytes = stringToCode.getBytes();
        String encodedString = new String(Base64.getEncoder().encode(inputBytes));
        String base64EncodedString = "Basic " + encodedString;

        dto.setBase64EncodedString(base64EncodedString);

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
