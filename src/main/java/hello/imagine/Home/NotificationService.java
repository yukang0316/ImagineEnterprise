package hello.imagine.Home;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsByMypage(Long memberId);
    Notification createNotificationForMypage(Long memberId, String type, String message);
    Notification markAsRead(Long notificationId);
    void markAllAsRead(Long memberId);
}
