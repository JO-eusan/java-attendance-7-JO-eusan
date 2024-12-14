package attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import attendance.model.AttendanceManager;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;

public class AttendanceController {
	private InputView inputView;
	private OutputView outputView;
	private AttendanceManager attendanceManager;

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
		try {
			String function = inputView.readFunction(currentTime);
			return function;
		} catch(IllegalArgumentException e) {
			outputView.printErrorMessage(e);
			selectFunction(currentTime);
		}
		return "";
	}

	private void executeFunction(LocalDateTime currentTime, int functionNumber) {
		if(functionNumber == 1) {
			attend(currentTime);
		}
		if(functionNumber == 2) {
			retouchAttendance();
		}
		if(functionNumber == 3) {
			checkRecord();
		}
		if(functionNumber == 4) {
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

	private String enterName() {
		try {
			return inputView.readNickName(attendanceManager);
		} catch (IllegalArgumentException e) {
			outputView.printErrorMessage(e);
			enterName();
		}
		return "";
	}

	private String enterTime() {
		try {
			return inputView.readTime();
		} catch (IllegalArgumentException e) {
			outputView.printErrorMessage(e);
			enterTime();
		}
		return "";
	}

	private void retouchAttendance() {

	}

	private void checkRecord() {

	}

	private void checkRiskCrews() {

	}

}
