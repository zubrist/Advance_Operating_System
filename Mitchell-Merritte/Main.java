import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



 class Node {
    int id, u, v; // Node id, u - public label , and v - private label
    Node pre = null; // Predecessor node in probe message path
    Node post = null; // Successor node in probe message path

    // Constructor for Node class
    Node(int id, int u, int v) {
        this.id = id; // Set id field to given id parameter

        // Set u and v fields to given  parameter
        this.u = u;
        this.v = v; 
    }

    @Override
    public String toString() {
        return "[Node " + id + ": " + u + " / " + v + "]";
    }
}


class InputProcessing {
    static int Nodes; // Number of nodes

    static ArrayList<Node> input() {
        ArrayList<Node> array = new ArrayList<Node>();
        try {
            File f = new File("input.txt"); 
            try (Scanner sc1 = new Scanner(f)) { 
                Nodes = Integer.parseInt(sc1.nextLine()); // first line of input file is number of nodes
                
               // Create a Node object for each node and add it to the array list
                for (int i = 0; i < Nodes; i++) {
                    array.add(new Node(i, i + 1, i + 1));
                    System.out.println(array.get(i) + " added successfully!");
                }

                // // Example nodes 
                // array.add(new Node(0,13,11));
                //  System.out.println(array.get(0) + " added successfully!");
                // array.add(new Node(1,7,4));
                //  System.out.println(array.get(1) + " added successfully!");
                // array.add(new Node(2,5,3));
                //  System.out.println(array.get(2) + " added successfully!");
                // array.add(new Node(3,3,6));
                //  System.out.println(array.get(3) + " added successfully!");
                // array.add(new Node(4,19,6));
                //  System.out.println(array.get(4) + " added successfully!");

                // Read the remaining lines of the input file as requests
                while (sc1.hasNextLine() == true) {
                    String[] al = sc1.nextLine().split(" ");
                    try {
                        // Set the post field of the first node to be the second node
                        array.get(Integer.parseInt(al[0])).post = array.get(Integer.parseInt(al[1]));
                        // Set the pre field of the second node to be the first node
                        array.get(Integer.parseInt(al[1])).pre = array.get(Integer.parseInt(al[0]));
                       
                        System.out.println("\n\nNode " + al[0] + " is requesting to Node " + al[1]);
                        // Call the afterRequest method of the Main class to simulate the sending of a probe message
                        Main.afterRequest(array.get(Integer.parseInt(al[0])));

                        // Print the current state of all nodes
                        for (Node i : array) {
                            System.out.println(i);
                        }

                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return array;
    }

}
public class Main {
   
    public static void afterRequest(Node n) {
        // If n has a successor node
        if (n.post != null) {

            // Update n's u value to be the maximum of its current u value and its successor's u value, plus 1
            n.u = Math.max(n.u, n.post.u) + 1;  // Blocked COndition

            // Set n's private  value to be equal to its public value
            n.v = n.u;
        }

        
        Node m = n;
        

        while (true) {
            // If m has a predecessor node
            if (m.pre != null) {
                // If m's public value is greater than its predecessor's public  value
                if (m.u > m.pre.u) {
                    // Set m's predecessor's public value to be equal to m's public value
                    m.pre.u = m.u;  // Tansmit Codition 
                
                    m = m.pre;
                }
                // Else if m's public value is less than its predecessor's public value
                else if (m.u < m.pre.u) {
                    
                    break;
                }
                // Else if m's public value is equal to its predecessor's public value 
                //and public value is equal to private value
                else if (m.u == m.pre.u && m.u == m.pre.v) {
                    // Deadlock Condition
                    
                    System.out.println("Deadlock detected between :: " + m  +" and " + m.pre);
                    
                    break;
                }
            }
            // Else if m does not have a predecessor node
            // When all the nodes are covered 
            else {
                
                break;
            }
            //System.out.println("No deadlock");
        }
    }

    public static void main(String[] args) {
        
        InputProcessing.input();
    }
}