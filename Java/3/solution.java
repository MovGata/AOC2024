import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
 
public class solution {
    public static void main(String[] args) throws Exception {
        // Matches multiplier command
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        // Matches Do/Don't command, but also captures start/end of input.
        Pattern do_pattern = Pattern.compile("(\\A|do\\(\\)|don't\\(\\))(.*?)(?=do\\(\\)|don't\\(\\)|\\Z)");
        
        Integer do_result = 0; // For do
        Integer dont_result = 0; // To add to do, for total

        boolean start_do = true; // For knowing to "do" or "don't" at start of input.

        try {
            // Input
            Scanner scanner = new Scanner(new File("input.txt"));
            while (scanner.hasNextLine()) {
                // Match Do's and Don't's
                Matcher do_matcher = do_pattern.matcher(scanner.nextLine());
                while (do_matcher.find()) {
                    // For summing multiplication results.
                    Integer result = 0;
                    Matcher matcher = pattern.matcher(do_matcher.group(2)); // Find mul's
                    while (matcher.find()) {
                        result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2)); // Calculate sum for a single do/don't
                    }

                    if (do_matcher.group(1).compareTo("do()") == 0) { // Do, add to do_result
                        do_result += result;
                        start_do = true; // Currently do-ing
                    } else if (do_matcher.group(1).compareTo("don't()") == 0) { // Don't, add to dont_result
                        dont_result += result;
                        start_do = false; // Currently don't-ing
                    } else { // Start of line, follow start_do result.
                        if (start_do) {
                            do_result += result;
                        } else {
                            dont_result += result;
                        }
                    }
                }
            }
            // EOF
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Results
        System.out.println(do_result + dont_result); // Part A
        System.out.println(do_result); // Part B
    }
}