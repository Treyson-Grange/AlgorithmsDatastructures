import java.io.File;
import java.nio.file.attribute.AttributeView;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
//input is describing a graph, first number is how many vertexes there are, next rows follow [node goes to node with weight WEIGHT]
public class Graph {
    int vertexCt;  // Number of vertices in the graph.
    int[][] capacity;  // Adjacency  matrix
    String graphName;  //The file from which the graph was created.
    boolean[] visited;
    int[] pred;
    int[][] residual;
    int maxFlow = 0;
    public Graph() {
        this.vertexCt = 0;
        this.graphName = "";
    }


    public int getVertexCt() {
        return vertexCt;
    }

    public boolean addEdge(int source, int destination, int cap) {
        System.out.println("addEdge " + source + "->" + destination + "(" + cap + ")");
        if (source < 0 || source >= vertexCt) return false;
        if (destination < 0 || destination >= vertexCt) return false;
        capacity[source][destination] = cap;
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nThe Graph " + graphName + " \n");

        for (int i = 0; i < vertexCt; i++) {
            for (int j = 0; j < vertexCt; j++) {
                sb.append(String.format("%5d", capacity[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            pred = new int[vertexCt];
            visited = new boolean[vertexCt];
            capacity = new int[vertexCt][vertexCt];
            residual = capacity;
            for (int i = 0; i < vertexCt; i++) {//simply filling the graph with 0's
                for (int j = 0; j < vertexCt; j++) {
                    capacity[i][j] = 0;
                }
            }
            System.out.println("------> " + "Max Flow Min Cut for: " + filename + " <------");
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                int cap = reader.nextInt();
                if (!addEdge(v1, v2, cap))
                    throw new Exception();
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void fordFulkerson(Graph G, int s, int t) {
        System.out.println();
        maxFlow = 0;
        for(int i = 0; i <capacity.length; i++) {
            for(int j = 0; j <capacity.length;j++) {
                residual[i][j] = capacity[i][j];
            }
        }
        int totalFlow = 0;
        while(hasAugmentingPath(G,s,t)) {
            StringBuilder sb = new StringBuilder();
            double availFlow = Double.POSITIVE_INFINITY;
            for(int v = t; v!= s; v=pred[v]) {
                availFlow = Math.min(availFlow,residual[pred[v]][v]);
            }
            for(int v = t; v!=s; v=pred[v]) {
                residual[pred[v]][v] -= availFlow;
                residual[v][pred[v]] += availFlow;//
                sb.append(v + " ");
            }
            sb.append(0 +" ");
            sb.reverse();

            totalFlow+= availFlow;
            String flowString = String.format("%.0f",availFlow);
            maxFlow += availFlow;
            System.out.print("Found Flow " + flowString + ": ");
            System.out.println(sb);
            //System.out.println(availFlow);
        }
        System.out.println("\nMax Flow "+totalFlow);
        for(int i =0; i < capacity.length;i++) {
            for(int j = 0; j < capacity.length;j++) {
                if(residual[i][j] > 0) {
                    System.out.println("Flow" +j + "->" + i + "(" +residual[i][j] + ")");
                }
            }
        }
        minCut(residual);


    }
    public void minCut(int[][] residual) {
        System.out.println("\n---MIN CUT---");
        for(int i = 0;i<vertexCt;i++) {
            for(int j = 0; j<vertexCt;j++) {
                if(residual[i][j]>0 && visited[j] && !visited[i]) {
                    System.out.println("Edge ("+ j +"," + i+") transports " + residual[i][j]);
                }
            }
        }
        System.out.println();
    }
    public boolean hasAugmentingPath(Graph G, int s, int t) {
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(s);
        Arrays.fill(visited,false);
        visited[s] = true;
        while(!queue.isEmpty() && !visited[t]) {
            int v = queue.remove(0);
            for (int w = 0; w < residual.length;w++) {
                if(residual[v][w] > 0 && !visited[w]){
                    pred[w] = v;
                    visited[w] = true;
                    queue.add(w);
                    if(w==t) {return true;}
                }
            }
        }
        return false;
    }




    public static void main(String[] args) {


        Graph graph0 = new Graph();
        graph0.makeGraph("src/demands1.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
        graph0.makeGraph("src/demands2.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
        graph0.makeGraph("src/demands3.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
        graph0.makeGraph("src/demands4.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
        graph0.makeGraph("src/demands5.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
        graph0.makeGraph("src/demands6.txt");
        System.out.println(graph0.toString());
        graph0.fordFulkerson(graph0,0, graph0.vertexCt-1);
    }
}