package ru.isaev.service_made_by_rodina;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.IUserRepo;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.userService.UserService;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.NotYourProfileException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    private User user;
    private MyUserDetails myUserDetails;
    private Authentication authentication;

    @BeforeEach
    void before()
    {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setRole(Roles.ROLE_USER);
        myUserDetails = new MyUserDetails(user);
        authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
    }

    @Test
    public void addUserTest()
    {
        userService.addUser(user);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void getUserByIdTest()
    {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    public void getUserByIdTestFailed()
    {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void getUserByEmailTest()
    {
        when(userRepo.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        User result = userService.getUserByEmail("testuser@example.com");
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    public void getUserByEmailTestFailed()
    {
        when(userRepo.findByEmail("testuser@example.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("testuser@example.com"));
    }

    @Test
    public void getUserByRoleTest()
    {
        List<User> users = Arrays.asList(user);
        when(userRepo.findByRole(Roles.ROLE_USER)).thenReturn(users);

        List<User> result = userService.getUserByRole(Roles.ROLE_USER);
        assertNotNull(result);
        assertEquals(users, result);
    }

    @Test
    public void updateUserTest()
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Update Test User");
        updatedUser.setEmail("testuser@example.com");
        updatedUser.setPassword("password");
        updatedUser.setRole(Roles.ROLE_USER);

        userService.updateUser(updatedUser);

        verify(userRepo, times(1)).save(user);
        assertEquals("Update Test User", user.getName());
    }

    @Test
    public void updateUserTestFailed()
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User updatedUser = new User();
        updatedUser.setId(2L);
        assertThrows(NotYourProfileException.class, () -> userService.updateUser(updatedUser));
    }

    @Test
    public void removeUserByIdTest()
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        userService.removeUserById(1L);
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    public void removeUserByIdTestFailed()
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepo.findById(2L)).thenReturn(Optional.of(new User()));
        assertThrows(NotYourProfileException.class, () -> userService.removeUserById(2L));
    }
}
