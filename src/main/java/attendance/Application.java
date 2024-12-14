package attendance;

import attendance.controller.AttendanceController;
import attendance.controller.FileController;
import attendance.model.AttendanceManager;

public class Application {
	public static void main(String[] args) {
		AttendanceManager attendanceManager = new AttendanceManager();

		FileController fileController = new FileController(attendanceManager);
		AttendanceController attendanceController = new AttendanceController(attendanceManager);

		fileController.initializeData("src/main/resources/attendances.csv");
		attendanceController.startFunction();
	}
}
