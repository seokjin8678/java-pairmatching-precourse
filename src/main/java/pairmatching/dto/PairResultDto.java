package pairmatching.dto;

import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.Pair;

public class PairResultDto {
    private final List<String> pairNames;

    private PairResultDto(List<String> pairNames) {
        this.pairNames = pairNames;
    }

    public static PairResultDto of(List<Pair> pairs) {
        List<String> collect = pairs.stream()
                .map(Pair::getCrewsName)
                .collect(Collectors.toList());
        return new PairResultDto(collect);
    }

    public List<String> getPairNames() {
        return pairNames;
    }
}
