
public class Task1 extends Task {
    public Task1(int ID, int start, int deadline, int duration) {

        super(ID,start,deadline,duration);
    }

    @Override
    public int compareTo(Task t2) {//START TIME
        if(t2.start < this.start) {
            return 1;
        }
        if(t2.start > this.start) {
            return -1;
        }
        if(t2.start == this.start) {
            if(t2.deadline > this.deadline) {
                return -1;
            }
            if(t2.duration < this.deadline) {
                return 1;
            }
            if(t2.deadline == this.deadline) {
                if(t2.duration > this.duration) {
                    return 1;
                }
                return -1;
            }
        }
        return -1;
    }


    public static void main(String []args0 ) {
        Task1 task1 = new Task1(1,1,1,1);
        System.out.println(task1.toStringL());
        System.out.println(task1.toString());
    }

}
