package hello.imagine.myPage.repository;

import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPageRepository extends JpaRepository<Mypage, MypageId> {
    // Member와의 관계를 통해 Mypage를 조회
    Optional<Mypage> findByMemberId(Long memberId);
    Optional<Mypage> findByNickname(String nickname);
    Optional<Mypage> findByPoints(int points);
    Optional<Mypage> findByEmail(String email);

    @Query("SELECT m FROM Mypage m JOIN FETCH m.member WHERE m.memberId = :memberId")
    Optional<Mypage> findMypageWithMember(@Param("memberId") Long memberId);

}

