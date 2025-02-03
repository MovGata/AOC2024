import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
 
public class solution {
    
    public static Integer step(List<List<Character>> grid, Integer y, Integer x, Integer y_step, Integer x_step, String search) {
        if (x < 0 || y < 0) return 0; // Lower bound check
        if (y >= grid.size() || x >= grid.get(y).size()) return 0; // Upper bound check
        if (grid.get(y).get(x) != search.charAt(0)) return 0; // Character does not match
        return search.length() <= 1 ? 1 : step(grid, y + y_step, x + x_step, y_step, x_step, search.substring(1)); // If last character return match, else check next char.
    }
    
    // Searches each direction for "XMAS" and return number of detections.
    public static Integer xmas(List<List<Character>> grid, Integer y, Integer x) {
        return step(grid, y, x, -1, 0, "XMAS") +
        step(grid, y, x, -1, -1, "XMAS") +
        step(grid, y, x, 0, -1, "XMAS") +
        step(grid, y, x, 1, -1, "XMAS") +
        step(grid, y, x, 1, 0, "XMAS") +
        step(grid, y, x, 1, 1, "XMAS") +
        step(grid, y, x, 0, 1, "XMAS") +
        step(grid, y, x, -1, 1, "XMAS");
    }
    
    // Searches each diagonal for 'MAS' and bit-shifts to divide by 2.
    public static Integer x_mas(List<List<Character>> grid, Integer y, Integer x) {
        return step(grid, y+1, x+1, -1, -1, "MAS") +
        step(grid, y-1, x+1, 1, -1, "MAS") +
        step(grid, y-1, x-1, 1, 1, "MAS") +
        step(grid, y+1, x-1, -1, 1, "MAS") >> 1;
    }

    public static void main(String[] args) throws Exception {
        List<List<Character>> char_grid = new ArrayList<List<Character>>(); 
       
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
 
            while (scanner.hasNextLine()) {
                // Add line as list of chars to grid.
                char_grid.add(scanner.nextLine().chars().mapToObj(c->(char)(c)).collect(Collectors.toList()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
 
        // Filter char_grid for 'X' and count XMAS's.
        Integer result = IntStream.range(0, char_grid.size()).map(i -> {
            return IntStream.range(0, char_grid.get(i).size()).map(j -> char_grid.get(i).get(j) == 'X' ? xmas(char_grid, i, j) : 0).sum();
        }).sum();
 
        // Filter char_grid for 'A' and count X-MAS's.
        Integer result2 = IntStream.range(0, char_grid.size()).map(i -> {
            return IntStream.range(0, char_grid.get(i).size()).map(j -> char_grid.get(i).get(j) == 'A' ? x_mas(char_grid, i, j) : 0).sum();
        }).sum();

        System.out.println(result);
        System.out.println(result2);
    }
}