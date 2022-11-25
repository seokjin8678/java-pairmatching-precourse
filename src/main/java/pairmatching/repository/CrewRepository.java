package pairmatching.repository;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;

public class CrewRepository {
    private final Set<Crew> crews = new HashSet<>();

    public void saveAll(Collection<Crew> crews) {
        this.crews.addAll(crews);
    }

    public Set<Crew> findAll() {
        return Set.copyOf(crews);
    }

    public Set<Crew> findByCourse(Course course) {
        if (course == Course.BACKEND) {
            return findBackEndCrews();
        }
        if (course == Course.FRONTEND) {
            return findFrontEndCrews();
        }
        return Collections.emptySet();
    }

    private Set<Crew> findBackEndCrews() {
        return crews.stream()
                .filter(Crew::isBackEnd)
                .collect(toSet());
    }

    private Set<Crew> findFrontEndCrews() {
        return crews.stream()
                .filter(Crew::isFrontEnd)
                .collect(toSet());
    }
}
