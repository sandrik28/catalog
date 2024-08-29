package ru.isaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain.NotificationDtos.NotificationDto;
import ru.isaev.service.Mapper.IMyMapper;
import ru.isaev.service.NotificationService.INotificationService;

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

    @GetMapping("/all/{id}")
    public ResponseEntity<List<NotificationDto>> getAllByUserId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.mapListOfNotificationsToListOfDtos(notificationService.getAllNotificationsOfUserById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.notificationToNotificationDto(notificationService.getNotificationById(id)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}