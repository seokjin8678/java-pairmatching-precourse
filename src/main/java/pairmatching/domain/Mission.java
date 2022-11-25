package pairmatching.domain;

import java.util.Arrays;

public enum Mission {
    CAR_RACE("자동차경주", Level.LEVEL1),
    LOTTO("로또", Level.LEVEL1),
    NUMBER_BASEBALL("숫자야구게임", Level.LEVEL1),
    SHOPPING_BASKET("장바구니", Level.LEVEL2),
    PAYMENT("결제", Level.LEVEL2),
    SUBWAY_MAP("지하철노선도", Level.LEVEL2),
    PERFORMANCE_IMPROVE("성능개선", Level.LEVEL4),
    DEPLOY("배포", Level.LEVEL4);

    private final String name;
    private final Level level;

    Mission(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public static Mission of(String input, Level level) {
        return Arrays.stream(values())
                .filter(mission -> input.equals(mission.name))
                .filter(mission -> mission.level == level)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 미션이 없습니다."));
    }

    public boolean isSameLevel(Level level) {
        return this.level == level;
    }

    public Level getLevel() {
        return level;
    }
}
