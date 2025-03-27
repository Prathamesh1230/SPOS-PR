import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime; // Arrival time
    int burstTime; // Burst time
    int completionTime;
    int turnAroundTime;
    int waitingTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FCFS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] processes = new Process[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes by Arrival Time
        sortByArrivalTime(processes);

        // Calculate Completion Time, Turnaround Time, and Waiting Time
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.completionTime = currentTime + process.burstTime;
            currentTime = process.completionTime;

            process.turnAroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;
        }

        // Display results
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (Process process : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n",
                    process.pid, process.arrivalTime, process.burstTime,
                    process.completionTime, process.turnAroundTime, process.waitingTime);
        }

        // Calculate average Turnaround Time and Waiting Time
        double totalTurnAroundTime = 0, totalWaitingTime = 0;
        for (Process process : processes) {
            totalTurnAroundTime += process.turnAroundTime;
            totalWaitingTime += process.waitingTime;
        }
        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTurnAroundTime / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / n);

        scanner.close();
    }

    // Helper function to sort processes by Arrival Time
    private static void sortByArrivalTime(Process[] processes) {
        for (int i = 0; i < processes.length - 1; i++) {
            for (int j = 0; j < processes.length - i - 1; j++) {
                if (processes[j].arrivalTime > processes[j + 1].arrivalTime) {
                    Process temp = processes[j];
                    processes[j] = processes[j + 1];
                    processes[j + 1] = temp;
                }
            }
        }
    }
}

/*
 * Enter the number of processes: 3
 * Enter details for Process 1:
 * Arrival Time: 0
 * Burst Time: 5
 * Enter details for Process 2:
 * Arrival Time: 1
 * Burst Time: 3
 * Enter details for Process 3:
 * Arrival Time: 2
 * Burst Time: 8
 * 
 */