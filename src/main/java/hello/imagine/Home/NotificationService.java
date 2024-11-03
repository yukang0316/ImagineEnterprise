package hello.imagine.Home;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsByMypage(String id);
    Notification createNotificationForMypage(String id, String type, String message);
    Notification markAsRead(Long notificationId);
    void markAllAsRead(String id);
}
