package Utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Task class for scheduling algorithms.
 */
public class Task {
    private final String name;

    private final int priority;
    private int burst;
    private Integer entry_time = null;
    private int exit_time;
    private int time_remaining;
    private AtomicInteger tid;

    public Task(String name, int priority, int burst) {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
        this.time_remaining = burst;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getBurst() {
        return this.burst;
    }

    public int getExit() {
        return exit_time;
    }

    public void setExit(int exit_time) {
        this.exit_time = exit_time;
    }

    public Integer getEntry() {
        return entry_time;
    }

    public void setEntry(int entry_time) {
        this.entry_time = entry_time;
    }

    public int getRemaining() {
        return time_remaining;
    }

    public void setRemaining(int time_remaining) {
        this.time_remaining = time_remaining;
    }

    public void setTid(AtomicInteger tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return
            "Name: " + name + "\n" +
            "TID: " + tid + "\n" +
            "Priority: " + priority + "\n" +
            "Burst: " + time_remaining + "\n";
    }
}
