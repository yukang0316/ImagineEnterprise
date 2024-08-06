package hello.imagine.myPage.service;

import hello.imagine.login.model.Member;
import hello.imagine.myPage.entity.Mypage;
import hello.imagine.myPage.entity.MypageId;
import hello.imagine.myPage.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageServiceImpl implements MypageService{
    private final MyPageRepository myPageRepository;

    @Autowired
    public MypageServiceImpl(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    @Override
    public Mypage findById(MypageId mypageId) {
        return myPageRepository.findById(mypageId).orElse(null);
    }

    @Override
    public Mypage findByNickname(String nickname) {
        return myPageRepository.findByNickname(nickname).orElse(null);
    }

    @Override
    public Mypage findByPoints(int points) {
        return myPageRepository.findByPoints(points).orElse(null);
    }

    @Override
    public Mypage save(Mypage mypage) {
        return myPageRepository.save(mypage);
    }

    @Override
    public Mypage createOrUpdateMypageFromMember(Member member){
        MypageId mypageId = new MypageId(member.getMemberId());
        Mypage mypage = myPageRepository.findById(mypageId).orElse(new Mypage(member));
        mypage.setNickname(member.getNickname());
        mypage.setPoints(member.getPoints());
        return myPageRepository.save(mypage);
    }
}
