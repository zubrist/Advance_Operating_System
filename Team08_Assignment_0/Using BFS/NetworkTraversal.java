import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkTraversal {

    public static void main(String[] args) {
        // Read graph from CSV file
        ArrayList<ArrayList<Integer>> graph = readCSVFile("network2.csv"); //network.csv
        /* In the file each of the lines shows the one directional connections between nodes*/
        // Test all nodes in the graph
        for (int i = 0; i < graph.size(); i++) {
            boolean canTraverseAll = canTraverseAllNodes(graph, i);
            if (canTraverseAll) {
                System.out.printf("\n Yes, node %d can traverse all nodes in the network.%n", i+1);
            } else {
                System.out.printf("\n No, node %d cannot traverse all nodes in the network.%n", i+1);
            }
        }
    }

/**
Reads a CSV file and returns a graph as an adjacency list
@param fileName the name of the CSV file to be read
@return the graph as an adjacency list represented as an ArrayList of ArrayLists of integers
*/
    public static ArrayList<ArrayList<Integer>> readCSVFile(String fileName) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line into two integers representing an edge
                String[] parts = line.split(",");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                // Add edge to graph
                while (u > graph.size() || v > graph.size()) {
                    graph.add(new ArrayList<Integer>());
                }
                /*directed edge from node with index u-1 to node with index v-1 in the adjacency list representation of the graph. */
                graph.get(u - 1).add(v - 1);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    
    public static boolean canTraverseAllNodes(ArrayList<ArrayList<Integer>> graph, int startNode) {
        // Create a boolean array to keep track of visited nodes
        boolean[] visited = new boolean[graph.size()];

        // Create a queue to hold nodes to visit
        Queue<Integer> queue = new LinkedList<Integer>();
    
        // Mark the start node as visited and add it to the queue
        visited[startNode] = true;
        queue.add(startNode);
    // Traverse the graph using Breadth First Search
        while (!queue.isEmpty()) {
            // Get the next node from the queue
            int node = queue.poll();
            // Visit each neighbor of the current node
            for (int neighbor : graph.get(node)) {
                // If the neighbor has not been visited yet, mark it as visited and add it to the queue
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    
        // Check if all nodes were visited
        for (boolean v : visited) {
            if (!v) {
                // If a node was not visited, return false
                return false;
            }
        }
    // If all nodes were visited, return true
        return true;
    }
}
