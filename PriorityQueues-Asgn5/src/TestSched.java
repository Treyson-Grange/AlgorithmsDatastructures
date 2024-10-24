import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestSched {

    public static void readTasks(String filename, ArrayList<Task> task1, ArrayList<Task> task2, ArrayList<Task> task3) throws FileNotFoundException {
        // Create lists where base type is different
        File file1 = new File("src/" + filename);
        Scanner reader = new Scanner(file1);
        int lineNum = 1;
        while(reader.hasNextInt()) {
            int start = reader.nextInt();
            int deadline = reader.nextInt();
            int duration = reader.nextInt();
            Task toList = new Task(lineNum,start,deadline,duration);
            Task1 to1 = new Task1(lineNum,start,deadline,duration);
            Task2 to2 = new Task2(lineNum,start,deadline,duration);
            lineNum += 1;
            task1.add(toList);
            task2.add(to1);
            task3.add(to2);
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        Scheduler s = new Scheduler();
        String [] files = {"taskset1.txt","taskset2.txt","taskset3.txt","taskset4.txt","taskset5.txt" };
        for (String f : files) {
            ArrayList<Task> t1 = new ArrayList();    // elements are Task1
            ArrayList<Task> t2 = new ArrayList();    // elements are Task2
            ArrayList<Task> t3 = new ArrayList();    // elements are Task3
            readTasks(f, t1, t2, t3);
            s.makeSchedule("Deadline Priority "+ f, t1);
            s.makeSchedule("Start Time Priority " + f, t2);
            s.makeSchedule("Wild and Crazy priority " + f, t3);
       }

    }
}

