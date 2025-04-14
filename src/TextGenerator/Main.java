

package TextGenerator;

import java.util.Scanner;

/**
 * Main Text generating class
 * @author Jason Chang, Enoch Nielsen
 * @version Apr 23, 2024
 */
public class Main {
    public static void main(String[] args) {
        // Create an instance of the MarkovGenerator
        MarkovGenerator generator = new MarkovGenerator();

        // Specify the text file for building the dictionary
        String filename = "src/dictionary.txt"; // Ensure this path is correct
        generator.createDictionary(filename);

        // Create a Scanner object to get user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for a seed word
        System.out.print("Enter a seed word: ");
        String seed = scanner.nextLine().toLowerCase();  // Lowercase to match tokenization

        // Optionally, display the top possibilities for the given seed word
        System.out.print("Top possibilities after '" + seed + "': ");
        generator.kNumberOfPossibilities(seed, 3);
        System.out.println();

        // Ask the user for the number of words to generate
        System.out.print("Enter the number of words to generate: ");
        int numWords = scanner.nextInt();
        scanner.nextLine(); // clear the newline character

        // Ask the user which mode to use ("all" for weighted random, "one" for highest frequency)
        System.out.print("Enter mode ('all' or 'one'): ");
        String mode = scanner.nextLine();

        // Generate and print the text based on user input
        System.out.println("Generated Text:");
        generator.generateText(seed, numWords, mode);

        // Close the scanner
        scanner.close();
    }
}
