package ru.isaev.service.NotificationService;

import ru.isaev.domain.Notifications.Notification;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.Users.User;

import java.util.List;

public interface INotificationService {
    public Notification getNotificationById(Long id);

    public List<Notification> getAllNotificationsOfUserById(Long userId);

    public void addNotification(Notification notification);

    public void addNotificationToSubscribersOfProduct(Notification notification, Product product, List<User> productSubscribersList);

    public void deleteNotificationById(Long notificationId);
}
