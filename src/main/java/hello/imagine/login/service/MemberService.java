package hello.imagine.login.service;

import hello.imagine.login.exception.CustomDuplicateException;
import hello.imagine.login.model.Member;

// MemberService.java
public interface MemberService {
    void register(Member member) throws CustomDuplicateException;
    boolean login(String id, String pw);
    Member findById(Long memberId);
    Member findByEmail(String email);
    Member findByNickname(String nickname);
    void logout();
}