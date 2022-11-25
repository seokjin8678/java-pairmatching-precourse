package pairmatching.view;

import pairmatching.controller.Select;
import pairmatching.dto.CourseLevelMissionDto;
import pairmatching.dto.PairResultDto;

public class PairMatchingView {
    private final InputView inputView;
    private final OutputView outputView;

    public PairMatchingView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public Select receiveSelect() {
        while (true) {
            try {
                outputView.printSelectList();
                return inputView.inputSelect();
            } catch (IllegalArgumentException e) {
                outputView.printException(e);
            }
        }
    }

    public CourseLevelMissionDto receiveCourseLevelMission() {
        while (true) {
            try {
                outputView.printCourseList();
                return inputView.inputCourseLevelMission();
            } catch (IllegalArgumentException e) {
                outputView.printException(e);
            }
        }
    }

    public void printPairResult(PairResultDto pairResultDto) {
        for (String pairName : pairResultDto.getPairNames()) {
            outputView.printPairName(pairName);
        }
    }

    public void printClearMessage() {
        outputView.printClearMessage();
    }
}
