package hello.imagine.Home;

import hello.imagine.myPage.entity.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // 특정 Mypage에 해당하는 알림 목록 조회
    List<Notification> findByMypage(Mypage mypage);

    // 읽지 않은 알림만 조회
    List<Notification> findByMypageAndIsReadFalse(Mypage mypage);
}
