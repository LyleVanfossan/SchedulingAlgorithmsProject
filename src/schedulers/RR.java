package schedulers;

import Utils.CPU;
import Utils.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR implements Algorithm {

    private final Queue<Task> process_queue = new LinkedList<>();
    private final ArrayList<Task> completed_processes = new ArrayList<>();
    private int total_time = 0;

    public RR(ArrayList<Task> processes) {
        this.process_queue.addAll(processes);
        this.completed_processes.addAll(processes);
    }

    @Override
    public void schedule() {
        while (this.process_queue.size() > 0) {
            Task cur_task = pickNextTask();

            CPU.run(cur_task);

            if (cur_task.getEntry() == null) {
                cur_task.setEntry(total_time);
            }

            int quantum = 10;

            if (cur_task.getRemaining() > quantum) {
                cur_task.setRemaining(cur_task.getRemaining() - quantum);
                this.process_queue.remove();
                this.process_queue.add(cur_task);
                total_time += quantum;
            } else if(cur_task.getRemaining() <= quantum) {
                this.process_queue.remove(cur_task);
                total_time += cur_task.getRemaining();
                cur_task.setExit(total_time);
            }
        }

        calculateAverageTimes(completed_processes);
    }

    @Override
    public Task pickNextTask() {
        return this.process_queue.peek();
    }
}
