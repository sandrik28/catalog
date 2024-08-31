package ru.isaev.service_made_by_isaev.notificationService;

import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.users.User;

import java.util.List;

public interface INotificationService {
    public Notification getNotificationById(Long id);

    public List<Notification> getAllNotificationsOfUserById(Long userId);

    public void addNotification(Notification notification);

    public void addNotificationToSubscribersOfProduct(Notification notification, Product product, List<User> productSubscribersList);

    public void deleteNotificationById(Long notificationId);
}
