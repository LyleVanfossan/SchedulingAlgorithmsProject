package schedulers;

import Utils.Task;
import Utils.CPU;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FCFS implements Algorithm {

    private final Queue<Task> queued_processes = new LinkedList<>();
    private final ArrayList<Task> completed_processes = new ArrayList<>();
    private int total_time = 0;

    public FCFS(ArrayList<Task> processes) {
        this.queued_processes.addAll(processes);
    }

    @Override
    public void schedule() {
        Task cur_task;
        while (queued_processes.size() > 0) {
            cur_task = pickNextTask();
            CPU.run(cur_task);

            //Task completed run, adding time of arrival and exit time.
            cur_task.setEntry(total_time);
            total_time += cur_task.getBurst();
            cur_task.setExit(total_time);
            completed_processes.add(cur_task);
        }

        calculateAverageTimes(completed_processes);
    }

    @Override
    public Task pickNextTask() {
        Task nextTask = this.queued_processes.peek();
        this.queued_processes.remove();

        return nextTask;
    }
}
