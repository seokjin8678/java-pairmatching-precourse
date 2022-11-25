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

    public Course getCourse() {
        return course;
    }

    public Mission getMission() {
        return mission;
    }
}
