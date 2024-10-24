public class Task implements Comparable<Task> {
    public int ID;
    public int start;
    public int deadline;
    public int duration;
    public Task() {
        this.ID = 0;
        this.start = 0;
        this.deadline = 0;
        this.duration = 0;
    }


    public Task(int ID, int start, int deadline, int duration) {
        this.ID = ID;
        this.start = start;
        this.deadline = deadline;
        this.duration = duration;
    }

    public String toString() {
        return "Task " + ID;
    }

    public String toStringL() {
        return "Task " + ID + "[" + start + "-" + deadline + "] " + duration;
    }

//Task file will be for the earliest start time
    public int compareTo(Task t2) {//DEADLINE
        if(t2.deadline > this.deadline) {
            return -1;
        }
        if(t2.deadline < this.deadline) {
            return 1;
        }
        if(t2.start > this.start) {
            return -1;
        }
        return 1;
    }

}
