import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;

public class PoetryMain {
    public static void main(String[] args) throws FileNotFoundException {
//ok
        WritePoetry poem = new WritePoetry();
        System.out.println(poem.WritePoem("beemovie.txt","the",10000,true));
//        System.out.println(poem.WritePoem("green.txt", "sam", 20, true));
//        System.out.println(poem.WritePoem("Lester.txt", "lester", 30, true));
//        System.out.println(poem.WritePoem("HowMany.txt", "how", 30, false));
//        System.out.println(poem.WritePoem("Zebra.txt", "are", 50, true));
    }

    public static class WritePoetry {
        int x;

        WritePoetry() {
            this.x = 0;
        }

        public String WritePoem(String fileName, String startingWord, int poemLength, boolean printOrNo) throws FileNotFoundException {
            HashTable hashTable = readFile("src/" + fileName);
            if (printOrNo) {
                System.out.println(hashTable.toString(hashTable.capacity()));
            }
            String wordToPoem = hashTable.findWord(startingWord).word;
            int wordsSoFar = 1;
            StringBuilder poem = new StringBuilder();
            poem.append(wordToPoem + " ");
            wordToPoem = getNewWord(hashTable.findWord(startingWord));
            if (wordToPoem.equals(".") || wordToPoem.equals(",") || wordToPoem.equals("?") || wordToPoem.equals("!")) {
                poem.append(wordToPoem + " \n");
            } else {
                poem.append(wordToPoem + " ");
            }
            while (wordsSoFar < poemLength - 1) {
                wordToPoem = getNewWord(hashTable.findWord(wordToPoem));
                if (wordToPoem.equals("!") || wordToPoem.equals(".") || wordToPoem.equals("?") || wordToPoem.equals(",")) {
                    poem.append(wordToPoem + " \n");
                } else {
                    poem.append(wordToPoem + " ");
                }
                wordsSoFar += 1;

            }
            poem.append("\n");
            return poem.toString();
        }


        public String getNewWord(WordFreqInfo wordFreqInfo) {
            if (wordFreqInfo == null) {
                return "";
            }
            int occurCt = wordFreqInfo.occurCt / 2;
            //System.out.println("HEREEEEEEEEEEe" + wordFreqInfo.occurCt);
            int lengthOfFollowList = wordFreqInfo.followList.size() - 1;
            Random random = new Random();
            int word = 0;
            int randomNumber = random.nextInt(occurCt);
            //System.out.println(randomNumber);
            int soFar = 0;
            while(soFar < randomNumber) {
                soFar += wordFreqInfo.followList.get(word).followCt;
                if(soFar >= randomNumber) {
                    return wordFreqInfo.followList.get(word).follow;
                }
                if(word == lengthOfFollowList) {
                    //System.out.println("THIS IS HERE");
                    return wordFreqInfo.followList.get(word).follow;
                }
//                System.out.println(soFar);
//                System.out.println(randomNumber);
//                System.out.println();
                word ++;
            }
            return wordFreqInfo.followList.get(word).follow;
        }


        public HashTable readFile(String fileName) throws FileNotFoundException {
            HashTable<String> hashTable = new HashTable<>();
            File poem = new File(fileName);
            Scanner reader = new Scanner(poem);
            String currWord = reader.next().toLowerCase();
            hashTable.insert(currWord);

            while (reader.hasNext()) {
                String nextWord = reader.next().toLowerCase();
                WordFreqInfo firstWord = hashTable.findWord(currWord);
                if (firstWord != null) {
                    firstWord.updateFollows(nextWord);
                }
                if (hashTable.contains(nextWord)) {
                    WordFreqInfo found = hashTable.findWord(nextWord);
                    found.occurCt += 1;
                }
                if (!hashTable.contains(nextWord)) {
                    hashTable.insert(nextWord);
                }
                currWord = nextWord;
            }
            return hashTable;
        }

    }
}