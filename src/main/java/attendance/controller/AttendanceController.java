package attendance.controller;

import attendance.model.AttendanceManager;
import attendance.view.InputView;
import attendance.view.OutputView;

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
			String function = selectFunction();
			if(function.equals("Q")) {
				break;
			}
			//executeFunction(Integer.parseInt(function));
		}
	}

	private String selectFunction() {
		try {
			String function = inputView.readFunction();
			return function;
		} catch(IllegalArgumentException e) {
			outputView.printErrorMessage(e);
			selectFunction();
		}
		return "";
	}

	// private void executeFunction(int functionNumber) {
	// 	if(functionNumber == 1) {
	// 		pairMatching();
	// 	}
	// 	if(functionNumber == 2) {
	// 		pairCheck();
	// 	}
	// 	if(functionNumber == 3) {
	// 		pairReset();
	// 	}
	// }

}
