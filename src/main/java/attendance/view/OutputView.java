package attendance.view;

import java.io.FileNotFoundException;

public class OutputView {

	private static final String ERROR_PREFIX = "[ERROR] ";

	public void printFileErrorMessage(FileNotFoundException e) {
		System.out.println(ERROR_PREFIX + e.getMessage());
	}

	public void printErrorMessage(IllegalArgumentException e) {
		System.out.println(ERROR_PREFIX + e.getMessage());
	}
}
