package pairmatching.controller;

import java.util.List;
import pairmatching.domain.Course;
import pairmatching.domain.Mission;
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

    public void init() {
        pairMatchingService.saveAllCrewsFromFile();
    }

    public void loop() {
        while (true) {
            ExitFlag flag = routine();
            if (flag == ExitFlag.EXIT) {
                return;
            }
        }
    }

    private ExitFlag routine() {
        Select select = pairMatchingView.receiveSelect();
        if (select == Select.MATCHING) {
            matching();
        }
        if (select == Select.INQUIRY) {
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
        CourseLevelMissionDto courseLevelMissionDto = pairMatchingView.receiveCourseLevelMission();
        Course course = courseLevelMissionDto.getCourse();
        Mission mission = courseLevelMissionDto.getMission();
        // 매칭 기능 수행
        if (pairMatchingService.isAlreadyMatch(course, mission)) {
            YesOrNo yesOrNo = pairMatchingView.receiveYesOrNo();
            if (yesOrNo == YesOrNo.YES) {
                pairMatchingService.deletePairByCourseAndMission(course, mission);
                List<Pair> pairs = pairMatchingService.matchCrew(course, mission);
                pairMatchingView.printPairResult(PairResultDto.of(pairs));
            }
        } else {
            List<Pair> pairs = pairMatchingService.matchCrew(course, mission);
            pairMatchingView.printPairResult(PairResultDto.of(pairs));
        }
    }

    private void inquiry() {
        CourseLevelMissionDto courseLevelMissionDto = pairMatchingView.receiveCourseLevelMission();
        // 조회 기능 수행
        Course course = courseLevelMissionDto.getCourse();
        Mission mission = courseLevelMissionDto.getMission();
        List<Pair> pairs = pairMatchingService.findPairByCourseAndMission(course, mission);
        pairMatchingView.printPairResult(PairResultDto.of(pairs));
    }

    private void reset() {
        // 초기화 기능 수행
        pairMatchingService.clearAllPairs();
        pairMatchingView.printClearMessage();
    }
}
