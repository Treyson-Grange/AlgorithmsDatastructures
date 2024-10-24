import java.util.Arrays;

public class UnionFindGraph {
    public UnionFindGraph(int numberElements) {
        int[] set = new int[numberElements];//might need to change this because of the tops and things being numbers, and we need to union find those as well
        Arrays.fill(set, -1);
        this.set = set;
    }
    public void union(int first, int second) {
        first = find(first);
        second = find(second);
        if(first==second) {
            return;
        }
        if(set[second] < set[first]) {
            set[first] = second;
        }
        else{
            if(set[first]==set[second]) {
                set[first]--;
            }
            set[second] = first;
        }
    }
    public int find(int x) {
        if(set[x] < 0) {
            return x;
        }
        else{return set[x] = find(set[x]);}
    }
    public int[] set;

    public static void main(String[] args) {
        UnionFindGraph graph = new UnionFindGraph(100);
        System.out.println("----------STARTING UNION FIND TESTS----------\n");
//        ---Union Functionality Test---
        graph.union(1,2);
        graph.union(3,4);
        System.out.println("---We union 1 and 2, then 3 and 4---\n");
        graph.union(1,3);
        System.out.println("---Then we union both unions!---");
        System.out.println("find on 1: "+graph.find(1) + " find on 3: " + graph.find(3));
        System.out.println("But what happened to 2 and 4? \n find on 2: " + graph.find(2) + " find on 4: " + graph.find(4) + "\nThis means that 2, 3, and 4 all point to 1 and are in the same group as 1.");
//        ---Path Compression Test---
        System.out.println("\n\n---Testing Path Compression---");
        System.out.println("Union 30 with 29 28 27 and 26...");
        graph.union(31,30);//note that because lists are silly, we -1
        graph.union(31,28);
        graph.union(31,27);
        graph.union(31,26);
        System.out.print("find(29): "+graph.find(29) + "   ");
        System.out.print("find(28): "+graph.find(28) + "   ");
        System.out.print("find(27): "+graph.find(27) + "   ");
        System.out.println("find(26): "+graph.find(26));
        System.out.println("If these are all 3, path compression is working");

//        ---Testing More!---
        System.out.println("\n\n---Testing More---\nStarting over with a new union find graph, of size 50, lets union every number in order with 50!");
        UnionFindGraph graph2 = new UnionFindGraph(50);
        for(int i =0; i<graph2.set.length;i++) {
            graph2.union(36,i);
        }
        System.out.println("Due to Union favoring the first in the union function, we can expect that if we put 36 as the first argument, every number will point to 36!");
        for(int j=0;j<graph2.set.length;j++) {
            System.out.print(graph2.find(j) + " ");
        }



//        ---Test Winning!---
        System.out.println("\n\n\n---Testing Winning!---\nStarting over with a new union find graph of size 50");
        UnionFindGraph graph3 = new UnionFindGraph(150);
        System.out.println("In the game, 125 and 126 are the top and bottom, so if they are being correctly unioned the game can end! Let's do that now.");
        System.out.println("Unioning 125 and 126");
        graph3.union(125,126);
        System.out.println("find(125): " + graph3.find(125) + " == find(126): " + graph3.find(126) +"\nBoth equal 125 so the red team wins!");
    }

}
