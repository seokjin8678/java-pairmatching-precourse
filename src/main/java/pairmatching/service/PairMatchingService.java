package pairmatching.service;

import pairmatching.repository.PairMatchingRepository;

public class PairMatchingService {

    private final PairMatchingRepository pairMatchingRepository;

    public PairMatchingService(PairMatchingRepository pairMatchingRepository) {
        this.pairMatchingRepository = pairMatchingRepository;
    }
}
