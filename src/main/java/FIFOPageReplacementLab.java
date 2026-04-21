import java.util.*;

public class FIFOPageReplacementLab {

    /**
     * Simulates FIFO page replacement for a given reference string and number of frames.
     * Returns an array: [pageFaults, pageHits]
     */
    public static int[] simulateFIFO(int[] referenceString, int numFrames) {
        int pageFaults = 0;
        int pageHits = 0;

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> pagesInMemory = new HashSet<>();

        for (int page : referenceString) {
            // Check if the page is already in memory
            if (pagesInMemory.contains(page)) {
                // Page HIT
                pageHits++;
            } else {
                // Page FAULT
                pageFaults++;
                
                // If frames are full, remove the oldest page (victim)
                if (queue.size() == numFrames) {
                    int victim = queue.poll(); // Removes from the front of the queue
                    pagesInMemory.remove(victim); // Removes from our fast-lookup set
                }
                
                // Add the new page to both the queue and the set
                queue.offer(page);
                pagesInMemory.add(page);
            }
        }

        return new int[]{pageFaults, pageHits};
    }

    /**
     * Display results for a test case (FULLY PROVIDED)
     */
    public static void displayResults(String testName, int[] referenceString, 
                                     int frames1, int frames2) {
        System.out.println("\nTest Case: " + testName);
        System.out.print("Reference String: [");
        for (int i = 0; i < referenceString.length; i++) {
            System.out.print(referenceString[i]);
            if (i < referenceString.length - 1) System.out.print(", ");
        }
        System.out.println("]\n");

        // Test with first frame count
        int[] result1 = simulateFIFO(referenceString, frames1);
        System.out.println("--- Running with " + frames1 + " frames ---");
        System.out.println("Page Faults: " + result1[0]);
        System.out.println("Page Hits: " + result1[1]);
        System.out.println("Total Accesses: " + referenceString.length);
        System.out.printf("Hit Rate: %.1f%%\n", 
            (result1[1] * 100.0) / referenceString.length);
        System.out.printf("Fault Rate: %.1f%%\n\n", 
            (result1[0] * 100.0) / referenceString.length);

        // Test with second frame count
        int[] result2 = simulateFIFO(referenceString, frames2);
        System.out.println("--- Running with " + frames2 + " frames ---");
        System.out.println("Page Faults: " + result2[0]);
        System.out.println("Page Hits: " + result2[1]);
        System.out.println("Total Accesses: " + referenceString.length);
        System.out.printf("Hit Rate: %.1f%%\n", 
            (result2[1] * 100.0) / referenceString.length);
        System.out.printf("Fault Rate: %.1f%%\n\n", 
            (result2[0] * 100.0) / referenceString.length);

        // Check for Belady's Anomaly
        if (result2[0] > result1[0]) {
            System.out.println("⚠️  BELADY'S ANOMALY DETECTED! ⚠️");
            System.out.println("With " + frames1 + " frames: " + result1[0] + " page faults");
            System.out.println("With " + frames2 + " frames: " + result2[0] + " page faults");
            System.out.println("Adding more frames INCREASED page faults!\n");
        }

        System.out.println("========================================");
    }

    /**
     * Main method with test cases (FULLY PROVIDED)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("FIFO Page Replacement Simulator");
        System.out.println("========================================");

        // Test Case 1: Random access pattern
        int[] test1 = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        displayResults("Random Access Pattern", test1, 3, 4);

        // Test Case 2: Belady's Anomaly example
        int[] test2 = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        displayResults("Belady's Anomaly Example", test2, 3, 4);

        // Test Case 3: Looping pattern
        int[] test3 = {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4};
        displayResults("Looping Pattern", test3, 3, 4);

        System.out.println("\nSimulation Complete!");
        System.out.println("========================================");
    }
}
