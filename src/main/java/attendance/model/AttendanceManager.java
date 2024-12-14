package attendance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AttendanceManager {
	private HashMap<String, List<LocalDateTime>> records;
	private HashMap<String, LocalDateTime> pastRecords;

	public AttendanceManager() {
		this.records = new HashMap<>();
		this.pastRecords = new HashMap<>();
	}

	public Set<String> getKeys() {
		return records.keySet();
	}

	public void addRecord(String name, LocalDateTime date) {
		List<LocalDateTime> tmp = new ArrayList<>();
		if (records.containsKey(name)) {
			tmp = records.get(name);
		}
		tmp.add(date);
		records.put(name, tmp);
	}

	public void modifyRecord(String name, LocalDateTime modifiedDate) {
		List<LocalDateTime> recordsOfCrew = records.get(name);
		for (LocalDateTime localDateTime : recordsOfCrew) {
			if (localDateTime.getDayOfMonth() == modifiedDate.getDayOfMonth()) {
				recordsOfCrew.remove(localDateTime);
				pastRecords.put(name, localDateTime);
				break;
			}
		}
		recordsOfCrew.add(modifiedDate);
		records.put(name, recordsOfCrew);
	}

	public boolean isContain(String name) {
		return records.containsKey(name);
	}

	public String getState(String dayOfWeek, int hour, int minute) {
		if (dayOfWeek.equals("월요일")) {
			if ((hour == 13 && minute <= 5) || hour < 13) {
				return "출석";
			}
			if (hour == 13 && (5 < minute && minute < 30)) {
				return "지각";
			}
			return "결석";
		}
		if ((hour == 10 && minute <= 5) || hour < 10) {
			return "출석";
		}
		if (hour == 10 && (5 < minute && minute < 30)) {
			return "지각";
		}
		return "결석";
	}

	public LocalDateTime getPastRecord(String name) {
		return pastRecords.get(name);
	}

	public List<LocalDateTime> getAllRecord(String name) {
		return records.get(name);
	}
}
