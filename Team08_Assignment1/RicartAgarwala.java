

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RicartAgarwala {
    private final int numNodes;
    private final AtomicInteger[] requestTimestamps;
    private final AtomicBoolean[] replyDeferred;
    private final AtomicInteger[] replyCounts;
    private final AtomicInteger localTimestamp;

    public RicartAgarwala(int numNodes) {
        this.numNodes = numNodes;
        this.requestTimestamps = new AtomicInteger[numNodes];
        this.replyDeferred = new AtomicBoolean[numNodes];
        this.replyCounts = new AtomicInteger[numNodes];
        this.localTimestamp = new AtomicInteger(0);

        for (int i = 0; i < numNodes; i++) {
            requestTimestamps[i] = new AtomicInteger(-1);
            replyDeferred[i] = new AtomicBoolean(false);
            replyCounts[i] = new AtomicInteger(0);
        }
    }
    public AtomicInteger[] getReplyCounts() {
    return replyCounts;
}
    

    public void requestCriticalSection(int nodeId) {
        localTimestamp.incrementAndGet();
        requestTimestamps[nodeId].set(localTimestamp.get());
        

        for (int i = 0; i < numNodes; i++) {
            if (i != nodeId) {
            	 System.out.println("The node : "+nodeId +" at Timestamp: " +  localTimestamp.get()+ " is Requesting to: "+i);
                // Send request message to other nodes
                receiveRequest(nodeId, i);
               
            }
        }
        int re=replyCounts[nodeId].get();
        while ( re< numNodes - 1) {
            // Wait for replies from all other nodes
        	
        }
    }

    public void releaseCriticalSection(int nodeId) {
        replyCounts[nodeId].set(0);
        requestTimestamps[nodeId].set(-1);

        for (int i = 0; i < numNodes; i++) {
            if (replyDeferred[i].get()) {
                replyDeferred[i].set(false);
                // Send reply message to deferred nodes
                receiveReply(nodeId, i);
            }
        }
    }

    private void receiveRequest(int senderId, int receiverId) {
        if (requestTimestamps[receiverId].get() == -1 ||
            requestTimestamps[senderId].get() < requestTimestamps[receiverId].get() ||
            (requestTimestamps[senderId].get() == requestTimestamps[receiverId].get() && senderId < receiverId)) {
            // Send reply message
            receiveReply(receiverId, senderId);
            System.out.println("Node "+receiverId + " sends reply to Node "+ senderId  +"\n");
        } else {
            replyDeferred[senderId].set(true);
        }
    }

    private void receiveReply(int senderId, int receiverId) {
        replyCounts[receiverId].incrementAndGet();
    }
     public static void main(String[] args) throws FileNotFoundException {
        int numNodes;
        int numRequests;
        String data; 
        // Reading a file
    FileReader fp = new FileReader("input.txt");
    Scanner sc=new Scanner(fp);
  
    data=sc.nextLine();
  //Reading number of edge and vertices
        numNodes=Integer.parseInt(data.substring(0,1));
        numRequests=Integer.parseInt(data.substring(2,3));


        RicartAgarwala ra = new RicartAgarwala(numNodes);

        for (int i = 0; i < numRequests; i++) {
            int nodeId = i % numNodes;
            System.out.println("Node " + nodeId + " is requesting the critical section.\n");
            ra.requestCriticalSection(nodeId);
            System.out.println("Node " + nodeId + " has entered the critical section (SUCCESS).");

//            // Print the status of the lists
//            System.out.println("Status of lists:");
//            for (int j = 0; j < numNodes; j++) {
//                System.out.println("Node " + j + ": " + Arrays.toString(ra.getReplyCounts()));
//            }

            ra.releaseCriticalSection(nodeId);
            System.out.println("Node " + nodeId + " has released the critical section.\n");
        }
    }
}