
public class Task2 extends Task {
    public Task2(int ID, int start, int deadline, int duration) {

        super(ID,start,deadline,duration);
    }
    // Prioirity is deadline
    @Override
    public int compareTo(Task t2) {//WILD ONE
        if(t2.duration >this.duration) {
            return 1;
        }
        if(t2.duration<this.duration) {
            return -1;
        }
        if(t2.ID < this.ID) {
            return -1;
        }
        return 1;
    }


    public static void main(String []args0 ) {
        Task2 task1 = new Task2(1,1,1,1);
        System.out.println(task1.toStringL());
        System.out.println(task1.toString());
    }

}
