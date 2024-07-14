package hello.imagine.login;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LoginDbRepository implements MemberRepository{

    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> pwList = new ArrayList<>();

    @Override
    public void save(Member member) {

        idList.add(member.id);
        pwList.add(member.pw);
    }

    @Override
    public boolean loginCheck(Member member) {

        if(idList.contains(member.id)){
           if( pwList.contains(member.pw))return true;
        }
        return false;
    }


}
