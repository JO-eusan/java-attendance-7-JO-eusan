package attendance.model;

public enum Penalty {
	WARNING("경고 대상자", 2),
	DISCUSSION("면담 대상자", 3),
	EXPULSION("제적 대상자", 5);

	private String name;
	private int absence;

	private Penalty(String name, int absence) {
		this.name = name;
		this.absence = absence;
	}

	public static String checkPenalty(StatusRecord statusRecord) {
		int latency = statusRecord.getLatency();
		int absence = statusRecord.getAbsence();
		absence += latency/3;

		String result = "";
		for(Penalty penalty : values()) {
			if(penalty.absence <= absence) {
				result = penalty.name;
			}
		}
		return result;
	}

}
