import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DynamicProgramming {
    int test = 0;
    int[][] table1 = new int[12][24];
    int[][] table2 = new int[12][24];
    int[] set1 = {Integer.MAX_VALUE,1,3,5,9,10,15,17,18,19,22,25,27};//dont use [0]
    int[] set2 = {Integer.MAX_VALUE,2,5,8,9,10,15,19,23,24,29,30,32};
    ArrayList<String> all = new ArrayList<>();

    public void printAll(int amt, String soFar, int currentSize) {
        if(amt <= 0) {
            test+= 1;
            System.out.println(soFar);
        }
        else {
            for(int i = currentSize; i <= amt; i ++) {
                printAll(amt - i,soFar +" " +  i ,i);
            }
        }
    }
    static int[][] graphMaker(int[] set) {
        int[][] toReturn = new int[13][25];//add one because we won't use [0]
        for(int i = 1; i<12;i++) {
            for(int j = 1; j < 12; j++) {
                toReturn[j][i] = teapots(i,j,set);
            }
        }
        return toReturn;
    }//2,1


//    public void driver(int[][] set) {//Something I've been trying, got too complex for what we were doing
////        ArrayList<Integer> totals = new ArrayList<>();
//        for(int h = 0; h < set.length; h++) {
//            for(int g = 0; g<set[0].length;g++) {
//                all.clear();
//                getBest(g,"",h);
//                ArrayList<Integer> totals = new ArrayList<>();
//                for(int i = 0; i < all.size();i++) {//going through all the different combinations
//                    ArrayList<String[]> lists = new ArrayList<>();
//                    all.get(i).split(" ");
//                    lists.add(all.get(i).split(" "));
//                    int total = 0;
//                    for(int j = 0; j<lists.size();j++) {
//                        for(int p = 0; i < lists.get(j).length; p++) {
//
//                        }
//                        int to = Integer.parseInt(lists.get(j).toString());
//                        total += to;
//                    }
//                    totals.add(total);
//                }
//                Collections.sort(totals);
//                System.out.println(totals.get(-1));
//            }
//        }
//
//    }

//    public void getBest(int amt, String soFar, int currentSize) {
//        if(amt <= 0) {
//            all.add(soFar);
//        }
//        else {
//            for(int i = currentSize; i <= amt; i ++) {
//                getBest(amt - i,soFar +" " +  i ,i);
//            }
//        }
//
//    }
    static int teapots(int c, int amount, int[] set) {//this was on the notes for coins, though it doesn't work
        if (c < 0 || amount < 0) {return  Integer.MAX_VALUE;}
        if (amount == 0) {return 0;}
        int val = set[c];
        if(amount < val) {return teapots(c-1,amount,set);}
        int use = set[amount] + teapots(c,amount-val,set);//1 + // 1+
        int dont = teapots(c-1,amount,set);
        return  Math.min(use,dont);
    }




    public static void main(String[] args) {
        DynamicProgramming dPro = new DynamicProgramming();
        System.out.println("---Starting test for part 1. All Combinations that add up to 10---");
        dPro.printAll(10,"",1);
        System.out.println("There are " + dPro.test + " Combinations");

        //Testing for part 2
        System.out.println("---Testing that teapots works---");
        System.out.println(teapots(12,11, dPro.set1));//c is y ais on graph
        System.out.println(teapots(8,6, dPro.set1));//above by one
        System.out.println(teapots(12,12, dPro.set1));//This one is wrong
        System.out.println(teapots(1,1, dPro.set1));//all good
        System.out.println(teapots(1,2, dPro.set1));//its squared for some reason
        System.out.println(teapots(1,3, dPro.set1));//its squared for some reason
        System.out.println(teapots(2,2, dPro.set1));//above by 1
        System.out.println(teapots(2,3, dPro.set1));//above by one

        int[][] graph = graphMaker(dPro.set1);


        for(int i = 1; i< 12;i++ ) {
            for(int j = 1; j < 12;j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
