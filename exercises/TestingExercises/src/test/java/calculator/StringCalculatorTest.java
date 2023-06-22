package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
