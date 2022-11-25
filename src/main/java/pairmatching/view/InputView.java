package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import pairmatching.controller.Select;
import pairmatching.dto.CourseLevelMissionDto;

public class InputView {
    public Select inputSelect() {
        String input = Console.readLine();
        return Select.of(input);
    }

    public CourseLevelMissionDto inputCourseLevelMission() {
        String input = Console.readLine();
        return CourseLevelMissionDto.of(input);
    }
}
