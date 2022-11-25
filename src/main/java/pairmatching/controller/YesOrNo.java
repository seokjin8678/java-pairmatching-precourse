package pairmatching.controller;

import java.util.Arrays;

public enum YesOrNo {
    YES("네"),
    NO("아니오");

    private final String flag;

    YesOrNo(String flag) {
        this.flag = flag;
    }

    public static YesOrNo of(String flag) {
        return Arrays.stream(values())
                .filter(yesOrNo -> flag.equals(yesOrNo.flag))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 입력입니다."));
    }
}
