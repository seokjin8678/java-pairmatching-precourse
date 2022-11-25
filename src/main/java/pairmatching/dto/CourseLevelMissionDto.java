package pairmatching.dto;

import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;

public class CourseLevelMissionDto {
    private final Course course;
    private final Mission mission;

    private CourseLevelMissionDto(Course course, Mission mission) {
        this.course = course;
        this.mission = mission;
    }

    public static CourseLevelMissionDto of(String input) {
        String[] inputs = input.split(", ");
        validate(inputs);
        Course course = Course.of(inputs[0]);
        Level level = Level.of(inputs[1]);
        Mission mission = Mission.of(inputs[2], level);
        return new CourseLevelMissionDto(course, mission);
    }

    private static void validate(String[] inputs) {
        if (inputs.length != 3) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
        }
    }

    public Course getCourse() {
        return course;
    }

    public Mission getMission() {
        return mission;
    }
}
