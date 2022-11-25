package pairmatching.view;

import pairmatching.controller.Select;

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
}
