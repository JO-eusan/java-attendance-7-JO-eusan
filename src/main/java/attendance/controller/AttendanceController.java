package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import attendance.model.AttendanceManager;
import attendance.model.Penalty;
import attendance.model.StatusManager;
import attendance.model.StatusRecord;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;

public class AttendanceController {
	private InputView inputView;
	private OutputView outputView;
	private AttendanceManager attendanceManager;
	private StatusManager statusManager;

	public AttendanceController(AttendanceManager attendanceManager) {
		this.inputView = new InputView();
		this.outputView = new OutputView();
		this.attendanceManager = attendanceManager;
	}

	public void startFunction() {
		while(true) {
			LocalDateTime currentTime = DateTimes.now();
			String function = selectFunction(currentTime);
			if(function.equals("Q")) {
				break;
			}
			executeFunction(currentTime, Integer.parseInt(function));
		}
	}

	private String selectFunction(LocalDateTime currentTime) {
		String function = inputView.readFunction(currentTime);
		return function;
	}

	private void executeFunction(LocalDateTime currentTime, int functionNumber) {
		if(functionNumber == 1) {
			attend(currentTime);
		}
		if(functionNumber == 2) {
			retouchAttendance(currentTime);
		}
		if(functionNumber == 3) {
			this.statusManager = new StatusManager(attendanceManager, currentTime);
			checkRecord(currentTime);
		}
		if(functionNumber == 4) {
			this.statusManager = new StatusManager(attendanceManager, currentTime);
			checkRiskCrews();
		}
	}

	private void attend(LocalDateTime currentTime) {
		String nickname = enterName();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = currentTime.format(formatter);
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date + " " + enterTime(), formatter);

		attendanceManager.addRecord(nickname, dateTime);
		outputView.printAttendanceRecord(attendanceManager, dateTime);
	}

	private void retouchAttendance(LocalDateTime currentTime) {
		String name = enterRetryName();
		int day = Integer.parseInt(enterRetryDay());
		String[] times = enterRetryTime().split(":");
		int hour = Integer.parseInt(times[0]);
		int minute = Integer.parseInt(times[1]);

		LocalDateTime date = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), day, hour, minute);
		attendanceManager.modifyRecord(name, date);
		outputView.printModifyRecord(name, date, attendanceManager);
	}

	private void checkRecord(LocalDateTime currentTime) {
		String nickname = enterName();
		List<LocalDateTime> allRecords = attendanceManager.getAllRecord(nickname);
		LocalDate dateCounter = LocalDate.of(currentTime.getYear(), currentTime.getMonth(), 1);

		while(dateCounter.getDayOfMonth() < currentTime.getDayOfMonth()) {
			String dayOfWeek = dateCounter.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
			if(!dayOfWeek.equals("토요일") && !dayOfWeek.equals("일요일")) {
				checkRecord(allRecords, dateCounter);
			}
			dateCounter = LocalDate.of(currentTime.getYear(), currentTime.getMonth(), dateCounter.getDayOfMonth() + 1);
		}
		outputView.printResult(statusManager.findByName(nickname));
	}

	private void checkRecord(List<LocalDateTime> allRecords, LocalDate current) {
		for(LocalDateTime record : allRecords) {
			if(record.toLocalDate().isEqual(current)) {
				outputView.printAttendanceRecord(attendanceManager, record);
				return;
			}
		}
		outputView.printAttendanceRecordOfDate(current);
	}

	private void checkRiskCrews() {
		outputView.printPenaltyCrew(statusManager);
	}

	private String enterRetryName() {
		return inputView.readRetryName(attendanceManager);
	}

	private String enterName() {
		return inputView.readNickName(attendanceManager);
	}

	private String enterTime() {
		return inputView.readTime();
	}

	private String enterRetryDay() {
		return inputView.readRetryDay();
	}

	private String enterRetryTime() {
		return inputView.readRetryTime();
	}
}
