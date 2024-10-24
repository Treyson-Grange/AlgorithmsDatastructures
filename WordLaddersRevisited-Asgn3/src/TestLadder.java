/**
 * TestLadder is the main class where a LadderGame is played using different word inputs. The play function can be
 * called given a starting and ending words or an integer which will lead to random words being selected. It also
 * prints out the elapsed time for the game.
 */
public class TestLadder {
    public static void main(String[] args) {
        String[] source ={"oops", "slow", "kiss", "cock","jura","stet","rums","stylus","herded","trip","jaggs" };
        String[] dest   ={"tots","fast", "woof", "numb","such","whey","numb","swives","raffia","tube","smalt"};

// Since constructor has the dictionary, it can read one and used // to solve many problems.        
LadderGame g = new LadderGame("src/dictionary-1.txt");
        for (int i=0; i < source.length; i++){
            LadderInfo results = g.play(source[i], dest[i]);//bruteForce
            System.out.println("BRUTE SOLUTION:\t"+results.toStringForClass() + ": Total enqueues " + results.enqueues);
            LadderInfo aResults = g.play2(source[i],dest[i]);
            System.out.println("A* SOLUTION:\t" + aResults.toStringForClass() + ": Total enqueues " + aResults.enqueues);
            if(aResults.ladder.length() == results.ladder.length()) {
                System.out.println("The two word ladders are the SAME length");
            }
            else {System.out.println("The two word ladders are of DIFFERENT lengths");}

            float ratio = (float)aResults.enqueues / results.enqueues;
            ratio = 1 - ratio;
            ratio = ratio * 100;

            System.out.println("Comparison "+results.enqueues + " " + aResults.enqueues + " %" + ratio + " better");
            System.out.println("\n");


        }

    }
}