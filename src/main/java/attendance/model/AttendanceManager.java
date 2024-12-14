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
}
