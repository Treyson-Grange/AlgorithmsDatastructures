import org.w3c.dom.Node;

import java.awt.*;
import java.util.ArrayList;

public class Scheduler<E extends Comparable> {
    public void makeSchedule(String title, ArrayList<Task> list) {
        System.out.println(title);
        MinHeapLeftistPQ<Task> queue = new MinHeapLeftistPQ<>();
        int time = 1;
        int numberLate = 0;
        int totalLate = 0;
        while(true) {
            for (Task task : list) {
                if (task.start == time) {
                    queue.insert(task);
                }
            }
            if(!queue.isEmpty()) {
                Task task = queue.deleteMin().element;

                task.duration -= 1;//dfasdfasdf

                if(task.duration > 0) {
                    System.out.println("Time: " + time + " Task " + task.ID );
                    queue.insert(task);
                }
                if(task.duration <=0) {
                    if(task.deadline < time) {//it is late
                        numberLate += 1;
                        System.out.println("Time: " + time + " Task " + task.ID +  " ** " + "Late " + (time - task.deadline));
                        totalLate += time - task.deadline;
                    }
                    else{
                        System.out.println("Time: " + time + " Task " + task.ID + " ** ");
                    }
                }
            }
            time += 1;
            if(queue.isEmpty()) {break;}
        }
        System.out.println("Tasks late: " + numberLate + " Total minutes late: " + totalLate);
        System.out.println();

    }


}


