package ru.isaev.service_made_by_isaev.notificationService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.domain_made_by_isaev.notifications.Notification;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.NotificationRepo;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.NotYourNotificationException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.UserNotFoundException;
import ru.isaev.service_made_by_isaev.utilities.NotificationsTimestampComparator;

import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);

    private final NotificationRepo notificationRepo;

    @Autowired
    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    @Override
    public Notification getNotificationById(Long id) {
        logger.info("NotificationService - Fetching notification by ID: {}", id);
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Notification notification = notificationRepo.findById(id).orElseThrow(
                () -> {
                    logger.error("NotificationService - Notification not found with ID: {}", id);
                    return new UserNotFoundException("Not found notification with id= " + id);
                }
        );

        if (!currentUser.getId().equals(notification.getUserId()) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("NotificationService - Attempt to access notification with ID {} by non-admin user with ID {}", id, currentUser.getId());
            throw new NotYourNotificationException("You do not have access to notification with id= " + notification.getId());
        }

        logger.info("NotificationService - Notification with ID {} fetched successfully", id);
        return notification;
    }

    @Override
    public List<Notification> getAllNotificationsOfUserById(Long userId) {
        logger.info("NotificationService - Fetching all notifications for user with ID: {}", userId);
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("NotificationService - Attempt to access notifications of user with ID {} by non-admin user with ID {}", userId, currentUser.getId());
            throw new NotYourNotificationException("You do not have access to notifications of user with id = " + userId);
        }

        List<Notification> notificationsList = notificationRepo.findAllByUserId(currentUser.getId());
        notificationsList.sort(new NotificationsTimestampComparator());
        logger.info("NotificationService - All notifications for user with ID {} retrieved successfully", userId);
        return notificationsList;
    }

    @Override
    public void addNotification(Notification notification) {
        logger.info("NotificationService - Adding new notification: {}", notification);
        notificationRepo.save(notification);
        logger.info("NotificationService - Notification added successfully: {}", notification);
    }

    @Override
    public void addNotificationToSubscribersOfProduct(Notification notification, Product product, List<User> productSubscribersList) {
        logger.info("NotificationService - Adding notification to subscribers of product: {}", product);
        for (User user : productSubscribersList) {
            notification.setUserId(user.getId());
            notification.setProductId(product.getId());
            notification.setTitleOfProduct(product.getTitle());
            this.addNotification(notification);
        }
        logger.info("NotificationService - Notification added to subscribers of product successfully: {}", product);
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        logger.info("NotificationService - Deleting notification by ID: {}", notificationId);
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        Notification notification = notificationRepo.findById(notificationId).orElseThrow(
                () -> {
                    logger.error("NotificationService - Notification not found with ID: {}", notificationId);
                    return new UserNotFoundException("Not found notification with id= " + notificationId);
                }
        );

        if (!currentUser.getId().equals(notification.getUserId()) && !currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            logger.error("NotificationService - Attempt to delete notification with ID {} by non-admin user with ID {}", notificationId, currentUser.getId());
            throw new NotYourNotificationException("You do not have access to notification with id= " + notification.getId());
        }

        notificationRepo.deleteById(notificationId);
        logger.info("NotificationService - Notification with ID {} deleted successfully", notificationId);
    }
}