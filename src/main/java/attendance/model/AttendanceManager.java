package attendance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendanceManager {
	private HashMap<String, List<LocalDateTime>> records;

	public AttendanceManager() {
		this.records = new HashMap<>();
	}

	public void addRecord(String name, LocalDateTime date) {
		List<LocalDateTime> tmp = new ArrayList<>();
		if(records.containsKey(name)) {
			tmp = records.get(name);
		}
		tmp.add(date);
		records.put(name, tmp);
	}

	public boolean isContain(String name) {
		return records.containsKey(name);
	}

	public String getState(String dayOfWeek, int hour, int minute) {
		if(dayOfWeek.equals("월요일")) {
			if((hour == 13 && minute <= 5) || hour < 13) {
				return "출석";
			}
			if(hour == 13 &&( 5 < minute && minute < 30)) {
				return "지각";
			}
			return "결석";
		}
		if((hour == 10 && minute <= 5) || hour < 10) {
			return "출석";
		}
		if(hour == 10 &&( 5 < minute && minute < 30)) {
			return "지각";
		}
		return "결석";
	}
}
