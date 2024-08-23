package ru.isaev.Service.NotificationService;

import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Domain.Products.Product;

import java.util.List;

public interface INotificationService {
    public Notification getNotificationById(Long id);

    public List<Notification> getAllNotifications();

    public void addNotification(Notification notification);

    public void addNotificationToSubscribersOfProduct(Notification notification, Product product);

    public void deleteNotificationById(Long notificationId);
}
