import java.io.*;
import java.util.*;

public class ass0 {

   public static boolean isGoodNode(String startingNode, String filename) {
      Map<String, Boolean> visitedMap = new HashMap<String, Boolean>();
      Queue<String> queue = new LinkedList<String>();
      
      // Read input file and populate graph
      try {
         File file = new File(filename);
         Scanner sc = new Scanner(file);
         while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(" ");
            String node1 = parts[0];
            String node2 = parts[1];
            // Add nodes to visited map
            visitedMap.put(node1, false);
            visitedMap.put(node2, false);
         }
         sc.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

      // BFS traversal of the graph
      queue.add(startingNode);
      visitedMap.put(startingNode, true);
      while (!queue.isEmpty()) {
         String currentNode = queue.poll();
         // Get neighbors of current node
         List<String> neighbors = getNeighbors(currentNode, filename);
         for (String neighbor : neighbors) {
            if (!visitedMap.get(neighbor)) {
               visitedMap.put(neighbor, true);
               queue.add(neighbor);
            }
         }
      }
      // Check if all nodes have been visited
      for (String node : visitedMap.keySet()) {
         if (!visitedMap.get(node)) {
            return false;
         }
      }
      return true;
   }

   // Helper method to get neighbors of a given node
   public static List<String> getNeighbors(String node, String filename) {
      List<String> neighbors = new ArrayList<String>();
      try {
         File file = new File(filename);
         Scanner sc = new Scanner(file);
         while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(" ");
            if (parts[0].equals(node)) {
               neighbors.add(parts[1]);
            } else if (parts[1].equals(node)) {
               neighbors.add(parts[0]);
            }
         }
         sc.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      return neighbors;
   }

   public static void main(String[] args) {
      String startingNode = "A";
      String filename = "Graph.txt";
      boolean result = isGoodNode(startingNode, filename);
      if (result) {
         System.out.println(startingNode + " is a good starting node.");
      } else {
         System.out.println(startingNode + " is not a good starting node.");
      }
   }
}
