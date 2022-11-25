package pairmatching.controller;

import java.util.List;
import pairmatching.domain.Pair;
import pairmatching.dto.CourseLevelMissionDto;
import pairmatching.dto.PairResultDto;
import pairmatching.service.PairMatchingService;
import pairmatching.view.PairMatchingView;

public class PairMatchingController {

    private final PairMatchingService pairMatchingService;
    private final PairMatchingView pairMatchingView;

    public PairMatchingController(PairMatchingService pairMatchingService,
                                  PairMatchingView pairMatchingView) {
        this.pairMatchingService = pairMatchingService;
        this.pairMatchingView = pairMatchingView;
    }

    public void run() {
        pairMatchingService.saveAllCrewsFromFile();
        while (true) {
            Select select = pairMatchingView.receiveSelect();
            if (select == Select.MATCHING) {
                CourseLevelMissionDto courseLevelMissionDto = pairMatchingView.receiveCourseLevelMission();
                // 매칭 기능 수행
                List<Pair> pairs = pairMatchingService.matchCrew(courseLevelMissionDto.getCourse(),
                        courseLevelMissionDto.getMission());
                pairMatchingView.printPairResult(PairResultDto.of(pairs));
            }
            if (select == Select.INQUIRY) {
                pairMatchingView.receiveCourseLevelMission();
                // 조회 기능 수행
            }
            if (select == Select.RESET) {
                // 초기화 기능 수행
                pairMatchingService.clearAllPairs();
                pairMatchingView.printClearMessage();
            }
            if (select == Select.QUIT) {
                return;
            }
        }
    }
}
