import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;


public class LadderGame extends LinkedListQueue {
    static int MaxWordSize = 15;  //Max legnth word allowed
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;  // Random number generator

    /**
     *  Creates separate ArrayLists for words of each length
     * @param dictionaryFileName  Contains all words to be used in word ladder in alpha order
     */
    public LadderGame(String dictionaryFileName) {
        random = new Random();
        allList = new ArrayList[MaxWordSize];
        for (int i = 0; i < MaxWordSize; i++)
            allList[i] = new ArrayList<String>();

        try {
            Scanner reader = new Scanner(new File(dictionaryFileName));
            while (reader.hasNext()) {
                String word = reader.next();
                if (word.length()< MaxWordSize) allList[word.length()].add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Given starting and ending words, create a word ladder of minimal length.
     * @param startWord  Beginning word of word ladder
     * @param endWord  Ending word on word ladder
     */
    public void play(String startWord, String endWord) {
        if (startWord.length() != endWord.length()){
            System.out.println("Words are not the same length");
            return;
        }
        if (startWord.length()  >= MaxWordSize) return;
        ArrayList list = new ArrayList();

        ArrayList<String> l = allList[startWord.length()];
        list = (ArrayList) l.clone();
        if(!list.contains(startWord) || !list.contains(endWord)) {
            System.out.println("Word not found in dictionary");
        }
        System.out.println("Seeking a solution from " + startWord + " ->" + endWord + " Size of List " + list.size());
        
        // Solve the word ladder problem

        if(startWord.equals(endWord)) {
            System.out.println("Starting Word = Ending Word");
            return;
        }

        LinkedListG<Object> queue = new LinkedListG<>();

        boolean done = false;
        int enquqes = 0;

        LadderInfo initladder = new LadderInfo(startWord,0,"" + startWord);
        queue.enque(initladder);
        enquqes += 1;

        while(!done && !queue.returnIfEmpty()) {
            LadderInfo wordCheck = (LadderInfo) queue.getHead();
            queue.deque();
            //System.out.println(wordCheck.toStringForClass()); //use this if you want everything to print
            if(wordCheck.word.equals(endWord)) {
                System.out.print(wordCheck.toStringForClass());
                System.out.println(" " + enquqes + " enques");
                done = true;
                continue;
            }

            String word = wordCheck.word;
            char[] charArray = word.toCharArray();

            for(int letterPos = 0; letterPos < charArray.length; letterPos++) {
                for(char alphabet = 'a'; alphabet <='z'; alphabet++) {
                    char temporaryHolding = charArray[letterPos];
                    if(charArray[letterPos] != alphabet) {
                        charArray[letterPos] = alphabet;
                    }
                    String toQueue = String.valueOf(charArray);

                    if(list.contains(toQueue)) {


                        LadderInfo nextLadder = new LadderInfo(toQueue, wordCheck.moves + 1, wordCheck.ladder + " "  + toQueue);

                        list.remove(toQueue);
                        if(wordCheck.word.equals(endWord)) {
                            System.out.println(nextLadder.toStringForClass());
                            done = true;
                        }
                        enquqes += 1;
                        queue.enque(nextLadder);
                    }

                    charArray[letterPos] = temporaryHolding;

                }
            }

        }
        if(!done) {
            System.out.println("\"I did my time, cannot find it for you, sorry\" -My Program");
        }
    }
      
    
    /**
     * Find a word ladder between random words of length len
     * @param len  Length of words in desired word ladder
     */
    public void play(int len) {
       if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String firstWord = list.get(random.nextInt(list.size()));
        String lastWord = list.get(random.nextInt(list.size()));
        play(firstWord, lastWord );
    }

}


