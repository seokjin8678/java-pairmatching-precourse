package pairmatching.domain;

import java.util.Arrays;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String name;

    Course(String name) {
        this.name = name;
    }

    public static Course of(String input) {
        return Arrays.stream(values())
                .filter(course -> input.equals(course.name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 코스가 없습니다."));
    }
}
