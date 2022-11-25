package pairmatching.controller;

import java.util.Arrays;

public enum Select {
    MATCHING("1"),
    INQUIRY("2"),
    RESET("3"),
    QUIT("Q");

    private final String flag;

    Select(String flag) {
        this.flag = flag;
    }

    public static Select of(String flag) {
        return Arrays.stream(values())
                .filter(selection -> flag.equals(selection.flag))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 입력입니다."));
    }
}
