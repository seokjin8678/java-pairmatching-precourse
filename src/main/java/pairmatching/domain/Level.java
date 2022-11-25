package pairmatching.domain;

import java.util.Arrays;

public enum Level {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private final String name;

    Level(String name) {
        this.name = name;
    }

    public static Level of(String input) {
        return Arrays.stream(values())
                .filter(level -> input.equals(level.name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 레벨이 없습니다."));
    }
}
