import java.util.Scanner;

class Process {
    int id; // Process ID
    int burstTime; // Burst time
    int remainingTime; // Remaining time for the process
    int waitingTime; // Waiting time
    int turnAroundTime; // Turnaround time

    Process(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // Initially, remaining time is equal to burst time
    }
}

public class RoundRobin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of processes and quantum time
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        System.out.print("Enter the time quantum: ");
        int quantum = scanner.nextInt();

        Process[] processes = new Process[n];

        // Input: Burst times for processes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        int currentTime = 0; // Tracks current time
        boolean allCompleted = false;

        // Simulate Round Robin Scheduling
        while (!allCompleted) {
            allCompleted = true; // Assume all processes are completed

            for (int i = 0; i < n; i++) {
                Process p = processes[i];

                // If the process still has remaining time
                if (p.remainingTime > 0) {
                    allCompleted = false; // At least one process is still not completed

                    int timeSpent = Math.min(p.remainingTime, quantum); // Process runs for a maximum of quantum time
                    p.remainingTime -= timeSpent; // Reduce the remaining time of the process
                    currentTime += timeSpent; // Increase current time by the time spent by the process

                    // If the process is completed
                    if (p.remainingTime == 0) {
                        p.turnAroundTime = currentTime; // Turnaround time is current time when the process completes
                        p.waitingTime = p.turnAroundTime - p.burstTime; // Waiting time is turnaround time - burst time
                    }
                }
            }
        }

        // Display process details
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println("P" + p.id + "\t\t" + p.burstTime + "\t\t" + p.waitingTime + "\t\t" + p.turnAroundTime);
        }

        // Calculate and display average waiting time and turnaround time
        int totalWaitingTime = 0, totalTurnAroundTime = 0;
        for (Process p : processes) {
            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;
        }

        System.out.println("\nAverage Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnAroundTime / n);

        scanner.close();
    }
}

/*
 * Enter the number of processes: 4
 * Enter the time quantum: 4
 * Enter burst time for Process 1: 5
 * Enter burst time for Process 2: 7
 * Enter burst time for Process 3: 8
 * Enter burst time for Process 4: 6
 * 
 */