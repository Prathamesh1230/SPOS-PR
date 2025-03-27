import java.util.Scanner;

class Process {
    int id; // Process ID
    int burstTime; // Burst time
    int priority; // Priority
    int waitingTime; // Waiting time
    int turnAroundTime; // Turnaround time

    Process(int id, int burstTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityScheduling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];

        // Input: Burst times and priorities for processes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            System.out.print("Enter priority for Process " + (i + 1) + " (lower number means higher priority): ");
            int priority = scanner.nextInt();

            processes[i] = new Process(i + 1, burstTime, priority);
        }

        // Sort processes based on priority (lowest priority number = highest priority)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (processes[i].priority > processes[j].priority) {
                    // Swap processes[i] and processes[j]
                    Process temp = processes[i];
                    processes[i] = processes[j];
                    processes[j] = temp;
                }
            }
        }

        // Calculate waiting time and turnaround time
        int totalWaitingTime = 0, totalTurnAroundTime = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                processes[i].waitingTime = 0; // First process has no waiting time
            } else {
                processes[i].waitingTime = processes[i - 1].waitingTime + processes[i - 1].burstTime;
            }
            processes[i].turnAroundTime = processes[i].waitingTime + processes[i].burstTime;
            totalWaitingTime += processes[i].waitingTime;
            totalTurnAroundTime += processes[i].turnAroundTime;
        }

        // Display process details
        System.out.println("\nProcess\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println("P" + p.id + "\t\t" + p.burstTime + "\t\t" + p.priority + "\t\t" + p.waitingTime + "\t\t"
                    + p.turnAroundTime);
        }

        // Calculate and display average waiting time and turnaround time
        System.out.println("\nAverage Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnAroundTime / n);

        scanner.close();
    }
}

/*
 * Enter the number of processes: 4
 * Enter burst time for Process 1: 6
 * Enter priority for Process 1 (lower number means higher priority): 2
 * Enter burst time for Process 2: 8
 * Enter priority for Process 2 (lower number means higher priority): 1
 * Enter burst time for Process 3: 7
 * Enter priority for Process 3 (lower number means higher priority): 3
 * Enter burst time for Process 4: 3
 * Enter priority for Process 4 (lower number means higher priority): 2
 * 
 */