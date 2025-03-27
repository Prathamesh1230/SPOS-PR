import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int id; // Process ID
    int burstTime; // Burst time
    int waitingTime; // Waiting time
    int turnAroundTime; // Turnaround time

    Process(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
    }
}

public class SJF {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];

        // Input: Burst times for processes
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        // Sort processes by burst time (Shortest Job First)
        Arrays.sort(processes, Comparator.comparingInt(p -> p.burstTime));

        // Calculate waiting time and turnaround time
        int totalWaitingTime = 0, totalTurnAroundTime = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                processes[i].waitingTime = 0;
            } else {
                processes[i].waitingTime = processes[i - 1].waitingTime + processes[i - 1].burstTime;
            }
            processes[i].turnAroundTime = processes[i].waitingTime + processes[i].burstTime;
            totalWaitingTime += processes[i].waitingTime;
            totalTurnAroundTime += processes[i].turnAroundTime;
        }

        // Display process details
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println("P" + p.id + "\t\t" + p.burstTime + "\t\t" + p.waitingTime + "\t\t" + p.turnAroundTime);
        }

        // Display average waiting and turnaround times
        System.out.println("\nAverage Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnAroundTime / n);

        scanner.close();
    }
}

/*
 * Enter the number of processes: 4
 * Enter burst time for Process 1: 6
 * Enter burst time for Process 2: 8
 * Enter burst time for Process 3: 7
 * Enter burst time for Process 4: 3
 * 
 */