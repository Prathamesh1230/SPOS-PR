import java.util.Scanner;

public class BestWorstFit {

    // Best Fit Memory Allocation
    static void bestFit(int[] blockSizes, int[] processSizes) {
        int[] allocation = new int[processSizes.length];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1; // Initialize allocation array
        }

        for (int i = 0; i < processSizes.length; i++) {
            int bestIdx = -1;
            for (int j = 0; j < blockSizes.length; j++) {
                if (blockSizes[j] >= processSizes[i]) {
                    if (bestIdx == -1 || blockSizes[j] < blockSizes[bestIdx]) {
                        bestIdx = j;
                    }
                }
            }

            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSizes[bestIdx] -= processSizes[i];
            }
        }

        printAllocation("Best Fit", allocation, processSizes);
    }

    // Worst Fit Memory Allocation
    static void worstFit(int[] blockSizes, int[] processSizes) {
        int[] allocation = new int[processSizes.length];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1; // Initialize allocation array
        }

        for (int i = 0; i < processSizes.length; i++) {
            int worstIdx = -1;
            for (int j = 0; j < blockSizes.length; j++) {
                if (blockSizes[j] >= processSizes[i]) {
                    if (worstIdx == -1 || blockSizes[j] > blockSizes[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }

            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                blockSizes[worstIdx] -= processSizes[i];
            }
        }

        printAllocation("Worst Fit", allocation, processSizes);
    }

    // Print the allocation result
    static void printAllocation(String strategy, int[] allocation, int[] processSizes) {
        System.out.println("\n" + strategy + " Allocation:");
        System.out.printf("%-10s %-10s %-10s\n", "Process", "Size", "Block");
        for (int i = 0; i < processSizes.length; i++) {
            System.out.printf("%-10d %-10d %-10s\n", i + 1, processSizes[i],
                    allocation[i] != -1 ? allocation[i] + 1 : "Not Allocated");
        }
    }

    // Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int nBlocks = sc.nextInt();
        int[] blockSizes = new int[nBlocks];
        System.out.println("Enter the sizes of the memory blocks:");
        for (int i = 0; i < nBlocks; i++) {
            blockSizes[i] = sc.nextInt();
        }

        System.out.print("Enter the number of processes: ");
        int nProcesses = sc.nextInt();
        int[] processSizes = new int[nProcesses];
        System.out.println("Enter the sizes of the processes:");
        for (int i = 0; i < nProcesses; i++) {
            processSizes[i] = sc.nextInt();
        }

        // Make copies of block sizes for reuse
        int[] blockSizesForBestFit = blockSizes.clone();
        int[] blockSizesForWorstFit = blockSizes.clone();

        // Best Fit Allocation
        bestFit(blockSizesForBestFit, processSizes);

        // Worst Fit Allocation
        worstFit(blockSizesForWorstFit, processSizes);

        sc.close();
    }
}

/*
 * Enter the number of memory blocks: 5
 * Enter the sizes of the memory blocks:
 * 100 500 200 3 00 600
 * Enter the number of processes: 4
 * Enter the sizes of the processes:
 * 212 417 112 426
 */