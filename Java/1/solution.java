import java.io.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class solution {

    public static void main(String[] args) {

        ArrayList<Integer> one = new ArrayList<Integer>();
        ArrayList<Integer> two = new ArrayList<Integer>();

        Map<Integer, Integer> occurrence_map = new HashMap<Integer, Integer>();

        File file = new File("input.txt"); // Load file
        try {
            Scanner inputFile = new Scanner(file); // Create file parser
            while (inputFile.hasNext()) {
                String numPair = inputFile.nextLine(); // Get next line
                String[] split = numPair.split("\\s+"); // Split at whitespace
                one.add(Integer.valueOf(split[0])); // First split value
                two.add(Integer.valueOf(split[1])); // Second split value

                occurrence_map.putIfAbsent(two.getLast(), 0); // Initial value for new numbers
                occurrence_map.put(two.getLast(), occurrence_map.get(two.getLast()) + 1); // Increment detection
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        Collections.sort(one); // Self-explanatory
        Collections.sort(two);

        // Generate stream of indices, map to piecewise difference of values, put into a
        // collection, map to sum of collection.
        int sum = IntStream.range(0, one.size())
                .mapToObj(i -> Math.max(one.get(i), two.get(i)) - Math.min(one.get(i), two.get(i)))
                .collect(Collectors.toList()).stream().mapToInt(Integer::intValue).sum();

        // Generate stream of indices, map to value multiplied by occurrence in second
        // list, put into a collection, map to sum of collection.
        int occ_sum = IntStream.range(0, one.size())
                .mapToObj(i -> one.get(i) * occurrence_map.getOrDefault(one.get(i), 0)).collect(Collectors.toList())
                .stream().mapToInt(Integer::intValue).sum();

        // results :)
        System.out.println(sum);
        System.out.println(occ_sum);
    }
}