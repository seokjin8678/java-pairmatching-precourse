package pairmatching.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.repository.PairMatchingRepository;

public class PairMatchingService {

    private final PairMatchingRepository pairMatchingRepository;

    public PairMatchingService(PairMatchingRepository pairMatchingRepository) {
        this.pairMatchingRepository = pairMatchingRepository;
    }

    public void saveAllCrewsFromFile() {
        try {
            Set<Crew> crews = Files.list(Path.of("./src/main/resources"))
                    .flatMap(path -> retrieveCrewFile(path).stream())
                    .collect(Collectors.toSet());
            pairMatchingRepository.saveAllCrews(crews);
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
}
