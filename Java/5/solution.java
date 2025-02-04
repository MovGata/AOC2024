import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
 
public class solution {

    

    public static void main(String[] args) throws Exception {
        Map<Integer, List<Integer>> less_thans = new HashMap<Integer, List<Integer>>(); // Map to values that the key precedes.        
        Integer count = 0; // part 1
        Integer count2 = 0; // part 2

        try {
            Scanner scanner = new Scanner(new File("input.txt"));
 
            while (scanner.hasNextLine()) {
                // Get Rules
                String line = scanner.nextLine();
                if (line.isEmpty()) break; // End of rules
                List<Integer> vals = Arrays.stream(line.split("\\|")).mapToInt(Integer::valueOf).boxed().toList(); // Split rule into before | after
                less_thans.putIfAbsent(Integer.valueOf(vals.get(0)), new ArrayList<Integer>()); // Create entry if non-existent
                less_thans.get(Integer.valueOf(vals.get(0))).add(Integer.valueOf(vals.get(1))); // Add rule to map
            }
            while (scanner.hasNextLine()) {
                // Get Updates
                List<Integer> vals = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList()); // Get update as ints
                List<Integer> empty = new ArrayList<Integer>(); // In case no rules for a number
                List<Integer> sorted = vals.stream().sorted((o1, o2)->less_thans.getOrDefault(o1, empty).contains(o2) ? -1 : 0).collect(Collectors.toList()); // Sort by rules in map
                
                if (vals.equals(sorted)) count += vals.get((vals.size())/2); // Equal, part 1
                else count2 += sorted.get((sorted.size())/2); // Not equal, part 2
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(count); // part 1 results
        System.out.println(count2); // part 2 results
    }
}