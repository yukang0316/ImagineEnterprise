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

}
