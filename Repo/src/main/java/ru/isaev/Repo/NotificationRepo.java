package ru.isaev.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isaev.Domain.Notifications.Notification;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);

    Notification findByUserId(Long userId);
}
