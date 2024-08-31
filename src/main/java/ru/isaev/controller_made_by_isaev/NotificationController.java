package ru.isaev.controller_made_by_isaev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain_made_by_isaev.notificationDtos.NotificationDto;
import ru.isaev.service_made_by_isaev.mapper.IMyMapper;
import ru.isaev.service_made_by_isaev.notificationService.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private static final Logger logger = LogManager.getLogger(NotificationController.class);

    private final INotificationService notificationService;
    private final IMyMapper mapper;

    @Autowired
    public NotificationController(INotificationService notificationService, IMyMapper mapper) {
        this.notificationService = notificationService;
        this.mapper = mapper;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<NotificationDto>> getAllByUserId(@PathVariable("id") Long id) {
        logger.info("NotificationController - Received request to get all notifications for user with ID: {}", id);
        List<NotificationDto> notificationDtos = mapper.mapListOfNotificationsToListOfDtos(notificationService.getAllNotificationsOfUserById(id));
        logger.info("NotificationController - All notifications for user with ID {} retrieved successfully", id);
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable("id") Long id) {
        logger.info("NotificationController - Received request to get notification by ID: {}", id);
        NotificationDto notificationDto = mapper.notificationToNotificationDto(notificationService.getNotificationById(id));
        logger.info("NotificationController - Notification with ID {} retrieved successfully", id);
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable Long id) {
        logger.info("NotificationController - Received request to delete notification by ID: {}", id);
        notificationService.deleteNotificationById(id);
        logger.info("NotificationController - Notification with ID {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}