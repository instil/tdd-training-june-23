package calculator;

import java.util.Arrays;

public class StringCalculator {
    public int add(String numberString) {
        if (numberString.isEmpty()) {
            return 0;
        }
        var numbers = numberString.split("[,\\n]");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
    }
}
