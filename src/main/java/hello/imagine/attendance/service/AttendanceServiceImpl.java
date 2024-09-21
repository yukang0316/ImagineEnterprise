    package hello.imagine.attendance.service;

    import hello.imagine.attendance.model.Attendance;
    import hello.imagine.attendance.repository.AttendanceRepository;
    import hello.imagine.login.model.Member;
    import hello.imagine.login.repository.MemberRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.YearMonth;
    import java.util.List;

    @Service
    public class AttendanceServiceImpl implements AttendanceService {

        @Autowired
        private AttendanceRepository attendanceRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Override
        public void checkAttendance(Long memberId, LocalDate date) throws Exception {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new Exception("멤버를 찾을 수 없습니다."));
            if (attendanceRepository.existsByMemberAndDate(member, date)) {
                throw new Exception("오늘 출석이 이미 확인되었습니다.");
            }

            // attendance 포인트 업데이트
            Attendance attendance = new Attendance();
            attendance.setMember(member);
            attendance.setDate(date);
            attendance.setPoints(5);
            attendanceRepository.save(attendance);


            // Member 포인트 업데이트
            member.setPoints(member.getPoints() + 5);
            memberRepository.save(member);
        }

        @Override
        public List<Attendance> getMonthlyAttendance(Long memberId, int year, int month) throws Exception {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new Exception("멤버를 찾을 수 없습니다."));
            return attendanceRepository.findByMemberAndDateBetween(
                    member,
                    LocalDate.of(year, month, 1),
                    LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth())
            );
        }

        @Override
        public int getPoints(Long memberId) throws Exception {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new Exception("멤버를 찾을 수 없습니다."));

            return member.getPoints();  // Member 엔터티에서 포인트 조회
        }
    }