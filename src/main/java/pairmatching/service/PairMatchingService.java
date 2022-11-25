package pairmatching.service;

import static java.util.stream.Collectors.*;

import camp.nextstep.edu.missionutils.Randoms;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.repository.CrewRepository;
import pairmatching.repository.PairRepository;

public class PairMatchingService {
    public static final int RETRY_COUNT = 3;

    private final CrewRepository crewRepository;
    private final PairRepository pairRepository;

    public PairMatchingService(CrewRepository crewRepository, PairRepository pairRepository) {
        this.crewRepository = crewRepository;
        this.pairRepository = pairRepository;
    }

    public void saveAllCrewsFromFile() {
        try {
            Set<Crew> crews = Files.list(Path.of("./src/main/resources"))
                    .flatMap(path -> retrieveCrewFile(path).stream())
                    .collect(toSet());
            crewRepository.saveAll(crews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<Crew> retrieveCrewFile(Path path) {
        String fileName = path.getFileName().toString();
        try {
            if (fileName.equals("backend-crew.md")) {
                return getCrews(Course.BACKEND, path);
            }
            if (fileName.equals("frontend-crew.md")) {
                return getCrews(Course.FRONTEND, path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    private Set<Crew> getCrews(Course course, Path path) throws IOException {
        return Files.lines(path)
                .map(line -> new Crew(course, line))
                .collect(toSet());
    }

    public List<Pair> matchCrew(Course course, Mission mission) {
        if (crewRepository.countByCourse(course) < 2) {
            return Collections.emptyList();
        }
        List<Pair> pairs = makePairs(course, mission);
        pairRepository.saveAll(pairs);
        return pairs;
    }


    private List<Pair> makePairs(Course course, Mission mission) {
        List<Pair> sameLevelPairs = pairRepository.findByCourseAndLevel(course, mission.getLevel());
        for (int i = 0; i < RETRY_COUNT; i++) {
            List<Pair> pairs = pairMatch(course, mission);
            if (!checkDuplicatePair(sameLevelPairs, pairs)) {
                return pairs;
            }
        }
        return Collections.emptyList();
    }

    private List<Pair> pairMatch(Course course, Mission mission) {
        List<String> crewNames = getCrewNames(course);
        List<Pair> pairs = new ArrayList<>();
        Queue<String> shuffledCrewsName = new LinkedList<>(Randoms.shuffle(crewNames));
        while (shuffledCrewsName.size() > 0) {
            List<Crew> crewPair = new ArrayList<>();
            crewPair.add(new Crew(course, shuffledCrewsName.poll()));
            crewPair.add(new Crew(course, shuffledCrewsName.poll()));
            if (shuffledCrewsName.size() == 1) {
                crewPair.add(new Crew(course, shuffledCrewsName.poll()));
            }
            pairs.add(new Pair(course, mission, crewPair));
        }
        return pairs;
    }

    private List<String> getCrewNames(Course course) {
        return crewRepository.findByCourse(course)
                .stream()
                .map(Crew::getName)
                .collect(toList());
    }

    private boolean checkDuplicatePair(List<Pair> sameLevelPairs, List<Pair> pairs) {
        for (Pair pair : pairs) {
            boolean result = sameLevelPairs.stream()
                    .anyMatch(sameLevelPair -> sameLevelPair.isDuplicate(pair));
            if (result) {
                return true;
            }
        }
        return false;
    }

    public void clearAllPairs() {
        pairRepository.deleteAll();
    }

    public List<Pair> findPairByCourseAndMission(Course course, Mission mission) {
        return pairRepository.findByCourseAndMission(course, mission);
    }

    public boolean isAlreadyMatch(Course course, Mission mission) {
        return pairRepository.existsByCourseAndMission(course, mission);
    }

    public void deletePairByCourseAndMission(Course course, Mission mission) {
        pairRepository.deleteByCourseAndMission(course, mission);
    }
}
