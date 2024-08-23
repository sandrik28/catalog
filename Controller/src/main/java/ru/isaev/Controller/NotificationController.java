package ru.isaev.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.Controller.Mapper.IMyMapper;
import ru.isaev.Domain.NotificationDtos.NotificationDto;
import ru.isaev.Domain.Notifications.Notification;
import ru.isaev.Service.NotificationService.INotificationService;

import java.util.List;
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final INotificationService notificationService;

    private final IMyMapper mapper;

    @Autowired
    public NotificationController(INotificationService notificationService, IMyMapper mapper) {
        this.notificationService = notificationService;
        this.mapper = mapper;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> getAll() {
        return new ResponseEntity<>(
                mapper.mapListOfNotificationsToListOfDtos(notificationService.getAllNotifications()),
                HttpStatus.OK
        );
    }
}