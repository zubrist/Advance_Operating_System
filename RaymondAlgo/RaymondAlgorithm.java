//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//class Node {
//    private final int id;
//    private boolean hasToken; // asked
//    private boolean inCriticalSection; // using
//    private List<Integer> requestQueue; // queue to dtore node request
//    private Node predecessor; // holder
//
//    private FileWriter out_file, log_file;          // output and log files
//    
//    //Constructor
//    public Node(int id) {
//        this.id = id;
//        this.hasToken = false;
//        this.inCriticalSection = false;
//        this.requestQueue = new ArrayList<>();
//        this.predecessor = null;
//        
//        try {
//          out_file = new FileWriter("output.txt");
//          log_file = new FileWriter("log.txt");
//      } catch (IOException e) {
//          e.printStackTrace();
//      }try {
//          out_file = new FileWriter("output.txt");
//          log_file = new FileWriter("log.txt");
//      } catch (IOException e) {
//          e.printStackTrace();
//      }
//    }
//    
//   
//
//    public void setPredecessor(Node predecessor) {
//        this.predecessor = predecessor;
//    }
//
//    public void requestCriticalSection() {
//        if (predecessor != null) {
//            // Add current node's ID to the request queue of the predecessor
//            predecessor.addToRequestQueue(id);
//        }
//
//        // Check if the current node has received the token
//        if (hasToken) {
//            enterCriticalSection();
//        }
//    }
//
//    public void receiveToken() {
//        hasToken = true;
//        enterCriticalSection();
//    }
//
//    public void enterCriticalSection() {
//        inCriticalSection = true;
//        System.out.println("Node " + id + " enters the critical section.");
//    }
//
//    public void releaseCriticalSection() {
//        inCriticalSection = false;
//        hasToken = false;
//        System.out.println("Node " + id + " releases the critical section.");
//
//        // Pass the token to the next node
//        if (requestQueue.size() > 0) {
//            int nextNodeId = requestQueue.get(0);
//            requestQueue.remove(0);
//            System.out.println("Token is passed from Node " + id + " to Node " + nextNodeId);
//            getNodeById(nextNodeId).receiveToken();
//        }
//    }
//
//    public void addToRequestQueue(int nodeId) {
//        requestQueue.add(nodeId);
//    }
//
//    public static Node getNodeById(int nodeId) {
//        // Create and return the Node object based on the node ID
//        return new Node(nodeId);
//    }
//
//    public List<Integer> getRequestQueue() {
//    	System.out.println(requestQueue);
//        return requestQueue;
//    }
//}
//
//public class RaymondAlgorithm {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter the number of nodes: ");
//        int numNodes = scanner.nextInt();
//        Node[] nodes = new Node[numNodes];
//
//        // Initialize the nodes
//        for (int i = 0; i < numNodes; i++) {
//            nodes[i] = new Node(i);
//            nodes[i].setPredecessor(Node.getNodeById((i - 1 + numNodes) % numNodes));
//        }
//
//        System.out.print("Enter the number of requests: ");
//        int numRequests = scanner.nextInt();
//
//        // Perform requests assuming the current process is in its critical section
//        for (int i = 0; i < numRequests; i++) {
//            int currentNodeId = i % numNodes;
//            System.out.println("Current Process: Node " + currentNodeId);
//
//            // Request critical section
//            nodes[currentNodeId].requestCriticalSection();
//
//            // Show the changes in each queue after each request
//            for (int j = 0; j < numNodes; j++) {
//                System.out.println("Node " + j + " Request Queue: " + nodes[j].getRequestQueue());
//            }
//
//            // Assume the current process is in its critical section
//            nodes[currentNodeId].enterCriticalSection();
//
//            // Release critical section
//            nodes[currentNodeId].releaseCriticalSection();
//
//            // Show the changes in each queue after releasing the critical section
//            for (int j = 0; j < numNodes; j++) {
//                System.out.println("Node " + j + " Request Queue: " + nodes[j].getRequestQueue() );
//                		System.out.println();
//            }
//        }
//
//        scanner.close();
//    }
//}