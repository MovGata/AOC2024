import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
 
public class solution {
    public static void main(String[] args) throws Exception {
        Integer valids = 0;
        Integer damps = 0;
       
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
 
            while (scanner.hasNextLine()) {
                // Input
                List<Integer> ints = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
                
                // Difference between neighbours
                List<Integer> order = IntStream.range(0, ints.size()-1).mapToObj(i -> ints.get(i+1) - ints.get(i)).collect(Collectors.toList());
                
                // Get max and min difference
                Integer max = order.stream().mapToInt(Integer::valueOf).max().getAsInt();
                Integer min = order.stream().mapToInt(Integer::valueOf).min().getAsInt();
 
                // Pass condition
                if (min >= 1 && max <= 3 || min >= -3 && max <= -1) {
                    valids += 1;
                    continue;
                }
               
                if (order.stream().filter(i -> i < 0).count() > 1 && order.stream().filter(i -> i > 0).count() > 1) {
                    continue; // Unsalvageable
                }
 
                // Find first problematic number
                OptionalInt dampen;
                if (order.stream().filter(i -> i < 0).count() == 1) {
                    dampen = IntStream.range(0, order.size()).filter(i -> order.get(i) < 0).findFirst();
                } else if (order.stream().filter(i -> i > 0).count() == 1) {
                    dampen = IntStream.range(0, order.size()).filter(i -> order.get(i) > 0).findFirst();
                } else {
                    dampen = IntStream.range(0, order.size()).filter(i -> Math.abs(order.get(i)) > 3 || order.get(i) == 0).findFirst();
                }
 
                if (!dampen.isPresent()) {
                    continue;
                }
                
                // Two alternate ways to remove a differential
                Integer damp = dampen.getAsInt();
                Integer optionA = damp < order.size()-1 ? order.get(damp) + order.get(damp + 1) : 0;
                Integer optionB = damp > 0 ? order.get(damp) + order.get(damp - 1) : 0;
 
                // Get max and min difference
                max = IntStream.range(0, order.size()).filter(i -> i != damp).mapToObj(i -> i == damp + 1 ? optionA : order.get(i)).mapToInt(Integer::valueOf).max().getAsInt();
                min = IntStream.range(0, order.size()).filter(i -> i != damp).mapToObj(i -> i == damp + 1 ? optionA : order.get(i)).mapToInt(Integer::valueOf).min().getAsInt();
                
                // Pass Condition
                if (min >= 1 && max <= 3 || min >= -3 && max <= -1) {
                    damps += 1;
                    continue;
                }
                
                // Get max and min difference
                max = IntStream.range(0, order.size()).filter(i -> i != damp).mapToObj(i -> i == damp - 1 ? optionB : order.get(i)).mapToInt(Integer::valueOf).max().getAsInt();
                min = IntStream.range(0, order.size()).filter(i -> i != damp).mapToObj(i -> i == damp - 1 ? optionB : order.get(i)).mapToInt(Integer::valueOf).min().getAsInt();
 
                // Pass Condition
                if (min >= 1 && max <= 3 || min >= -3 && max <= -1) {
                    damps += 1;
                    continue;
                }
            }
 
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
 
        System.out.println(valids); // Part A
        System.out.println(valids + damps); // Part B
    }
}