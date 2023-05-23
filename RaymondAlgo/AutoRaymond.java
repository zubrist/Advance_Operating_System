// import java.util.ArrayList;
// import java.util.List;

// class Node {
//     private int id;
//     private boolean hasToken;
//     private List<Node> children;
    
//     public Node(int id) {
//         this.id = id;
//         this.hasToken = false;
//         this.children = new ArrayList<>();
//     }
    
//     public int getId() {
//         return id;
//     }
    
//     public boolean hasToken() {
//         return hasToken;
//     }
    
//     public void setToken(boolean hasToken) {
//         this.hasToken = hasToken;
//     }
    
//     public List<Node> getChildren() {
//         return children;
//     }
    
//     public void addChild(Node child) {
//         children.add(child);
//     }
    
//     public void requestCS() {
//         if (hasToken) {
//             // Execute critical section
//             System.out.println("Node " + id + " is executing the critical section.");
//         } else {
//             // Send request to parent
//             System.out.println("Node " + id + " is requesting the critical section.");
//             Node parent = getParent();
//             parent.receiveRequest();
//         }
//     }
    
//     public void receiveRequest() {
//         if (!hasToken) {
//             // Forward the request to the appropriate child
//             Node child = getFirstChild();
//             child.receiveRequest();
//         } else {
//             // Token is with this node, so grant the request
//             System.out.println("Node " + id + " is granting the request for the critical section.");
//             setToken(false);
//         }
//     }
    
//     // private Node getParent() {
//     //     // Assume parent-child relationships are already defined
//     //     // Implement your logic here to find and return the parent node
//     //     return null;
//     // }
//     private Node getParent(Node root) {
//         // Traverse the tree to find the parent node
//         return findParent(root, this);
//     }
    
//     private Node findParent(Node node, Node child) {
//         for (Node n : node.getChildren()) {
//             if (n == child) {
//                 return node;
//             } else {
//                 Node parent = findParent(n, child);
//                 if (parent != null) {
//                     return parent;
//                 }
//             }
//         }
//         return null;
//     }
    
//     private Node getFirstChild() {
//         // Return the first child node
//         if (getChildren().isEmpty()) {
//             return null;
//         } else {
//             return getChildren().get(0);
//         }
//     }
//     // private Node getFirstChild() {
//     //     // Assume parent-child relationships are already defined
//     //     // Implement your logic here to find and return the first child node
//     //     return null;
//     // }
// }

// class Raymond {
//     private Node root;
    
//     public Raymond(Node root) {
//         this.root = root;
//     }
    
//     public void generateRequests() {
//         // Implement your logic here to generate requests from nodes
//         // For example:
//         root.requestCS();
//     }
// }

// public class AutoRaymond {
//     public static void main(String[] args) {
//         // Create nodes and define parent-child relationships
//         Node node1 = new Node(1);
//         Node node2 = new Node(2);
//         Node node3 = new Node(3);
//         Node node4 = new Node(4);
        
//         node1.addChild(node2);
//         node1.addChild(node3);
//         node3.addChild(node4);
        
//         // Create Raymond instance with the root node
//         Raymond raymond = new Raymond(node1);
        
//         // Generate requests
//         raymond.generateRequests();
//     }
// }