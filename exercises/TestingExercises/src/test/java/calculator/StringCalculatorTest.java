package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        // arrange
        calculator = new StringCalculator();
    }

    @Test
    void emptyStringReturnsZero() {
        // act
        int result = calculator.add("");
        // assert
        assertEquals(0, result);
    }

    @Test
    void aSingleNumberAsString() {
        assertEquals(1, calculator.add("1"));
    }

    @Test
    void twoNumbersCommaSeparated() {
        assertEquals(3, calculator.add("1,2"));
    }

    @ParameterizedTest
    @MethodSource("anyNumberOfNumberProvider")
    void anyNumberOfNumbers(String numberString, int expectedResult) {
        assertEquals(expectedResult, calculator.add(numberString));
    }

    public static Stream<Arguments> anyNumberOfNumberProvider() {
        return Stream.of(
                Arguments.of("1,2,3", 6),
                Arguments.of("1,2,3,4", 10),
                Arguments.of("1,2,3,4,5", 15)
        );
    }

    @ParameterizedTest
    @MethodSource("commasOrNewlinesAllowedProvider")
    void canUseNewLinesOrCommas(String numberString, int expectedResult) {
        assertEquals(expectedResult, calculator.add(numberString));
    }
    public static Stream<Arguments> commasOrNewlinesAllowedProvider() {
        return Stream.of(
                Arguments.of("1\n2,3", 6),
                Arguments.of("1,2\n3,4", 10),
                Arguments.of("1\n2,3,4\n5", 15)
        );
    }


}
