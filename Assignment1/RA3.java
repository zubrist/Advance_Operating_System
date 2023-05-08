import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class RA3 {
    private  int numNodes;
    private  ArrayList<AtomicBoolean> replyDeferred;
    private  ArrayList<ArrayList<AtomicBoolean>> replyStatus;
    private  ArrayList<Integer> requestTimestamps;
    private  AtomicBoolean inCriticalSection;

    public RA3(int numNodes, ArrayList<Integer> requestTimestamps) {
        this.numNodes = numNodes;
        this.requestTimestamps = requestTimestamps;
        this.inCriticalSection = new AtomicBoolean(false);

        this.replyDeferred = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            this.replyDeferred.add(new AtomicBoolean(false));
        }

        this.replyStatus = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            ArrayList<AtomicBoolean> status = new ArrayList<>();
            for (int j = 0; j < numNodes; j++) {
                status.add(new AtomicBoolean(false));
            }
            this.replyStatus.add(status);
        }
    }

    public void requestCriticalSection(int nodeId) {
        int timestamp = requestTimestamps.get(nodeId);
        ArrayList<AtomicBoolean> deferredList = new ArrayList<>();

        for (int i = 0; i < numNodes; i++) {
            if (i != nodeId) {
                // Send request message to other nodes
                if (requestTimestamps.get(i) > timestamp) {
                    deferredList.add(replyDeferred.get(i));
                } else {
                    replyStatus.get(nodeId).set(i, new AtomicBoolean(true));
                    receiveReply(nodeId, i);
                }
            }
        }

        while (replyStatus.get(nodeId).contains(new AtomicBoolean(false))) {
            // Wait for replies from all other nodes
            deferredList.stream().filter(b -> b.getAndSet(true)).forEach(b -> receiveReply(nodeId, deferredList.indexOf(b)));

        }

        inCriticalSection.set(true);
    }

    public void releaseCriticalSection(int nodeId) {
        inCriticalSection.set(false);

        for (int i = 0; i < numNodes; i++) {
            if (replyDeferred.get(i).getAndSet(false)) {
                // Send reply message to deferred nodes
                receiveReply(nodeId, i);
            }
            replyStatus.get(nodeId).set(i, new AtomicBoolean(false));
        }
    }

    private void receiveReply(int senderId, int receiverId) {
        replyStatus.get(receiverId).set(senderId, new AtomicBoolean(true));
        if (inCriticalSection.get() && replyStatus.get(receiverId).stream().allMatch(AtomicBoolean::get)) {
            // Print the status of the lists
            System.out.println("Node " + receiverId + " has entered the critical section (SUCCESS).");
            System.out.println("Status of lists:");
            for (int i = 0; i < numNodes; i++) {
                System.out.println("Node " + i + ": " + replyStatus.get(i));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
    
        int numNodes = scanner.nextInt();
        int numRequests = scanner.nextInt();
    
        ArrayList<Integer> requestTimestamps = new ArrayList<>();
        for (int i = 0; i < numRequests; i++) {
            int timestamp = scanner.nextInt();
            int nodeId = scanner.nextInt();
            requestTimestamps.add(timestamp);
        }
    
        RA3 ra = new RA3(numNodes, requestTimestamps);
    
        for (int i = 0; i < numNodes; i++) {
            final int id = i;
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        ra.requestCriticalSection(id);
                        Thread.sleep(1000);
                        ra.releaseCriticalSection(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    
        scanner.close();
    }
}