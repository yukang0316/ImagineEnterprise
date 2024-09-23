package hello.imagine.Home;

import hello.imagine.myPage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage/notifications")
public class NotificationController {
    private final MypageService mypageService;

    @Autowired
    public NotificationController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    // Mypage에서 알림 조회
    //@GetMapping("/{memberId}")
    /*public ResponseEntity<List<Notification>> getNotificationsByMypage(@PathVariable Long memberId) {
        List<Notification> notifications = mypageService.getNotificationByMypage(memberId);
        return ResponseEntity.ok(notifications);
    }*/
}
