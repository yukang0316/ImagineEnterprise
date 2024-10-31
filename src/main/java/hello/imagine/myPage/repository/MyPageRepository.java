package hello.imagine.myPage.repository;

import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPageRepository extends JpaRepository<Mypage, MypageId> {
    Optional<Mypage> findByMemberId(Long memberId);
    Optional<Mypage> findByNickname(String nickname);
    Optional<Mypage> findByPoints(int points);
    Optional<Mypage> findByEmail(String email);
}
