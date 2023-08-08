/*
 * This program simulates a token ring network with a given number of nodes.
 * @Author - zubrist
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Node {
    // The ID of the node
    private int id;

    private boolean hasToken; // Whether the node currently has the token private boolean hasToken;
    private Queue<Integer> requestQueue; // The queue of requests for this node private Queue requestQueue;
   
    private Ring ring;

    public Node(int id, Ring ring) {
        this.id = id;
        this.hasToken = false;
        this.requestQueue = new LinkedList<>();
       
        this.ring = ring;
    }

    public int getId() {
        return id;
    }

    public boolean hasToken() {
        return hasToken;
    }

    public void setToken(boolean hasToken) {
        this.hasToken = hasToken;
    }

    public Queue<Integer> getRequestQueue() {
        return requestQueue;
    }

    public void printLocalQueue() {
        System.out.println("Node " + id + " local queue: " + requestQueue);
    }
int nextRequestId;
    public void requestToken() {
        //if get the token
        if (hasToken()) {
            System.out.println("Node " + id + " entered CS.");
            if (!requestQueue.isEmpty()) {
                nextRequestId = requestQueue.poll();
                System.out.println("Node " + id + " `s queue has node " + nextRequestId);
                passToken(nextRequestId);
            }
            passToken((id + 1) % ring.getNumNodes()); // Pass token to the next node in the ring
            

        } else {
            int nextNodeId = (id + 1) % ring.getNumNodes(); // Request token from the next node in the ring
            System.out.println("Node " + id + " is requesting token from node " + nextNodeId);
            Node toNode = ring.getNodes()[nextNodeId];
            if (toNode.hasToken()) {
                passToken(nextNodeId);
                System.out.println("Node " +id + "  passed the token to "+nextNodeId);
            } else {
                toNode.getRequestQueue().add(getId());
                System.out.println("Node "+ id +" added itself into its neighbor's queue");
            }
        }
    }

    private void passToken(int toNodeId) {
        Node toNode = ring.getNodes()[toNodeId];
        if (hasToken()) {
            setToken(false);
            toNode.setToken(true);
            System.out.println("Node " + id + " passed token to node " + toNode.getId());
            System.out.println(toNode.getId() + " has the token  #_#");
            if (!requestQueue.isEmpty()) {
                int nextRequestId = requestQueue.poll();
                toNode.getRequestQueue().addAll(requestQueue); // Add non-empty queue to toNode's queue 
                System.out.println("Node " + toNode.getId() + " send the non-empty queue to " + nextRequestId);
            } else {
                if (toNode.getId() != ring.getPhold()) {
                    toNode.requestToken();
                   
                } else {
                    System.out.println("Token returned to Phold (node " + ring.getPhold() + ").");
                }
            }
        }
    }
}

class Ring {
    private Node[] nodes;
    //  node having Token
    private int phold;
    private Random random;

    public Ring(int numNodes) {
        this.nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i, this);
        }
        this.random = new Random();
        // Randomly selecting a Node
       this.phold = random.nextInt(numNodes);
      
        nodes[phold].setToken(true);
        System.out.println(phold+" has the token #_#");
       
    }

    public void requestToken(int nodeId) {
        Node node = nodes[nodeId];
        node.requestToken();
    }

    public Node[] getNodes() {
        return nodes;
    }

    public int getPhold() {
        return phold;
    }

    public int getNumNodes() {
        return nodes.length;
    }
}

public class Main {
    public static void main(String[] args) {
        Ring ring = new Ring(5);

        ring.requestToken(0);
        ring.requestToken(2);
        ring.requestToken(3);
        ring.requestToken(1);
        ring.requestToken(4);

        for (int i = 0; i <  ring.getNumNodes() ; i++) {
            ring.getNodes()[i].printLocalQueue();
        }
    }
}
