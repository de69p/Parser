package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class StatisticReader {
    private final Map<String,Integer> wordCounter = new HashMap<>();
    private final Pattern PATTERN_WORD = Pattern.compile("[^a-zA-Z']");

    public StatisticReader(File book) {
        this.textToWordCounter(book);
    }
    private void textToWordCounter(File book){
        try (BufferedReader inputFile = new BufferedReader(new FileReader(book))){
            inputFile.lines()
                    .map(line -> PATTERN_WORD.split(line.trim().replaceAll("\\s+", " ")))
                    .flatMap(Arrays::stream)
                    .map(String::toLowerCase)
                    .forEach(word -> wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1));
        } catch (IOException e) {
            System.out.println("Failed reading the file with text. Try again.");
        }
    }

    public int countUniqueWords(){
        return wordCounter.keySet().size();
    }

    public int countWords(){
        return wordCounter.values()
                .stream()
                .reduce(Integer::sum)
                .orElse(0);
    }

    public List<String> getTenWords(){
        return wordCounter.entrySet()
                .stream()
                .filter(s -> s.getKey().length() >2)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(s -> s.getKey() + ":" + s.getValue() )
                .limit(10)
                .toList();
    }
}