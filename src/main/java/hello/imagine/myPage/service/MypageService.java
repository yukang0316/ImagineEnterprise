package hello.imagine.myPage.service;

import hello.imagine.login.model.Member;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;

public interface MypageService {
    Mypage findById(MypageId mypageId);
    Mypage findByNickname(String nickname);
    Mypage findByPoints(int points);
    Mypage save(Mypage mypage);
    Mypage createOrUpdateMypageFromMember(Member member);
}