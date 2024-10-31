package hello.imagine;


import hello.imagine.attendance.service.AttendanceService;
import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImagineApplicationTests {


	@Autowired
	private MemberService memberService;

	@Autowired
	private AttendanceService attendanceService;

	private Member testMember;

}
