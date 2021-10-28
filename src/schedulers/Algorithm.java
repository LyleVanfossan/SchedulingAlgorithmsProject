package schedulers;

import Utils.Task;

import java.util.ArrayList;

/**
 * Interface for generic scheduling algorithm
 */
public interface Algorithm {

    /**
     * Implementation of scheduling algorithm
     */
    public void schedule();

    /**
     * Selects the next task to be scheduled
     * @return The {@code Task} to be scheduled
     */
    public Task pickNextTask();

    /**
     * Takes a list of completed processes and calculates the average wait, response, and throughput times.
     * @param completed_processes A list of {@link Task} that have been completed
     */
    public default void calculateAverageTimes(ArrayList<Task> completed_processes) {

        float total_wait = 0;
        float total_response = 0;
        float total_turnaround = 0;
        for (Task task : completed_processes) {
            float turnaround = task.getExit();
            total_turnaround += task.getExit();
            total_wait += turnaround - task.getBurst();
            total_response += task.getEntry();
        }

        System.out.println(
                "Average wait: " + total_wait/completed_processes.size() + "\n" +
                "Average response: " + total_response/completed_processes.size() + "\n" +
                "Average turnaround: " + total_turnaround/completed_processes.size() + "\n"
        );
    }
}