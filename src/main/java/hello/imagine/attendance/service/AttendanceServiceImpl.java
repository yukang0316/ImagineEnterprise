package hello.imagine.attendance.Service;

import hello.imagine.attendance.model.Attendance;
import hello.imagine.attendance.Repository.AttendanceDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AttendServiceImpl implements AttendService {


    private final AttendanceDbRepository attendanceDbRepository;

    @Autowired
    public AttendServiceImpl(AttendanceDbRepository attendanceDbRepository) {
        this.attendanceDbRepository = attendanceDbRepository;
    }

    @Override
    public boolean CheckIn(String Id) {
        Optional<Attendance> optionalAttendance = attendanceDbRepository.findById(Id);
        Date now = new Date();

        if (optionalAttendance.isPresent()) {
            Attendance attendance = optionalAttendance.get();
            Date Date = attendance.getDate();

            if (!isSameDay(Date, now)) {
                attendance.setPoint(attendance.getPoint() + 20);
                attendance.setDate(now);
                attendanceDbRepository.save(attendance);
                return true;

            } else {
                return false;
            }


        } else {
            Attendance attendance = new Attendance(Id,20, new Date());
            attendanceDbRepository.save(attendance);
            return true;
        }

    }

    @Override
    public int getPoint(String Id) {
        Optional<Attendance> optionalAttendance = attendanceDbRepository.findById(Id);
        return optionalAttendance.map(Attendance::getPoint).orElse(0);
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

}


