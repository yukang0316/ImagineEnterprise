package hello.imagine;

import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import hello.imagine.attendance.model.Attendance;
import hello.imagine.attendance.service.AttendanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@SpringBootTest
class ImagineApplicationTests {


	@Autowired
	private MemberService memberService;

	@Autowired
	private AttendanceService attendanceService;

	private Member testMember;

//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	void testMemberRegistration() throws Exception {
//		Member member = new Member("tom", "aaaa", "2002-05-30", "bbbb", "hello@naver.com", "hello");
//		memberService.register(member);
//		Member retrievedMember = memberService.findById(member.getMemberId());
//		Assertions.assertNotNull(retrievedMember);
//		Assertions.assertEquals("tom", retrievedMember.getName());
//	}

//	@Test
//	void testAttendanceCheck() throws Exception {
//		testMember = memberService.findByNickname("hello");
//		Member member = memberService.findById(testMember.getMemberId());
//		Assertions.assertNotNull(member, "Member should be registered first");
//
//		LocalDate today = LocalDate.now();
//		attendanceService.checkAttendance(member.getMemberId(), today);
//		List<Attendance> attendanceList = attendanceService.getMonthlyAttendance(member.getMemberId(), today.getYear(), today.getMonthValue());
//
//		Assertions.assertFalse(attendanceList.isEmpty(), "Attendance list should not be empty");
//		Assertions.assertTrue(attendanceList.stream().anyMatch(a -> a.getDate().equals(today) && a.isChecked()), "Attendance for today should be checked");
//	}
}
