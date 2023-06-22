package words.clean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.empty;

public class Program {
    public static void main(String[] args) throws IOException {
        Stream<String> lines = readLinesFromPath("./data/uniqueWords.txt")
                .orElseThrow();
        Map<String, Integer> wordCounts = countWords(splitToWords(lines));

        printWordsWithCounts(wordCounts);
    }

    private static Comparator<Map.Entry<String, Integer>> byFrequency() {
        return Comparator.comparingInt(Map.Entry::getValue);
    }


    private static Optional<Stream<String>> readLinesFromPath(String fileName){
        Path path = new File(fileName).toPath();
        try {
            return Optional.of(Files.lines(path));
        } catch (IOException e) {
            return empty();
        }
    }

    private static Stream<String> splitToWords(Stream<String> lines) {
        return lines.flatMap(s -> Arrays.stream(s.split("\\s+")));
    }

    private static Map<String, Integer> countWords(Stream<String> words) {
        var groupedByWords = words
                .map(Program::sanitize)
                .filter(Program::notJustNumbers)
                .collect(Collectors.groupingBy(word -> word));
        return groupedByWords.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry-> entry.getValue().size()));
    }

    private static boolean notJustNumbers(String maybeWord) {
        return !maybeWord.matches("^[0-9]+$");
    }

    private static String sanitize(String word) {
        return word.toLowerCase().replaceAll("\\W", "");
    }

    private static void printWordsWithCounts(Map<String, Integer> wordCounts) {
        wordCounts.entrySet().stream()
                .sorted(byFrequency())
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}
