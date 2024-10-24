/**
 * This class creates WordInfo objects which allows the program
 * to keep track of words, their partial ladder, and number of moves in the word ladder.
 */
public class LadderInfo implements Comparable<LadderInfo> {
    public String word;  //last word in the word ladder
    public int moves;    //number of in the word ladder
    public String ladder;// printable representation of ladder
    public int height;//height in AVL TREE
    public int enqueues;

    public LadderInfo(String word, int moves, String ladder,int height,int enqueues){
        this.word = word;
        this.moves = moves;
        this.ladder = ladder;
        this.height = height;
        this.enqueues = enqueues;
    }

    public String toStringForClass(){
        return "Word " + word    + " Moves " +moves  + " Ladder ["+ ladder +"]";
    }


    public String wordToString() {
        return word;
    }
    @Override
    public int compareTo(LadderInfo b2) {
        if(Math.max(this.height,b2.height) == this.height) {
            return 1;
        }
        if(Math.max(this.height,b2.height) == b2.height) {
            return -1;
        }
        else{
            return 0;
        }
    }
}

