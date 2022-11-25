package pairmatching.service;

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
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.repository.CrewRepository;
import pairmatching.repository.PairRepository;

public class PairMatchingService {

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
                    .collect(Collectors.toSet());
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
                .collect(Collectors.toSet());
    }

    public List<Pair> matchCrew(Course course, Mission mission) {
        Set<Crew> crews = crewRepository.findByCourse(course);
        List<Pair> pairs = new ArrayList<>();
        List<String> crewNames = crews.stream()
                .map(Crew::getName)
                .collect(Collectors.toList());
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
        pairRepository.saveAll(pairs);
        return pairs;
    }

    public void clearAllPairs() {
        pairRepository.deleteAll();
    }
}
