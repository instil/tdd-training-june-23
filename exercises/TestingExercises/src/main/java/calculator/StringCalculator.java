package calculator;

import java.util.Arrays;

public class StringCalculator {
    public int add(String numberString) {
        if (numberString.isEmpty()) {
            return 0;
        }

        if (numberString.startsWith("//")) {
            var delimiterAndNumber = numberString.split("\\n");
            String delimiter = delimiterAndNumber[0].substring(2);
            String newNumberString = delimiterAndNumber[1];
            var numbers = newNumberString.split(delimiter);
            return Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
        }
        var numbers = numberString.split("[,\\n]");
        return Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
    }
}
