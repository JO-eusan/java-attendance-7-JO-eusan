package attendance.view;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import attendance.model.AttendanceManager;

public class OutputView {

	private static final String ERROR_PREFIX = "[ERROR] ";
	private static final String SELECT_FUNCTION_MESSAGE = "%02d월 %02d일 %s %02d:%02d (%s)";

	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(ERROR_PREFIX + e.getMessage());
	}

	public void printErrorMessage(IllegalArgumentException e) {
		System.out.println(ERROR_PREFIX + e.getMessage());
	}

	public void printAttendanceRecord(AttendanceManager attendanceManager,LocalDateTime dateTime) {
		String dayOfWeek = dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String status = attendanceManager.getState(dayOfWeek, dateTime.getHour(), dateTime.getMinute());
		System.out.println(String.format(SELECT_FUNCTION_MESSAGE,
			dateTime.getMonthValue(), dateTime.getDayOfMonth(), dayOfWeek
			, dateTime.getHour(), dateTime.getMinute(), status));
	}

	public void printModifyRecord(String name, LocalDateTime modifiedDate, AttendanceManager attendanceManager) {
		LocalDateTime pastDate = attendanceManager.getPastRecord(name);
		String pastDayOfWeek = pastDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String pastStatus = attendanceManager.getState(pastDayOfWeek, pastDate.getHour(), pastDate.getMinute());
		System.out.print(String.format(SELECT_FUNCTION_MESSAGE,
			pastDate.getMonthValue(), pastDate.getDayOfMonth(), pastDayOfWeek
			, pastDate.getHour(), pastDate.getMinute(), pastStatus));

		System.out.print(" -> ");

		String modifiedDayOfWeek = modifiedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		String modifiedStatus = attendanceManager.getState(modifiedDayOfWeek, modifiedDate.getHour(), modifiedDate.getMinute());
		System.out.print(String.format(SELECT_FUNCTION_MESSAGE,
			modifiedDate.getMonthValue(), modifiedDate.getDayOfMonth(), modifiedDayOfWeek
			, modifiedDate.getHour(), modifiedDate.getMinute(), modifiedStatus));
		System.out.println(" 수정 완료!");
	}

}
