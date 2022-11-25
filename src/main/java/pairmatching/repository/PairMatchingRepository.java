package pairmatching.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import pairmatching.domain.Crew;

public class PairMatchingRepository {
    private final Set<Crew> crews = new HashSet<>();

    public void saveAllCrews(Collection<Crew> crews) {
        this.crews.addAll(crews);
    }

    public Set<Crew> findAllCrew() {
        return Set.copyOf(crews);
    }
}
