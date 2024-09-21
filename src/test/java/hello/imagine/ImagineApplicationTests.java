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
import org.springframework.boot.test.mock.mockito.MockBean;

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

//	@BeforeEach
//	void setUp() throws Exception {
//		testMember = memberService.findById(1L);
//		if (testMember == null) {
//			testMember = new Member("John Doe", "johndoe", "1990-01-01", "password", "johndoe@example.com", "johndoe123");
//			memberService.register(testMember);
//			testMember = memberService.findById(1L);
//		}
//	}


	@Test
	void contextLoads() {
	}

	@Test
	void testMemberRegistration() throws Exception {
		Member member = new Member("yukang", "yukang0316", "2000-03-16", "password123", "yukang0316@naver.com", "asdfe");
		memberService.register(member);
		Member retrievedMember = memberService.findById(member.getMemberId());
		Assertions.assertNotNull(retrievedMember);
		Assertions.assertEquals("yukang", retrievedMember.getName());
	}

	@Test
	void testAttendanceCheck() throws Exception {
		testMember = memberService.findByNickname("asdfe");
		Member member = memberService.findById(testMember.getMemberId());
		Assertions.assertNotNull(member, "Member should be registered first");

		LocalDate today = LocalDate.now();
		attendanceService.checkAttendance(member.getMemberId(), today);
		List<Attendance> attendanceList = attendanceService.getMonthlyAttendance(member.getMemberId(), today.getYear(), today.getMonthValue());

		Assertions.assertFalse(attendanceList.isEmpty(), "Attendance list should not be empty");
		Assertions.assertTrue(attendanceList.stream().anyMatch(a -> a.getDate().equals(today) && a.isChecked()), "Attendance for today should be checked");
	}



}