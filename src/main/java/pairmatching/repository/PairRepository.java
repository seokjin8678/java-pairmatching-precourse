package pairmatching.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pairmatching.domain.Pair;

public class PairRepository {
    private final List<Pair> pairs = new ArrayList<>();

    public void saveAll(Collection<Pair> pairs) {
        this.pairs.addAll(pairs);
    }

    public void deleteAll() {
        pairs.clear();
    }
}
