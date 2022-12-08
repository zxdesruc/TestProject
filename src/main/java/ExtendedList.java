
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExtendedList {

    private final List<String> list = new ArrayList<>();
    private boolean isLimited;
    private int maxSize;
    private int firstStringIndex;

    public ExtendedList() {
    }

    public ExtendedList(File file) throws IOException {
        list.addAll(Collections.singleton(FileUtils.readFileToString(file)));
    }

    public ExtendedList(int maxSize) {
        this.isLimited = true;
        this.maxSize = maxSize;
        this.firstStringIndex = 0;
    }

    public void add(String string) {
        if (isLimited && list.size() == maxSize) {
            list.set(firstStringIndex, string);
            firstStringIndex++;
        } else {
            list.add(string);
        }
    }

    public String get(int index) {
        return list.get(index);
    }

    public void remove(String string) {
        list.remove(string);
    }

    public int getStringMatches(String string) {
        return (int) list.stream().filter(s -> s.equals(string)).count();
    }

    public int getCharMatches(char target) {
        int matches = 0;
        for (String s : list) {
            for (char c : s.toCharArray()) {
                matches += target == c
                        ? 1
                        : 0;
            }
        }
        return matches;
    }

    public void exportToXml(File filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<ExtendedList>").append('\n');
        for (String string : list) {
            sb
                    .append('\t')
                    .append("<String>")
                    .append(string)
                    .append("</String>")
                    .append('\n');
        }
        sb.append("</ExtendedList>");
        FileUtils.writeStringToFile(filename, sb.toString());
    }

    public void reverseAll() {
        for (int i = 0; i < list.size(); i++) {
            String string = list.get(i);
            string = new StringBuilder(string).reverse().toString();
            list.set(i, string);
        }
    }

    public String[] findBySubstring(String substring) {
        return list.stream().filter(s -> s.contains(substring)).toArray(String[]::new);
    }

    public void printAll() {
        list.forEach(System.out::println);
    }

    public int compareInnerObjects(int firstIndex, int secondIndex) {
        String firstString = list.get(firstIndex);
        String secondString = list.get(secondIndex);
        return firstString.compareTo(secondString);
    }

    public void printStringLengthsInOrder() {
        List<String> listCopy = new ArrayList<>(list);
        listCopy.sort(Comparator.comparingInt(String::length));
        listCopy.forEach(s -> System.out.println(s + " - " + s.length()));
    }
}


