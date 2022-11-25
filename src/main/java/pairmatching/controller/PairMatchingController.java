package pairmatching.controller;

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
                pairMatchingView.receiveCourseLevelMission();
                // 매칭 기능 수행
            }
            if (select == Select.INQUIRY) {
                pairMatchingView.receiveCourseLevelMission();
                // 조회 기능 수행
            }
            if (select == Select.RESET) {
                // 초기화 기능 수행
            }
            if (select == Select.QUIT) {
                return;
            }
        }
    }
}
