import java.util.*;

public class WordFreqInfo {
    public String word;
    public int occurCt;
    ArrayList<Freq> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCt = count;
        this.followList = new ArrayList<Freq>();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word+":");
        sb.append(" (" + occurCt + ") : ");
        for (Freq f : followList)
            sb.append(f.toString());

        return sb.toString();
    }

    public void updateFollows(String follow) {
       //System.out.println("updateFollows " + word + " " + follow);
        occurCt++;
        for (Freq f : followList) {
            if (follow.compareTo(f.follow) == 0) {
                f.followCt++;
                return;
            }
        }
        followList.add(new Freq(follow, 1));
    }

    public static class Freq {
        String follow;
        int followCt;

        public Freq(String follow, int ct) {
            this.follow = follow;
            this.followCt = ct;
        }
        public int getCT() {
            return followCt;
        }

        public String toString() {
            return follow + " [" + followCt + "] ";
        }

        public boolean equals(Freq f2) {
            return this.follow.equals(f2.follow);
        }
    }


    public static void main(String[] args) {
        WordFreqInfo first = new WordFreqInfo("hello",1);
        System.out.println("Tests for checking empty object");
        System.out.println(first.word);
        System.out.println(first.occurCt);
        System.out.println(first.followList);

        System.out.println(first.toString());
        first.updateFollows("test1");//updateFollows increments the first.word as well as the follwing, so we don't have to
        System.out.println(first.toString());
        first.updateFollows("test1");
        System.out.println(first.toString());
        System.out.println(first.followList.size());
        first.updateFollows("test2");

        first.updateFollows("test3");
        System.out.println(first.followList.size());
        int topRandom = first.followList.size();
        //then here we can do random from 0-toprandom to get our word
        System.out.println(first.followList.get(topRandom - 1).follow);//prints out our word without the number in front

    }

}

