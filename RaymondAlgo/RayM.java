//import java.util.Scanner;
//import java.util.Queue;
//import java.util.LinkedList;
//import java.io.FileWriter;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//class ANode {
//
//    private int ID;                                 // unique id of node
//    private boolean USING;                          // true if using critical section
//    private boolean ASKED;                          // true is request is sent
//    private ANode HOLDER;                            // next node to reach TOKEN
//    private Queue<ANode> REQUEST_Q;                  // queue to store node requests
//    private Queue<Integer> REQUEST_Q1;
// 
//          
//
//    public ANode(int id, ANode current_dir) {
//        ID = id;
//        USING = false;
//        ASKED = false;
//        if (current_dir == null) {
//            HOLDER = this; // root node is its own holder
//        } else {
//            HOLDER = current_dir;
//        }
//        REQUEST_Q = new LinkedList<>();
//        REQUEST_Q1 = new LinkedList<>();
//        
//    }
//    public int getID() {
//        return ID;
//    }
//
//    private void ASSIGN_PRIVILEGE() {
//        if (HOLDER == this && !USING && !REQUEST_Q.isEmpty()) {
//            HOLDER = REQUEST_Q.remove();
//            REQUEST_Q1.add(HOLDER.ID);
//            ASKED = false;
//            if (HOLDER == this) {
//                USING = true;
//                // start executing its critical section
//                
//                    System.out.println("Node " + ID + " has entered critical section\n");
//                    
//                    execute();
//                    HANDLE_EVENT("exit critical section", null);
//            }
//             else {
//                
//                    System.out.println("Node " + ID + " passed the privilege to Node " + HOLDER.ID + "\n");
//                   
//                    HOLDER.HANDLE_EVENT("privilege", null);
//                } 
//            }
//        }
//    
//    
//    private void MAKE_REQUEST() {
//        if (HOLDER != this && !REQUEST_Q.isEmpty() && !ASKED) {
//            ASKED = true;
//            HOLDER.HANDLE_EVENT("request", this);
//        }
//    }
//    
//    private void execute() {
//        
//            System.out.println("Node " + ID + " has executed\n");
//            
//        
//    }
// 
//    public void HANDLE_EVENT(String message, ANode aNode) {
//        if (message.equals("enter critical section")) {
//            REQUEST_Q.add(this);
//            //REQUEST_Q1.add(this);
//            ASSIGN_PRIVILEGE();
//            MAKE_REQUEST();
//        }
//        if (message.equals("request")) {
//            System.out.println("Node " + ID + " received request from Node " + aNode.ID + "\n");
//            REQUEST_Q.add(aNode);
//            //REQUEST_Q1.add(aNode);
//            ASSIGN_PRIVILEGE();
//            MAKE_REQUEST();
//        }
//        if (message.equals("privilege")) {
//            HOLDER = this;
//            ASSIGN_PRIVILEGE();
//            MAKE_REQUEST();
//        }
//        if (message.equals("exit critical section")) {
//           
//                System.out.println("Node " + ID + " exits critical section\n");
//                
//            
//            USING = false;
//            ASSIGN_PRIVILEGE();
//            MAKE_REQUEST();
//        }
//    }
//    public Queue<ANode> getRequestQueue() {
//        return REQUEST_Q;
//    }
//    public Queue<Integer> getRequestQueue1() {
//        return REQUEST_Q1;
//    }
//}
//    
//
//
//public class RayM {
//
// static final int NUM_NODES = 5;
//
//public static void main(String[] args) {
//
//    // create nodes
//    ANode[] nodes = new ANode[NUM_NODES];
//    for (int i = 0; i < NUM_NODES; i++) {
//        nodes[i] = new ANode(i, nodes[0]);
//    }
//
//    // create tree structure
//    nodes[0].getRequestQueue().add(nodes[1]);
//    nodes[0].getRequestQueue().add(nodes[2]);
//    nodes[1].getRequestQueue().add(nodes[3]);
//    nodes[1].getRequestQueue().add(nodes[4]);
//
//    // simulate requests
//    nodes[0].HANDLE_EVENT("enter critical section", null); // node 0 is already in critical section
//    nodes[1].HANDLE_EVENT("enter critical section", null); // node 1 requests critical section
//    nodes[3].HANDLE_EVENT("enter critical section", null); // node 3 requests critical section
//
//    // print queue status
//    for (int i = 0; i < NUM_NODES; i++) {
//        Queue<Integer> queue = nodes[i].getRequestQueue1();
//        System.out.print("Node " + i + " queue: ");
//        for (Integer node : queue) {
//            System.out.print(node + " ");
//        }
//        System.out.println();
//    }
//}
//}