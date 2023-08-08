import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class InitiatorFinder {

    // A method that takes an adjacency matrix of a directed graph and returns a list of possible initiator nodes
    public static List<Integer> findInitiators(int[][] matrix) {
        List<Integer> initiators = new ArrayList<>(); // A list to store the possible initiators
        int n = matrix.length; // The number of nodes in the graph
        for (int i = 0; i < n; i++) { // For each node i
            List<Integer> nodesCovered = new ArrayList<>(); // A list to store the nodes covered so far
            nodesCovered.add(i); // Add node i to nodesCovered
            int count = 1; // A variable to store the number of nodes covered so far
            boolean done = false; // A variable to indicate whether the algorithm has finished or not
            while (!done) { // While done is false
                List<Integer> nextNodes = new ArrayList<>(); // A list to store the next nodes to be covered
                for (int j : nodesCovered) { // For each node j in nodesCovered
                    for (int k = 0; k < n; k++) { // For each node k in the graph
                        if (matrix[j][k] == 1 && !nodesCovered.contains(k) && !nextNodes.contains(k)) { // If there is an edge from j to k and k is not already covered
                            nextNodes.add(k); // Add k to nextNodes
                            count++; // Increment count by 1
                        }
                    }
                }
                if (nextNodes.isEmpty()) { // If nextNodes is empty
                    done = true; // Set done to true
                    System.out.println("Node " + i + " is not a possible initiator because it cannot reach all other nodes.");
                } else if (count == n) { // If count is equal to n
                    done = true; // Set done to true
                    initiators.add(i); // Node i is a possible initiator because it can reach all other nodes
                    System.out.println("Node " + i + " is a possible initiator because it can reach all other nodes.");
                } else { // Otherwise
                    nodesCovered.addAll(nextNodes); // Append nextNodes to nodesCovered
                }
            }
        }
        return initiators; // Return the list of possible initiators
    }

    public static void printPaths(int[][] matrix, int source) {
        int n = matrix.length;
        boolean[] visited = new boolean[n];
        int[] distance = new int[n];
        int[] predecessor = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            predecessor[i] = -1;
        }
        distance[source] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (matrix[u][v] == 1 && !visited[v]) {
                    if (distance[v] > distance[u] + 1) {
                        distance[v] = distance[u] + 1;
                        predecessor[v] = u;
                    }
                    queue.add(v);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (i != source) {
                System.out.print("Path from node " + source + " to node " + i + ": ");
                printPath(predecessor, source, i);
                System.out.println();
            }
        }
    }

    public static void printPath(int[] predecessor, int source, int destination) {
        if (destination == source) {
            System.out.print(source);
        } else if (predecessor[destination] == -1) {
            System.out.print("No path");
        } else {
            printPath(predecessor, source, predecessor[destination]);
            System.out.print(" -> " + destination);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        List<Integer> initiators = findInitiators(matrix);
        for (int initiator : initiators) {
            printPaths(matrix, initiator);
        }
    }
}
