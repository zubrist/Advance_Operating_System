
import java.util.Queue;
import java.util.LinkedList;




class ANode {

    private int ID;                                 // unique id of node
    private boolean USING;                          // true if using critical section
    private boolean ASKED;                          // true is request is sent
    private ANode HOLDER;                            // next node to reach TOKEN
    private Queue<ANode> REQUEST_Q;                  // queue to store node requests
    private Queue<Integer> REQUEST_Q1;                 // queue to store nodes id
    

    public ANode(int id, ANode current_Q) {
      ID = id;
      USING = false;
      ASKED = false;
      if (current_Q == null) {
            HOLDER = this; // root node is its own holder
        } else {
            HOLDER = current_Q;
        }
      REQUEST_Q = new LinkedList<>();
      REQUEST_Q1 = new LinkedList<>();

        
    }
    /**
     * Assigns the privilege to the next node in the queue.
     */

    private void ASSIGN_PRIVILEGE() {
        if (HOLDER == this && !USING && !REQUEST_Q.isEmpty()) {
            HOLDER = REQUEST_Q.remove();
            ASKED = false;
            if (HOLDER == this) {
                USING = true;
                // start executing its critical section
                
                    System.out.println("Node " + ID + " has entered critical section\n");
                    
                    execute();
                    HANDLE_EVENT("exit critical section", null);
            }
             else {
                
                    System.out.println("Node " + ID + " passed the privilege to Node " + HOLDER.ID + "\n");
                    Queue<Integer> queue = this.getRequestQueue1();
        
        
                    System.out.println("Current request queue of Node " + ID + ": " );
                    for (Integer node : queue) {
                    System.out.print(node + " ");
                    }
                    System.out.println();
                    HOLDER.HANDLE_EVENT("privilege", null);
                } 
            }
        }
    
    /**
     * Makes a request to the holder node.
     */
    
    private void MAKE_REQUEST() {
        if (HOLDER != this && !REQUEST_Q.isEmpty() && !ASKED) {
            ASKED = true;
            System.out.println(" Node "+ ID + " send a request");
            HOLDER.HANDLE_EVENT("request", this);
        }
    }
    
    /**
     * Executes the critical section of the node.
     */
    
    private void execute() {
        
            System.out.println("Node " + ID + " has executed\n");
         //   System.out.println("Current request queue of Node " + ID + ": " + getRequestQueue());
        
    }
    
    /**
     * Handles the events based on the received message.
     * @param message The message indicating the event
     * @param aNode The node associated with the event
     */
 
    public void HANDLE_EVENT(String message, ANode aNode) {
        if (message.equals("enter critical section")) {
            REQUEST_Q.add(this);
            
            ASSIGN_PRIVILEGE();
            MAKE_REQUEST();
        }
        if (message.equals("request")) {
            
                System.out.println("\n\nNode " + ID + " received request from Node " + aNode.ID + "\n");
                System.out.println("Current request queue of Node " + ID + ": " + getRequestQueue());
                 
            REQUEST_Q.add(aNode);
           
            ASSIGN_PRIVILEGE();
            MAKE_REQUEST();
        }
        if (message.equals("privilege")) {
            HOLDER = this;
            ASSIGN_PRIVILEGE();
            MAKE_REQUEST();
        }
        if (message.equals("exit critical section")) {
           
                System.out.println("Node " + ID + " exits critical section\n");
                System.out.println("Current request queue of Node " + ID + ": " + getRequestQueue());
                
            
            USING = false;
            ASSIGN_PRIVILEGE();
            MAKE_REQUEST();
        }
    }
    /**
     * Returns the request queue of the node.
     * @return The request queue
     */
    public Queue<ANode> getRequestQueue() {
        return REQUEST_Q;
}
    /**
     * Returns the request ID queue of the node.
     * @return The request ID queue
     */
    public Queue<Integer> getRequestQueue1() {
        return REQUEST_Q1; 
}
}
public class Ray{
    public static void main(String[] args) {
        // Create a tree with nodes
        ANode node1 = new ANode(1, null);
        ANode node2 = new ANode(2, node1);
        ANode node3 = new ANode(3, node1);
        ANode node4 = new ANode(4, node2);
        ANode node5 = new ANode(5, node2);

        // Node 1 is already in the critical section
        node1.HANDLE_EVENT("enter critical section", null);
        

        // Generate 3 requests
        node2.HANDLE_EVENT("enter critical section", null);
       
        node3.HANDLE_EVENT("enter critical section", null);
        node4.HANDLE_EVENT("enter critical section", null);
    }
}
