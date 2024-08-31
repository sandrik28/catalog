package ru.isaev.service.notificationService;

import ru.isaev.domain.notifications.Notification;
import ru.isaev.domain.products.Product;
import ru.isaev.domain.users.User;

import java.util.List;

public interface INotificationService {
    public Notification getNotificationById(Long id);

    public List<Notification> getAllNotificationsOfUserById(Long userId);

    public void addNotification(Notification notification);

    public void addNotificationToSubscribersOfProduct(Notification notification, Product product, List<User> productSubscribersList);

    public void deleteNotificationById(Long notificationId);
}
