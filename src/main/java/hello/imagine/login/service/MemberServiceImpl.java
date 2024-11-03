package hello.imagine.login.service;

import hello.imagine.login.exception.CustomDuplicateException;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.repository.MyPageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// MemberServiceImpl.java
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MyPageRepository myPageRepository;
    private final LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MyPageRepository myPageRepository, LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.memberRepository = memberRepository;
        this.myPageRepository = myPageRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void register(Member member) throws CustomDuplicateException {
        if (memberRepository.findById(member.getId()).isPresent()) {
            throw new CustomDuplicateException("아이디가 이미 존재합니다.");
        }
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new CustomDuplicateException("이메일이 이미 존재합니다.");
        }
        if (memberRepository.findByNickname(member.getNickname()).isPresent()) {
            throw new CustomDuplicateException("닉네임이 이미 존재합니다.");
        }
        // Mypage 자동 생성
        member.createMypage(); // Mypage 객체 자동 생성

        memberRepository.save(member);
    }

    @Override
    public boolean login(String id, String pw) {
        return memberRepository.findById(id)
                .map(member -> member.getPw().equals(pw))
                .orElse(false);
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElse(null);
    }

    @Override
    public void logout() {
        // 로그아웃 로직 구현
    }

    public Long findMemberIdByUserId(String id) {
        // 예시 코드 - userId를 기반으로 Member 엔티티에서 memberId 조회
        return memberRepository.findById(id)
                .map(Member::getMemberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateMember(Member member) {
        // Member 업데이트
        memberRepository.save(member);

        // Mypage에 Member 정보 반영
        List<Mypage> mypages = member.getMypages();
        if (mypages != null && !mypages.isEmpty()) {
            for (Mypage mypage : mypages) {
                mypage.setNickname(member.getNickname());
                mypage.setPoints(member.getPoints());
                mypage.setEmail(member.getEmail());
                myPageRepository.save(mypage);
            }
        } else {
            // Mypage가 없다면 새로 생성
            Mypage newMypage = new Mypage(member);
            myPageRepository.save(newMypage);
        }
    }

}