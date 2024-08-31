package ru.isaev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.domain.notifications.Notification;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);

    Notification findByUserId(Long userId);
}
