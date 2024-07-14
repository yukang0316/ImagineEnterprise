package hello.imagine.login;

public interface MemberRepository {
    void save(Member member);
    boolean loginCheck(Member member);
}
