package pairmatching.controller;

import java.util.Collections;
import java.util.List;
import pairmatching.domain.Pair;
import pairmatching.dto.CourseLevelMissionDto;
import pairmatching.dto.PairResultDto;
import pairmatching.service.PairMatchingService;
import pairmatching.view.PairMatchingView;

public class PairMatchingController {

    private final PairMatchingService pairMatchingService;
    private final PairMatchingView pairMatchingView;

    public PairMatchingController(PairMatchingService pairMatchingService, PairMatchingView pairMatchingView) {
        this.pairMatchingService = pairMatchingService;
        this.pairMatchingView = pairMatchingView;
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        pairMatchingService.saveAllCrewsFromFile();
    }

    private void loop() {
        while (true) {
            ExitFlag flag = routine();
            if (flag == ExitFlag.EXIT) {
                return;
            }
        }
    }

    private ExitFlag routine() {
        Select select = pairMatchingView.receiveSelect();
        return selectMenu(select);
    }

    private ExitFlag selectMenu(Select select) {
        if (select == Select.MATCHING) {
            pairMatchingView.printCourseList();
            matching();
        }
        if (select == Select.INQUIRY) {
            pairMatchingView.printCourseList();
            inquiry();
        }
        if (select == Select.RESET) {
            reset();
        }
        if (select == Select.QUIT) {
            return ExitFlag.EXIT;
        }
        return ExitFlag.CONTINUE;
    }

    private void matching() {
        CourseLevelMissionDto dto = pairMatchingView.receiveCourseLevelMission();
        if (pairMatchingService.isAlreadyMatch(dto.getCourse(), dto.getMission())) {
            if (pairMatchingView.receiveYesOrNo() == YesOrNo.NO) {
                matching();
                return;
            }
            pairMatchingService.deletePairByCourseAndMission(dto.getCourse(), dto.getMission());
        }
        List<Pair> pairs = pairMatchingService.matchCrew(dto.getCourse(), dto.getMission());
        if (pairs == Collections.EMPTY_LIST) {
            pairMatchingView.printPairMatchFail();
            return;
        }
        pairMatchingView.printPairResult(PairResultDto.of(pairs));
    }

    private void inquiry() {
        CourseLevelMissionDto dto = pairMatchingView.receiveCourseLevelMission();
        // 조회 기능 수행
        List<Pair> pairs = pairMatchingService.findPairByCourseAndMission(dto.getCourse(), dto.getMission());
        pairMatchingView.printPairResult(PairResultDto.of(pairs));
    }

    private void reset() {
        // 초기화 기능 수행
        pairMatchingService.clearAllPairs();
        pairMatchingView.printClearMessage();
    }
}
