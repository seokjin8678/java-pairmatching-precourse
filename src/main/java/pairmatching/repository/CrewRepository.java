package pairmatching.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import pairmatching.domain.Crew;

public class CrewRepository {
    private final Set<Crew> crews = new HashSet<>();

    public void saveAll(Collection<Crew> crews) {
        this.crews.addAll(crews);
    }

    public Set<Crew> findAll() {
        return Set.copyOf(crews);
    }
}
