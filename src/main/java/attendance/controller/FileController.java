package attendance.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import attendance.model.AttendanceManager;
import attendance.view.OutputView;

public class FileController {
	OutputView outputView;
	AttendanceManager attendanceManager;

	public FileController(AttendanceManager attendanceManager) {
		this.outputView = new OutputView();
		this.attendanceManager = attendanceManager;
	}

	public void initializeData(String attendancePath) {
		try {
			createRecords(loadFile(attendancePath));
		} catch (FileNotFoundException e) {
			outputView.printFileErrorMessage(e);
		}
	}

	private Scanner loadFile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);

		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}

		return scanner;
	}

	private void createRecords(Scanner attendaceScanner) {
		while (attendaceScanner.hasNextLine()) {
			String[] attr = attendaceScanner.nextLine().split(",");
			String name = attr[0];

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(attr[1], formatter);

			attendanceManager.addRecord(name, dateTime);
		}
	}
}
