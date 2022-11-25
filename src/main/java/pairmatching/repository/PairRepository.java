package pairmatching.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;

public class PairRepository {
    private final List<Pair> pairs = new ArrayList<>();

    public void saveAll(Collection<Pair> pairs) {
        this.pairs.addAll(pairs);
    }

    public void deleteAll() {
        pairs.clear();
    }

    public List<Pair> findByCourseAndMission(Course course, Mission mission) {
        return pairs.stream()
                .filter(pair -> pair.isCourseAndMissionMatch(course, mission))
                .collect(Collectors.toList());
    }

    public boolean existsByCourseAndMission(Course course, Mission mission) {
        return pairs.stream()
                .anyMatch(pair -> pair.isCourseAndMissionMatch(course, mission));
    }

    public void deleteByCourseAndMission(Course course, Mission mission) {
        pairs.removeIf(pair -> pair.isCourseAndMissionMatch(course, mission));
    }
}
