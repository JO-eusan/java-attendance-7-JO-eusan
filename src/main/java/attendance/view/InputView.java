package attendance.view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import attendance.model.AttendanceManager;
import camp.nextstep.edu.missionutils.Console;

public class InputView {
	private static final String SELECT_FUNCTION_MESSAGE = "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.";
	private static final String FUNCTION_1 = "1. 출석 확인";
	private static final String FUNCTION_2 = "2. 출석 수정";
	private static final String FUNCTION_3 = "3. 크루별 출석 기록 확인";
	private static final String FUNCTION_4 = "4. 제적 위험자 확인";
	private static final String FUNCTION_Q = "Q. 종료";
	private static final String NICKNAME_MESSAGE = "닉네임을 입력해 주세요.";
	private static final String TIME_MESSAGE = "등교 시간을 입력해 주세요.";
	private static final String RETRY_NICKNAME_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
	private static final String RETRY_DAY_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
	private static final String RETRY_TIME_MESSAGE = "언제로 변경하겠습니까?";

	private static final String WEEKEND_ERROR_MESSAGE = "%02d월 %02d일 %s은 등교일이 아닙니다.";
	private static final String NICKNAME_ERROR_MESSAGE = "등록되지 않은 닉네임입니다.";
	private static final String TIME_ERROR_MESSAGE = "잘못된 형식을 입력하였습니다.";
	private static final String DAY_ERROR_MESSAGE = "잘못된 날짜(일)입니다.";

	public String readFunction(LocalDateTime currentTime) {
		System.out.println(String.format(SELECT_FUNCTION_MESSAGE,
			currentTime.getMonthValue(), currentTime.getDayOfMonth(), currentTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
		System.out.println(FUNCTION_1);
		System.out.println(FUNCTION_2);
		System.out.println(FUNCTION_3);
		System.out.println(FUNCTION_4);
		System.out.println(FUNCTION_Q);

		String input = Console.readLine();
		validateFunction(input);
		validateWeekend(currentTime, input);
		return input;
	}

	public String readNickName(AttendanceManager attendanceManager) {
		System.out.println(NICKNAME_MESSAGE);

		String input = Console.readLine();
		validateNickName(attendanceManager, input);
		return input;
	}

	public String readTime() {
		System.out.println(TIME_MESSAGE);

		String input = Console.readLine();
		validateTimeFormat(input);
		return input;
	}

	public String readRetryName(AttendanceManager attendanceManager) {
		System.out.println(RETRY_NICKNAME_MESSAGE);

		String input = Console.readLine();
		validateNickName(attendanceManager, input);
		return input;
	}

	public String readRetryDay() {
		System.out.println(RETRY_DAY_MESSAGE);

		String input = Console.readLine();
		validateDay(input);
		return input;
	}

	public String readRetryTime() {
		System.out.println(RETRY_TIME_MESSAGE);

		String input = Console.readLine();
		validateTimeFormat(input);
		return input;
	}

	private void validateFunction(String input) {
		if(!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("Q")) {
			throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
		}
	}

	private void validateWeekend(LocalDateTime currentTime, String input) {
		String dayOfWeek = currentTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		if(input.equals("1") && (dayOfWeek.equals("요일") || dayOfWeek.equals("일요일"))) {
			throw new IllegalArgumentException(String.format(WEEKEND_ERROR_MESSAGE, currentTime.getMonthValue(), currentTime.getDayOfMonth(),dayOfWeek));
		}
	}

	private void validateNickName(AttendanceManager attendanceManager, String name) {
		if(!attendanceManager.isContain(name)) {
			throw new IllegalArgumentException(NICKNAME_ERROR_MESSAGE);
		}

	}

	private void validateTimeFormat(String input) {
		if(!input.matches("(0[1-9]|1[0-9]|2[0-4]):(0[1-9]|[1-5][0-9])")) {
			throw new IllegalArgumentException(TIME_ERROR_MESSAGE);
		}
	}

	private void validateDay(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(DAY_ERROR_MESSAGE);
		}
		int day = Integer.parseInt(input);
		if(day < 1 || day > 31) {
			throw new IllegalArgumentException(DAY_ERROR_MESSAGE);
		}
	}
}
