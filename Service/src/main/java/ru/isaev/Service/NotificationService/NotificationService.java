package ru.isaev.Service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.NotificationRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.UserNotFoundException;

import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private final UserRepo userRepo;

    private final NotificationRepo notificationRepo;

    @Autowired
    public NotificationService(UserRepo userRepo, NotificationRepo notificationRepo) {
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo;
    }

    @Override
    public Notification getNotificationById(Long id) {
        Notification notification = notificationRepo.findById(id).orElseThrow(
                () -> new UserNotFoundException("Not found notification with id= " + id)
        );

        return notification;
    }

    @Override
    public List<Notification> getAllNotifications() {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        return notificationRepo.findByUserId(currentUser.getId());
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
        notificationRepo.deleteById(notificationId);
    }
}
