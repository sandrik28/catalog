package ru.isaev.service.Utilities;

import ru.isaev.domain.Notifications.Notification;

import java.util.Comparator;

public class NotificationsTimestampComparator implements Comparator<Notification> {
    @Override
    public int compare(Notification o1, Notification o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
