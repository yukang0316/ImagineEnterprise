package hello.imagine.myPage.repository;

import hello.imagine.login.model.Member;
import hello.imagine.myPage.entity.Mypage_Communitylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Mypage_CommunitylistRepository extends JpaRepository<Mypage_Communitylist, Long> {

    // 특정 회원의 Mypage_Communitylist 정보를 조회하는 메서드
    Optional<Mypage_Communitylist> findByMember(Member member);

}
