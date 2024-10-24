import java.util.Locale;
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



    public LadderInfo play2(String startWord, String endWord) {
        if (startWord.length() != endWord.length()){
            System.out.println("Words are not the same length");
            return new LadderInfo("Words are not the same length",0,"",1,0);
        }
        if (startWord.length()  >= MaxWordSize) return new LadderInfo("Word is too big!",0,"",1,0);
        ArrayList list = new ArrayList();

        ArrayList<String> l = allList[startWord.length()];
        list = (ArrayList) l.clone();
        if(!list.contains(startWord) || !list.contains(endWord)) {
            System.out.println("Word not found in dictionary");
        }
        list.remove(startWord);

        //solve the avl tree queue problem

        if(startWord.equals(endWord)) {
            System.out.println("Starting Word = Ending Word");
        }

        AVLTree<LadderInfo> queue = new AVLTree<LadderInfo>();
        boolean done = false;
        int enquqes = 0;
        queue.insert(new LadderInfo(startWord,0,"" + startWord,calculateScore(startWord,endWord,0),enquqes));
        enquqes += 1;
        while(!done && !queue.isEmpty()) {
            LadderInfo wordCheck = queue.deleteMin();
            if(wordCheck.word.equals(endWord)) {
                System.out.println(wordCheck.ladder);
                done = true;
                continue;
            }
            String word = wordCheck.word;
            char[] charArray = word.toCharArray();
            for(int letterPos = 0; letterPos<charArray.length; letterPos ++) {
                for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
                    char temporaryHolding = charArray[letterPos];
                    if(charArray[letterPos] != alphabet) {
                        charArray[letterPos] = alphabet;
                    }
                    String toQueue = String.valueOf(charArray);
                    if(list.contains(toQueue) && !wordCheck.ladder.contains(toQueue)) {
                        LadderInfo nextLadder = new LadderInfo(toQueue,wordCheck.moves + 1, wordCheck.ladder + " " + toQueue,calculateScore(toQueue,endWord,wordCheck.ladder.length()),enquqes);
                        if (!nextLadder.ladder.contains(wordCheck.word)) {
                            System.out.print(wordCheck.word);
                        }
                        list.remove(toQueue);
                        if(toQueue.equals(endWord)) {
                            done = true;
                            return nextLadder;
                        }
                        enquqes += 1;
                        queue.insert(nextLadder);
                    }
                    charArray[letterPos] = temporaryHolding;
                }

            }
        }
        return new LadderInfo("Cannot Find a word ladder",0,"",1,enquqes);
    }
    public int calculateScore(String startWord, String endWord, int ladderLength) {
        int score = ladderLength;
        String[] startArray = startWord.split("");
        String[] endArray = endWord.split("");
        for(int i=0; i < startArray.length; i++) {
            if(startArray[i].equals(endArray[i])) {score -= 1;}
        }

        return score;
    }

    public LadderInfo play(String startWord, String endWord) {//On the last assignment, my algorithm was bad because it had too many enqueues. didn't change anything if you see this message
        if (startWord.length() != endWord.length()){
            System.out.println("Words are not the same length");
            return new LadderInfo("NOT SAME LENGTH",0,"",1,0);
        }
        if (startWord.length()  >= MaxWordSize) return new LadderInfo("WORD TOO LONG",0,"",1,0);
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
            return new LadderInfo("Start word = ending word",0,"",1,0);
        }

        LinkedListG<Object> queue = new LinkedListG<>();


        boolean done = false;
        int enquqes = 0;

        LadderInfo initladder = new LadderInfo(startWord,0,"" + startWord,0,enquqes);
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

                        enquqes += 1;
                        LadderInfo nextLadder = new LadderInfo(toQueue, wordCheck.moves + 1, wordCheck.ladder + " "  + toQueue,0,enquqes);

                        list.remove(toQueue);
                        if(toQueue.equals(endWord)) {
                            done = true;
                            return nextLadder;
                        }

                        queue.enque(nextLadder);
                    }

                    charArray[letterPos] = temporaryHolding;

                }
            }

        }
        return new LadderInfo("Cannot Find a word ladder",0,"",1,enquqes);
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


