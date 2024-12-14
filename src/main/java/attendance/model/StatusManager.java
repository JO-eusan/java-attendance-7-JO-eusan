package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatusManager {
	private List<StatusRecord> records;

	public StatusManager(AttendanceManager attendanceManager, LocalDateTime currentTime) {
		this.records = createRecords(attendanceManager, currentTime);
	}

	public StatusRecord findByName(String name) {
		for(StatusRecord record : records) {
			if(record.getName().equals(name)) {
				return record;
			}
		}
		return null;
	}

	private List<StatusRecord> createRecords(AttendanceManager attendanceManager, LocalDateTime currentTime) {
		List<StatusRecord> result = new ArrayList<>();
		for(String name : attendanceManager.getKeys()) {
			result.add(checkRecord(attendanceManager, name, currentTime));
		}
		return result;
	}

	private StatusRecord checkRecord(AttendanceManager attendanceManager, String name, LocalDateTime currentTime) {
		List<LocalDateTime> allRecords = attendanceManager.getAllRecord(name);
		LocalDate dateCounter = LocalDate.of(currentTime.getYear(), currentTime.getMonth(), 1);
		int attendance = 0;
		int latency = 0;
		int absence = 0;

		while(dateCounter.getDayOfMonth() < currentTime.getDayOfMonth() - 1) {
			String dayOfWeek = dateCounter.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
			if(!dayOfWeek.equals("토요일") && !dayOfWeek.equals("일요일")) {
				String currentStatus = checkRecord(attendanceManager, allRecords, dateCounter);
				if(currentStatus.equals("출석")) {
					attendance++;
				}
				if(currentStatus.equals("지각")) {
					latency++;
				}
				if(currentStatus.equals("결석")) {
					absence++;
				}
			}
			dateCounter = LocalDate.of(currentTime.getYear(), currentTime.getMonth(), dateCounter.getDayOfMonth() + 1);
		}
		return new StatusRecord(name, attendance, latency, absence);
	}

	private String checkRecord(AttendanceManager attendanceManager, List<LocalDateTime> allRecords, LocalDate current) {
		for(LocalDateTime record : allRecords) {
			if(record.toLocalDate().isEqual(current)) {
				String dayOfWeek = record.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
				return attendanceManager.getState(dayOfWeek, record.getHour(), record.getMinute());
			}
		}
		return "결석";
	}
}
