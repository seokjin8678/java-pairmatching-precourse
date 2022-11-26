package pairmatching.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

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
                .collect(joining(" : "));
    }

    public boolean isCourseMatch(Course course) {
        return this.course == course;
    }

    public boolean isMissionMatch(Mission mission) {
        return this.mission == mission;
    }

    public boolean isCourseAndMissionMatch(Course course, Mission mission) {
        return isCourseMatch(course) && isMissionMatch(mission);
    }

    public boolean isLevelMatch(Level level) {
        return mission.isSameLevel(level);
    }

    public boolean isDuplicate(Pair otherPair) {
        Crew firstCrew = otherPair.crews.get(0);
        if (!crews.contains(firstCrew)) {
            return false;
        }
        List<Crew> otherCrews = otherPair.crews.subList(1, otherPair.crews.size());
        for (Crew otherCrew : otherCrews) {
            if (crews.contains(otherCrew)) {
                return true;
            }
        }
        return false;
    }
}
