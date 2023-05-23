//import java.util.ArrayList;
//import java.util.List;
//
//class Node {
//    private int id;
//    private boolean hasToken;
//    private List<Integer> requestQueue;
//
//    public Node(int id) {
//        this.id = id;
//        this.hasToken = false;
//        this.requestQueue = new ArrayList<>();
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public boolean hasToken() {
//        return hasToken;
//    }
//
//    public void setToken(boolean hasToken) {
//        this.hasToken = hasToken;
//    }
//
//    public List<Integer> getRequestQueue() {
//        return requestQueue;
//    }
//
//    public void addToRequestQueue(int nodeId) {
//        requestQueue.add(nodeId);
//    }
//}
//
//public class RaymondAlgorithm {
//    private static List<Node> nodes;
//
//    public static void main(String[] args) {
//        // Create 4 nodes
//        nodes = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            Node node = new Node(i);
//            nodes.add(node);
//        }
//
//        // Simulate requests
//        nodes.get(0).addToRequestQueue(1); // Node 1 requests
//        nodes.get(1).addToRequestQueue(2); // Node 2 requests
//        nodes.get(2).addToRequestQueue(3); // Node 3 requests
//        nodes.get(3).addToRequestQueue(0); // Node 0 requests
//
//        // Start the algorithm
//        requestToken();
//
//        // Display the changes in each queue at each stage
//        for (Node node : nodes) {
//            System.out.println("Node " + node.getId() + " request queue: " + node.getRequestQueue());
//        }
//    }
//
//    private static void requestToken() {
//        boolean[] inQueue = new boolean[nodes.size()]; // To track if a node is already in the queue
//
//        for (Node node : nodes) {
//            if (node.hasToken()) {
//                continue; // Skip nodes that already have the token
//            }
//
//            int nodeId = node.getId();
//            List<Integer> requestQueue = node.getRequestQueue();
//
//            // Process the request queue
//            for (int i = 0; i < requestQueue.size(); i++) {
//                int requestingNode = requestQueue.get(i);
//
//                // Add the requesting node to its own request queue
//                nodes.get(requestingNode).addToRequestQueue(requestingNode);
//
//                // Add the requesting node to the current node's request queue if it's not already present
//                if (!inQueue[requestingNode]) {
//                    node.addToRequestQueue(requestingNode);
//                    inQueue[requestingNode] = true;
//                }
//            }
//
//            // Check if the current node is the next one to receive the token
//            if (nodeId == requestQueue.get(0)) {
//                node.setToken(true); // Grant the token to the current node
//                node.getRequestQueue().remove(0); // Remove the node from its own request queue
//                inQueue[nodeId] = false; // Mark the current node as not in the queue
//            }
//        }
//    }
//}