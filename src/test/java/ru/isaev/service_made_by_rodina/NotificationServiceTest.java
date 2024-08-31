package ru.isaev.service_made_by_rodina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.NotificationRepo;
import ru.isaev.repo_made_by_isaev.IUserRepo;
import ru.isaev.service_made_by_isaev.notificationService.NotificationService;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.NotYourNotificationException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.UserNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;
    @Mock
    private NotificationRepo notificationRepo;
    @Mock
    private IUserRepo userRepo;
    private User user;
    private MyUserDetails myUserDetails;
    private Authentication authentication;
    private Notification notification;
    private Product product;

    @BeforeEach
    void before()
    {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Roles.ROLE_USER);

        myUserDetails = new MyUserDetails(user);
        authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

        notification = new Notification();
        notification.setId(1L);
        notification.setUserId(user.getId());
        notification.setProductId(1L);
        notification.setTitleOfProduct("Test title");

        product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setCategory("Test Category");
        product.setOwner(user);
    }

    @Test
    public void getNotificationByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(notificationRepo.findById(1L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(notification, result);
    }

    @Test
    public void getNotificationByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(notificationRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.getNotificationById(1L));
    }

    @Test
    public void getNotificationByIdNotYourNotificationTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        notification.setUserId(2L);
        when(notificationRepo.findById(1L)).thenReturn(Optional.of(notification));

        assertThrows(NotYourNotificationException.class, () -> notificationService.getNotificationById(1L));
    }

    @Test
    public void getAllNotificationsOfUserByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationRepo.findAllByUserId(1L)).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotificationsOfUserById(1L);

        assertNotNull(result);
        assertEquals(notifications, result);}

    @Test
    public void getAllNotificationsOfUserByIdNotYourNotificationTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

        assertThrows(NotYourNotificationException.class, () -> notificationService.getAllNotificationsOfUserById(2L));
    }

    @Test
    public void addNotificationTest()
    {
        notificationService.addNotification(notification);
        verify(notificationRepo, times(1)).save(notification);
    }

    @Test
    public void addNotificationToSubscribersOfProductTest()
    {
        List<User> subscribers = Arrays.asList(user);
        notificationService.addNotificationToSubscribersOfProduct(notification, product, subscribers);

        verify(notificationRepo, times(1)).save(notification);
    }

    @Test
    public void deleteNotificationByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(notificationRepo.findById(1L)).thenReturn(Optional.of(notification));

        notificationService.deleteNotificationById(1L);

        verify(notificationRepo, times(1)).deleteById(1L);
    }

    @Test
    public void deleteNotificationByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(notificationRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> notificationService.deleteNotificationById(1L));
    }

    @Test
    public void deleteNotificationByIdNotYourNotificationTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        notification.setUserId(2L);
        when(notificationRepo.findById(1L)).thenReturn(Optional.of(notification));

        assertThrows(NotYourNotificationException.class, () -> notificationService.deleteNotificationById(1L));
    }
}
