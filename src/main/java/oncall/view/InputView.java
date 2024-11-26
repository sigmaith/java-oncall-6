package oncall.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

import java.util.Arrays;
import java.util.List;
import oncall.controller.dto.MonthAndStartDayOfWeek;
import oncall.domain.Workers;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class InputView {
    private static final String MONTH_AND_START_DAY_OF_THE_WEEK_INPUT_GUIDE = "비상 근무를 배정할 월과 시작 요일을 입력하세요> ";
    private static final String WEEKDAY_EMPLOYEE_NICKNAMES_INPUT_GUIDE = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";
    private static final String HOLIDAY_EMPLOYEE_NICKNAMES_INPUT_GUIDE = "휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";

    public MonthAndStartDayOfWeek askMonthAndStartDayOfWeek() {
        System.out.print(MONTH_AND_START_DAY_OF_THE_WEEK_INPUT_GUIDE);
        List<String> splits = parseStringToList(readLine());
        if (splits.size() != 2) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
        return new MonthAndStartDayOfWeek(validateMonth(splits.get(0)), splits.get(1));
    }

    public Workers askWeekdayWorkers() {
        System.out.print(WEEKDAY_EMPLOYEE_NICKNAMES_INPUT_GUIDE);
        List<String> workerNames = parseStringToList(readLine());
        return Workers.from(workerNames);
    }

    public Workers askWeekendWorkers() {
        System.out.print(HOLIDAY_EMPLOYEE_NICKNAMES_INPUT_GUIDE);
        List<String> workerNames = parseStringToList(readLine());
        return Workers.from(workerNames);
    }

    private int validateMonth(String rawMonth) {
        try {
            int month = Integer.parseInt(rawMonth);
            if (month < 1 || month > 12) {
                throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
            }
            return month;
        } catch (NumberFormatException e) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }

    private List<String> parseStringToList(final String message) {
        return Arrays.stream(message.split(",", -1)).toList();
    }
}
