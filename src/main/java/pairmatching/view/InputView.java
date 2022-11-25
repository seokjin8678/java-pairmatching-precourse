package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import pairmatching.controller.Select;

public class InputView {
    public Select inputSelect() {
        String input = Console.readLine();
        return Select.of(input);
    }
}
