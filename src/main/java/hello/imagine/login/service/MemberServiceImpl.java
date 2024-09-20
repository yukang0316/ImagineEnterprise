package hello.imagine.login.service;

import hello.imagine.login.exception.CustomDuplicateException;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



// MemberServiceImpl.java
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void register(Member member) throws CustomDuplicateException {
        if (memberRepository.findById(member.getId()).isPresent()) {
            throw new CustomDuplicateException("id is already exist");
        }
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new CustomDuplicateException("email is already exist");
        }
        if (memberRepository.findByNickname(member.getNickname()).isPresent()) {
            throw new CustomDuplicateException("nickname is already exist");
        }
        memberRepository.save(member);
    }

    @Override
    public boolean login(String id, String pw) {
        return memberRepository.findById(id)
                .map(member -> member.getPw().equals(pw))
                .orElse(false);
    }

    @Override
    public Member findById(String id) {
        return memberRepository.findById(id).orElse(null);
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
}