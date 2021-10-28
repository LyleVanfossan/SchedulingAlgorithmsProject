package schedulers;

import Utils.CPU;
import Utils.Task;

import java.util.*;

public class SJF implements Algorithm {

    private final Queue<Task> queued_processes;
    private final ArrayList<Task> completed_processes = new ArrayList<>();
    private int total_time = 0;

    public SJF(ArrayList<Task> processes) {
        this.queued_processes = new PriorityQueue<>(Comparator.comparing(Task::getBurst));
        this.queued_processes.addAll(processes);
    }

    @Override
    public void schedule() {
        Task cur_task;
        while (queued_processes.size() > 0) {
            cur_task = pickNextTask();
            CPU.run(cur_task);
            cur_task.setEntry(total_time);
            total_time += cur_task.getBurst();
            cur_task.setExit(total_time);
            completed_processes.add(cur_task);
        }

        calculateAverageTimes(completed_processes);
    }

    @Override
    public Task pickNextTask() {
        Task process;

        for (int i = 0; i < queued_processes.size(); i++) {
            if (queued_processes.size() == 1) {
                process = queued_processes.peek();
                queued_processes.remove();
                return process;
            }
        }

        process = queued_processes.peek();
        process.setEntry(total_time);
        queued_processes.remove();

        return process;
    }
}
