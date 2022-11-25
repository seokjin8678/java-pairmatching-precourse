package pairmatching.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SelectTest {

    @ParameterizedTest
    @DisplayName("1, 2, 3, Q가 입력으로 들어오면 정상적으로 Select를 반환해야 한다.")
    @ValueSource(strings = {"1", "2", "3", "Q"})
    void correctInputMustSuccessfulReturnSelect(String input) {
        // expect
        assertThat(Select.of(input))
                .isInstanceOf(Select.class);
    }

    @ParameterizedTest
    @DisplayName("1, 2, 3, Q 외의 입력이 들어오면 예외가 발생해야 한다.")
    @ValueSource(strings = {"0", "4", "5", "A"})
    void incorrectInputMustThrowException(String input) {
        // expect
        assertThatThrownBy(() -> Select.of(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}