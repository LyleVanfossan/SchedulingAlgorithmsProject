package schedulers;

import Utils.CPU;
import Utils.Task;

import java.util.*;
import java.util.stream.Collectors;

public class PriorityRR implements Algorithm {

    private final Stack<Queue<Task>> process_queue = new Stack<>();
    private Queue<Task> cur_priority;
    private final ArrayList<Task> completed_processes = new ArrayList<>();
    private int total_time = 0;


    public PriorityRR(ArrayList<Task> processes) {
        Map<Integer, List<Task>> tasks_grouped = Collections.synchronizedMap(
                processes.stream().collect(Collectors.groupingBy(Task::getPriority)));
        tasks_grouped.forEach((task, list) -> this.process_queue.add(new LinkedList<>(list)));
        completed_processes.addAll(processes);
    }


    @Override
    public void schedule() {

        Task cur_task;
        while (process_queue.size() > 0) {
            cur_task = pickNextTask();

            if (cur_task == null) {
                break;
            }

            if (cur_task.getEntry() == null) {
                cur_task.setEntry(total_time);
            }

            CPU.run(cur_task);
            int quantum = 10;

            if (cur_task.getRemaining() > quantum) {
                cur_task.setRemaining(cur_task.getRemaining() - quantum);
                this.cur_priority.remove();
                this.cur_priority.add(cur_task);
                total_time += quantum;
            } else if(cur_task.getRemaining() <= quantum) {
                this.cur_priority.remove();
                total_time += cur_task.getRemaining();
                cur_task.setExit(total_time);
            }
        }

        calculateAverageTimes(completed_processes);
    }

    @Override
    public Task pickNextTask() {
        this.cur_priority = this.process_queue.peek();
        if (this.cur_priority.isEmpty()) {

            this.process_queue.pop();
            try {
                this.cur_priority = this.process_queue.peek();
            } catch (EmptyStackException e) {
                return null;
            }
        }

        return this.cur_priority.peek();
    }
}
