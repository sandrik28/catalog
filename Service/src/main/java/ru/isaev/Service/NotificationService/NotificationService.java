package ru.isaev.Service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.NotificationRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.NotYourNotificationException;
import ru.isaev.Service.Utilities.Exceptions.NotYourProductException;
import ru.isaev.Service.Utilities.Exceptions.UserNotFoundException;
import ru.isaev.Service.Utilities.NotificationsTimestampComparator;

import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private final UserRepo userRepo;

    private final NotificationRepo notificationRepo;

    private final User currentUser;

    @Autowired
    public NotificationService(UserRepo userRepo, NotificationRepo notificationRepo) {
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo;

        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = currentPrincipal.getUser();
    }

    @Override
    public Notification getNotificationById(Long id) {
        Notification notification = notificationRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("Not found notification with id= " + id)
        );

        if (!currentUser.getId().equals(notification.getUserId()) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourNotificationException("You do not have access to notification with id= " + notification.getId());

        return notification;
    }
    @Override
    public List<Notification> getAllNotificationsOfUserById(Long userId) {
        if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourNotificationException("You do not have access to notifications of user with id = " + userId);

        List<Notification> notificationsList = notificationRepo.findAllByUserId(currentUser.getId());
        notificationsList.sort(new NotificationsTimestampComparator());
        return notificationsList;
    }

    @Override
    public void addNotification(Notification notification) {
        notificationRepo.save(notification);
    }

    @Override
    public void addNotificationToSubscribersOfProduct(Notification notification, Product product) {
        for (User user :
                product.getSubscribersList()) {
            notification.setUserId(user.getId());
            notification.setProductId(product.getId());
            notification.setCategoryOfProduct(product.getCategory());
            this.addNotification(notification);
        }
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId).orElseThrow(
                () -> new UserNotFoundException("Not found notification with id= " + notificationId)
        );

        if (!currentUser.getId().equals(notification.getUserId()) && !currentUser.getRole().equals(Roles.ROLE_ADMIN))
            throw new NotYourNotificationException("You do not have access to notification with id= " + notification.getId());

        notificationRepo.deleteById(notificationId);
    }
}
