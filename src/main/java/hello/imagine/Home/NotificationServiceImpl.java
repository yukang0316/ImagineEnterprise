package hello.imagine.Home;

import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {  // 인터페이스 구현
    private final MyPageRepository mypageRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(MyPageRepository mypageRepository, NotificationRepository notificationRepository) {
        this.mypageRepository = mypageRepository;
        this.notificationRepository = notificationRepository;
    }

    // 특정 Mypage의 모든 알림을 조회
    @Override
    public List<Notification> getNotificationsByMypage(Long memberId) {
        Mypage mypage = mypageRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Mypage not found with memberId: " + memberId));
        return notificationRepository.findByMypage(mypage);
    }

    // 특정 Mypage에 새로운 알림을 생성
    @Override
    public Notification createNotificationForMypage(Long memberId, String type, String message) {
        Mypage mypage = mypageRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Mypage not found with memberId: " + memberId));

        Notification notification = new Notification();
        notification.setMypage(mypage);
        notification.setType(type);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    // 특정 알림을 읽음 상태로 변경
    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));

        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    // 특정 Mypage의 모든 알림을 읽음 처리
    @Override
    public void markAllAsRead(Long memberId) {
        Mypage mypage = mypageRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Mypage not found with memberId: " + memberId));

        List<Notification> notifications = notificationRepository.findByMypage(mypage);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}
