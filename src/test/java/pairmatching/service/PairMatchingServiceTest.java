package pairmatching.service;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.repository.CrewRepository;
import pairmatching.repository.PairRepository;

class PairMatchingServiceTest {

    CrewRepository crewRepository;
    PairRepository pairRepository;
    PairMatchingService pairMatchingService;

    @BeforeEach
    void setUp() {
        crewRepository = new CrewRepository();
        pairRepository = new PairRepository();
        pairMatchingService = new PairMatchingService(crewRepository, pairRepository);
    }

    @Test
    @DisplayName("크루원이 없을 때 매칭을 수행하면 빈 리스트가 반환 되어야 한다.")
    void matchCrewByEmptyCrewMustReturnEmptyList() {
        // when
        List<Pair> pairs = pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);
        // expect
        assertThat(pairs)
                .isEqualTo(EMPTY_LIST);
    }

    @Test
    @DisplayName("크루원이 4명일때 매칭을 수행하면 2개의 페어가 반환 되어야 한다.")
    void matchCrewBy4CrewsMustReturn2Pairs() {
         // given
        List<Crew> crews = List.of(
            new Crew(Course.FRONTEND, "A"),
            new Crew(Course.FRONTEND, "B"),
            new Crew(Course.FRONTEND, "C"),
            new Crew(Course.FRONTEND, "D")
        );
        crewRepository.saveAll(crews);

        // when
        List<Pair> pairs = pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);

        // then
        Assertions.assertThat(pairs.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("크루원이 5명일때 매칭을 수행하면 2개의 페어가 반환 되어야 한다.")
    void matchCrewBy5CrewsMustReturn2Pairs() {
         // given
        List<Crew> crews = List.of(
            new Crew(Course.FRONTEND, "A"),
            new Crew(Course.FRONTEND, "B"),
            new Crew(Course.FRONTEND, "C"),
            new Crew(Course.FRONTEND, "D"),
            new Crew(Course.FRONTEND, "E")
        );
        crewRepository.saveAll(crews);

        // when
        List<Pair> pairs = pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);

        // then
        Assertions.assertThat(pairs.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("크루원이 3명일때 매칭을 수행하면 1개의 페어가 반환 되어야 한다.")
    void matchCrewBy3CrewsMustReturn1Pairs() {
         // given
        List<Crew> crews = List.of(
            new Crew(Course.FRONTEND, "A"),
            new Crew(Course.FRONTEND, "B"),
            new Crew(Course.FRONTEND, "C")
        );
        crewRepository.saveAll(crews);

        // when
        List<Pair> pairs = pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);

        // then
        Assertions.assertThat(pairs.size())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("크루원이 1명일때 매칭을 수행하면 빈 리스트가 반환 되어야 한다.")
    void matchCrewBy1CrewMustReturnEmptyList() {
         // given
        List<Crew> crews = List.of(
            new Crew(Course.FRONTEND, "A")
        );
        crewRepository.saveAll(crews);

        // when
        List<Pair> pairs = pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);

        // then
        Assertions.assertThat(pairs)
                .isEqualTo(EMPTY_LIST);
    }

    @Test
    @DisplayName("페어를 매칭한 이력이 있으면 참이 반환 되어야 한다.")
    void wasPairMatchedTrueMustReturnTrue() {
        // given
        List<Crew> crews = List.of(
                new Crew(Course.FRONTEND, "A"),
                new Crew(Course.FRONTEND, "B"),
                new Crew(Course.FRONTEND, "C")
        );
        crewRepository.saveAll(crews);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);
        // expect
        assertThat(pairMatchingService.isAlreadyMatch(Course.FRONTEND, Mission.CAR_RACE))
                .isTrue();
    }

    @Test
    @DisplayName("페어를 매칭한 이력이 없으면 거짓이 반환 되어야 한다.")
    void wasPairMatchedFalseMustReturnFalse() {
        // given
        List<Crew> crews = List.of(
                new Crew(Course.FRONTEND, "A"),
                new Crew(Course.FRONTEND, "B"),
                new Crew(Course.FRONTEND, "C")
        );
        crewRepository.saveAll(crews);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);
        // expect
        assertThat(pairMatchingService.isAlreadyMatch(Course.BACKEND, Mission.CAR_RACE))
                .isFalse();
    }

    @Test
    @DisplayName("페어 목록 중 특정 코스와 미션만 지울수 있어야 한다.")
    void deletePairsWithSpecificCourseAndMission() {
        // given
        List<Crew> crews = List.of(
                new Crew(Course.FRONTEND, "A"),
                new Crew(Course.FRONTEND, "B"),
                new Crew(Course.FRONTEND, "C")
        );
        crewRepository.saveAll(crews);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.SHOPPING_BASKET);
        // when
        List<Pair> otherMissionPairs = pairMatchingService.findPairByCourseAndMission(Course.FRONTEND,
                Mission.SHOPPING_BASKET);
        pairMatchingService.deletePairByCourseAndMission(Course.FRONTEND, Mission.CAR_RACE);
        List<Pair> allPairs = pairRepository.findAll();
        // then
        Assertions.assertThat(allPairs.containsAll(otherMissionPairs))
                .isTrue();
    }

    @Test
    @DisplayName("페어 목록을 모두 지울 수 있어야 한다.")
    void deleteAllPairsMustSuccess() {
        // given
        List<Crew> crews = List.of(
                new Crew(Course.FRONTEND, "A"),
                new Crew(Course.FRONTEND, "B"),
                new Crew(Course.FRONTEND, "C")
        );
        crewRepository.saveAll(crews);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.CAR_RACE);
        pairMatchingService.matchCrew(Course.FRONTEND, Mission.SHOPPING_BASKET);
        // when
        pairMatchingService.clearAllPairs();
        // then
        Assertions.assertThat(pairRepository.findAll())
                .isEqualTo(EMPTY_LIST);
    }
}