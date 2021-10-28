import Utils.Task;
import schedulers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Driver {
    public static void main(String[] args) {
        ArrayList<Task> processes = new ArrayList<Task>();

        try {
            File myObj = new File(args[1]);
            Scanner myReader = new Scanner(myObj);
            AtomicInteger task_id = new AtomicInteger(0);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] variables = data.split(", *");
                Task process = new Task(variables[0], Integer.parseInt(variables[1]), Integer.parseInt(variables[2]));

                task_id.incrementAndGet();
                AtomicInteger cur_tid = new AtomicInteger(task_id.get());
                process.setTid(cur_tid);
                processes.add(process);
            }
            myReader.close();

            switch (args[0]) {
                case "fcfs":
                    new FCFS(processes).schedule();
                    break;
                case "sjf":
                    new SJF(processes).schedule();
                    break;
                case "priority":
                    new Priority(processes).schedule();
                    break;
                case "RR":
                    new RR(processes).schedule();
                    break;
                case "priorityRR":
                    new PriorityRR(processes).schedule();
                    break;

            }
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be read");
            e.printStackTrace();
        }
    }
}