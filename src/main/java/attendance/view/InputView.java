package attendance.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
	private static final String SELECT_FUNCTION_MESSAGE = "기능을 선택하세요.";
	private static final String FUNCTION_1 = "1. 출석 확인";
	private static final String FUNCTION_2 = "2. 출석 수정";
	private static final String FUNCTION_3 = "3. 크루별 출석 기록 확인";
	private static final String FUNCTION_4 = "4. 제적 위험자 확인";
	private static final String FUNCTION_Q = "Q. 종료";

	public String readFunction() {
		System.out.println(SELECT_FUNCTION_MESSAGE);
		System.out.println(FUNCTION_1);
		System.out.println(FUNCTION_2);
		System.out.println(FUNCTION_3);
		System.out.println(FUNCTION_4);
		System.out.println(FUNCTION_Q);

		String input = Console.readLine();
		validateFunction(input);
		return input;
	}

	private void validateFunction(String input) {
		if(!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("Q")) {
			throw new IllegalArgumentException("잘못된 형식을 입력하였습니다.");
		}
	}
}
