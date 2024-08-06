package hello.imagine.myPage.repository;

import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPageRepository extends JpaRepository<Mypage, MypageId> {
    Optional<Mypage> findByNickname(String nickname);
    Optional<Mypage> findByPoints(int points);
}
