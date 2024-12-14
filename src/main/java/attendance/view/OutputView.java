package attendance.view;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import attendance.model.AttendanceManager;
import attendance.model.Penalty;
import attendance.model.StatusRecord;

public class OutputView {
	private static final String DATETIME_RECORD_MESSAGE = "%02d월 %02d일 %s %02d:%02d (%s)";
	private static final String DATETIME_MODIFIED_RECORD_MESSAGE = "%02d:%02d (%s)";
	private static final String DATE_RECORD_MESSAGE = "%02d월 %02d일 %s --:-- (결석)";
	private static final String ATTENDANCE_MESSAGE = "출석: %d회";
	private static final String LATENCY_MESSAGE = "지각: %d회";
	private static final String ABSENCE_MESSAGE = "결석: %d회";

	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(e.getMessage());
	}

	public void printAttendanceRecord(AttendanceManager attendanceManager,LocalDateTime dateTime) {
		String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String status = attendanceManager.getState(dayOfWeek, dateTime.getHour(), dateTime.getMinute());
		System.out.println(String.format(DATETIME_RECORD_MESSAGE,
			dateTime.getMonthValue(), dateTime.getDayOfMonth(), dayOfWeek
			, dateTime.getHour(), dateTime.getMinute(), status));
	}

	public void printAttendanceRecordOfDate(LocalDate date) {
		String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		System.out.println(String.format(DATE_RECORD_MESSAGE, date.getMonthValue(), date.getDayOfMonth(), dayOfWeek));
	}

	public void printModifyRecord(String name, LocalDateTime modifiedDate, AttendanceManager attendanceManager) {
		LocalDateTime pastDate = attendanceManager.getPastRecord(name);
		String pastDayOfWeek = pastDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String pastStatus = attendanceManager.getState(pastDayOfWeek, pastDate.getHour(), pastDate.getMinute());
		System.out.print(String.format(DATETIME_RECORD_MESSAGE,
			pastDate.getMonthValue(), pastDate.getDayOfMonth(), pastDayOfWeek
			, pastDate.getHour(), pastDate.getMinute(), pastStatus));

		System.out.print(" -> ");

		String modifiedDayOfWeek = modifiedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String modifiedStatus = attendanceManager.getState(modifiedDayOfWeek, modifiedDate.getHour(), modifiedDate.getMinute());
		System.out.print(String.format(DATETIME_MODIFIED_RECORD_MESSAGE
			, modifiedDate.getHour(), modifiedDate.getMinute(), modifiedStatus));
		System.out.println(" 수정 완료!");
	}

	public void printResult(StatusRecord record) {
		System.out.println();
		System.out.println(String.format(ATTENDANCE_MESSAGE, record.getAttendance()));
		System.out.println(String.format(LATENCY_MESSAGE, record.getLatency()));
		System.out.println(String.format(ABSENCE_MESSAGE, record.getAbsence()));
		System.out.println();
		if(Penalty.checkPenalty(record).equals("")) {
			return;
		}
		System.out.println(Penalty.checkPenalty(record) + "입니다.");
	}

}
