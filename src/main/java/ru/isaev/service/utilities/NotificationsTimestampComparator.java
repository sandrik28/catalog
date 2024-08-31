package ru.isaev.service.utilities;

import ru.isaev.domain.notifications.Notification;

import java.util.Comparator;

public class NotificationsTimestampComparator implements Comparator<Notification> {
    @Override
    public int compare(Notification o1, Notification o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
