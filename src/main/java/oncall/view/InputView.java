package oncall.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

import java.util.Arrays;
import java.util.List;
import oncall.controller.dto.RawDateInfo;
import oncall.domain.Workers;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class InputView {
    public RawDateInfo askMonthAndStartDayOfWeek() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
        List<String> rawMonthAndStartDayOfWeek = convertArrayToList(readLine().split(",", -1));
        validateTwoArguments(rawMonthAndStartDayOfWeek);
        int rawMonth = convertStringToInt(rawMonthAndStartDayOfWeek.get(0));
        String rawDayOfWeek = rawMonthAndStartDayOfWeek.get(1);
        return new RawDateInfo(rawMonth, rawDayOfWeek);
    }

    private List<String> convertArrayToList(final String[] splits) {
        return Arrays.stream(splits).toList();
    }

    private void validateTwoArguments(final List<String> rawMonthAndStartDayOfWeek) {
        if (rawMonthAndStartDayOfWeek.size() != 2) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    private int convertStringToInt(final String rawInt) {
        try {
            int rawMonth = Integer.parseInt(rawInt);
            if (rawMonth < 1 || 12 < rawMonth) {
                throw CustomException.from(ErrorMessage.INVALID_INPUT);
            }
            return rawMonth;
        } catch (NumberFormatException e) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    public Workers askWorkersWeekday() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        List<String> workerNames = convertArrayToList(readLine().split(",", -1));
        return new Workers(workerNames);
    }

    public Workers askWorkersWeekend() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
        List<String> workerNames = convertArrayToList(readLine().split(",", -1));
        return new Workers(workerNames);
    }
}
