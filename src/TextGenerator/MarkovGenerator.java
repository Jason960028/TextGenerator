package TextGenerator;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents Markov Chain text generator
 * @author Jason Chang, Enoch Nielsen
 * @version Apr 23, 2024
 */
public class MarkovGenerator {
    public Map<String, Map<String, Integer>> dictionary = new HashMap<>();

    /**
     * Creates a dictionary from given file
     * Runtime: Read file O(N) > Tokenization O(M) > Building dictionary O(W) = O(N)
     * @param filename to make the dictionary
     */
    public void createDictionary(String filename) {
        try {
            //Make whole file as a string.
            File file = new File(filename);
            String data = new String(Files.readAllBytes(file.toPath())); //O(N) 
            
            String[] words = tokenization(data); //O(M)

            for (int i = 0; i < words.length - 1; i++) {//build dictionary O(W)
                String currentWord = words[i];
                String nextWord = words[i + 1];
                //Search the current Word in the dictionary or create a new LinkedHashMap if it does not exist.(ComputeIfAbsent)
                //Search for the next Word in this LinkedHashMap, or if not, set the frequency to 1.(merge(nextWord,1)
                //If the nextWord already exists, increase its frequency by 1. (Integer::sum)
                dictionary.computeIfAbsent(currentWord, k -> new HashMap<>())
                        .merge(nextWord, 1, Integer::sum);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Extracting words and numbers from a given string
     * Runtime: O(N)
     * @param text given string
     * @return List of words and numbers
     */
    private static String[] tokenization(String text) {
        String[] rawTokens = text.split("\\s+");
        ArrayList<String> words = new ArrayList<>();

        for (String token : rawTokens) {
            Matcher matcher = Pattern.compile("^[a-zA-Z0-9]+").matcher(token);
            if (matcher.find()) {
                if (matcher.start() == 0) {
                    words.add(matcher.group().toLowerCase());
                }
            }
        }

        return words.toArray(new String[0]);
    }

    /**
     * This method returns the top k the highest frequency words from a given map.
     * Runtime: O(NLogK) where K is much smaller than N.
     * @param transitions Map
     * @param k number of words to return
     * @return top frequency words
     */
    private PriorityQueue<Map.Entry<String, Integer>> topWordsHeap(Map<String, Integer> transitions, int k) {
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
                k, Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()).reversed()
        );

        for (Map.Entry<String, Integer> entry : transitions.entrySet()) { //add and remove at the same time only keep top k at the queue
            maxHeap.add(entry);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        return maxHeap;
    }

    /**
     * This method find what word can come after seed word. Find the K highest probability words.
     * Runtime: Finding transition O(1) < Extracting from heap O(KLogK) < Printing K number of elements O(K) < Build Heap O(NLogK) = O(NLogK)
     * @param seed word
     * @param k highest probability
     */
    public void kNumberOfPossibilities(String seed, int k) {
        
        
        if (!dictionary.containsKey(seed)) {
          return;  
        }
        
        
        Map<String, Integer> transitions = dictionary.get(seed); //O(1)
        PriorityQueue<Map.Entry<String, Integer>> topWordsHeap = topWordsHeap(transitions, k); //O(NLogK)
        List<Map.Entry<String, Integer>> topWords = new ArrayList<>();

        // Extract all elements from the heap
        while (!topWordsHeap.isEmpty()) {
            topWords.add(topWordsHeap.poll()); //O(KLogK)
        }
        Collections.reverse(topWords);
        topWords.forEach(entry -> System.out.print(entry.getKey() + " ")); //O(K)
        
    }

    /**
     * This method generates text
     * Runtime: all = O(KN) one = O(KN). Most of the time O(K).
     * @param seed word starting from
     * @param k number of words to print
     * @param mode to select
     * @return generated text
     */
    public void generateText(String seed, int k, String mode) {
        
        List<String> output = new ArrayList<>();
        String currentWord = seed;
        output.add(currentWord);

        for (int i = 1; i < k; i++) { //O(K)
            Map<String, Integer> transitions = dictionary.get(currentWord);
            if (transitions == null || transitions.isEmpty()) {
                currentWord = seed;
            } else {
                Map.Entry<String, Integer> next = selectNextWord(transitions, mode, k); //based on mode either O(N) for all or O(NLogK) for one
                currentWord = next.getKey();
            }
            output.add(currentWord);
        }
        System.out.println(String.join(" ", output));
    }

    /**
     * This method help selcting next word based on its mode
     * Runtime: all = O(N) one = O(N).
     * @param transitions map containing words and its frequency
     * @param mode to select
     * @return the next word
     */
    private Map.Entry<String, Integer> selectNextWord(Map<String, Integer> transitions, String mode, int k) {
        if ("all".equals(mode)) {
            int total = transitions.values().stream().mapToInt(Integer::intValue).sum(); // Total number of frequencies of all words O(N)
            int limit = new Random().nextInt(total+1); //Generates a random limit of 0 to the total frequency 
            int sum = 0;
            for (Map.Entry<String, Integer> entry : transitions.entrySet()) { //O(1~N)
                sum += entry.getValue();
                if (sum >= limit) {
                    return entry;
                }
            }
        } else if ("one".equals(mode)) {
            PriorityQueue<Map.Entry<String, Integer>> heap = topWordsHeap(transitions, 1); //O(N)
            return heap.isEmpty() ? null : heap.poll();
        }
        return null;
    }



    //===================================================
    //Methods to check values


    public void writeDictionariesToFile(String filename) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println("Dictionary (Word Transitions):");
            for (Map.Entry<String, Map<String, Integer>> entry : dictionary.entrySet()) {
                out.println(entry.getKey() + " -> " + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
