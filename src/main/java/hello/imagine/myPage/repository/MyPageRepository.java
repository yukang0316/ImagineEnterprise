package hello.imagine.myPage.repository;

import hello.imagine.myPage.entity.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPageRepository extends JpaRepository<Mypage,Long> {
    // Member와의 관계를 통해 Mypage를 조회
    Optional<Mypage> findById(String id);
    Optional<Mypage> findByNickname(String nickname);
    Optional<Mypage> findByPoints(int points);
    Optional<Mypage> findByEmail(String email);

}

