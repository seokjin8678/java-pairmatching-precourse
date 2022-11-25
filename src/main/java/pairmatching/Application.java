package pairmatching;

import pairmatching.controller.PairMatchingController;
import pairmatching.repository.CrewRepository;
import pairmatching.repository.PairRepository;
import pairmatching.service.PairMatchingService;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;
import pairmatching.view.PairMatchingView;

public class Application {
    public static void main(String[] args) {
        PairMatchingService pairMatchingService = new PairMatchingService(new CrewRepository(), new PairRepository());
        PairMatchingView pairMatchingView = new PairMatchingView(new InputView(), new OutputView());
        PairMatchingController pairMatchingController = new PairMatchingController(pairMatchingService,
                pairMatchingView);
        pairMatchingController.run();
    }
}
