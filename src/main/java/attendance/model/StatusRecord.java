package attendance.model;

public class StatusRecord {
	private String name;
	private int attendance;
	private int latency;
	private int absence;

	public StatusRecord(String name, int attendance, int latency, int absence) {
		this.name = name;
		this.attendance = attendance;
		this.latency = latency;
		this.absence = absence;
	}

	public String getName() {
		return name;
	}

	public int getAttendance() {
		return attendance;
	}

	public int getLatency() {
		return latency;
	}

	public int getAbsence() {
		return absence;
	}
}
