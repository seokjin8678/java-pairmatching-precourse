package pairmatching.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Pair {
    private final Course course;
    private final Mission mission;
    private final List<Crew> crews;

    public Pair(Course course, Mission mission, List<Crew> crews) {
        this.course = course;
        this.mission = mission;
        this.crews = crews;
    }

    public String getCrewsName() {
        return crews.stream()
                .map(Crew::getName)
                .collect(Collectors.joining(" : "));
    }

    public boolean isCourseMatch(Course course) {
        return this.course == course;
    }

    public boolean isMissionMatch(Mission mission) {
        return this.mission == mission;
    }
}
