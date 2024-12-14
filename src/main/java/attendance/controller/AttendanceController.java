package attendance.controller;

import java.time.LocalDateTime;

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
			executeFunction(Integer.parseInt(function));
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

	private void executeFunction(int functionNumber) {
		if(functionNumber == 1) {
			attend();
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

	private void attend() {

	}

	private void retouchAttendance() {

	}

	private void checkRecord() {

	}

	private void checkRiskCrews() {

	}

}
