
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class RA {
    static int N = 5; // number of nodes
    static int M = 10; // number of requests

    static Node[] nodes = new Node[N];

    public static void main(String[] args) {
        RA ra = new RA();
        for (int i = 0; i < N; i++) {
            nodes[i] = ra.new Node(i);
        }
        for (int i = 0; i < M; i++) {
            int nodeId = (int) (Math.random() * N);
            nodes[nodeId].requestCS();
            printReqLists();
        }
        
    }


class Node {
    int id;
    int timestamp;
    boolean hasCS;
    ArrayList<Integer> reqList;
    ArrayList<Integer> deferredList;

    public Node(int id) {
        this.id = id;
        this.timestamp = 0;
        this.hasCS = false;
        this.reqList = new ArrayList<Integer>();
        this.deferredList = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            this.reqList.add(0);
        }
    }
    public  void msgReq(int fromId, int toId, int timestamp) {
        nodes[toId].handleReq(fromId, timestamp);
    }

    public  void msgGoAhead(int fromId, int toId, int timestamp) {
        nodes[toId].handleGoAhead(fromId, timestamp);
    }

    public  void msgDefer(int fromId, int toId) {
        // do nothing
    }
    

    public void requestCS() {
        this.timestamp++;
        for (int i = 0; i < N; i++) {
            if (i != this.id) {
                msgReq(this.id, i, this.timestamp);
            }
        }
        boolean canEnterCS = true;
        while (canEnterCS) {
            canEnterCS = false;
            for (int i = 0; i < N; i++) {
                if (i != this.id && this.reqList.get(i) == 0) {
                    canEnterCS = true;
                    break;
                }
            }
        }
        this.hasCS = true;
        System.out.println("Node " + this.id + " entered critical section.");
    }

    public void handleReq(int nodeId, int timestamp) {
        this.timestamp = Math.max(this.timestamp, timestamp) + 1;
        if (this.hasCS || (this.reqList.get(nodeId) != 0 && this.reqList.get(nodeId) < timestamp)) {
            msgDefer(this.id, nodeId);
            deferredList.add(nodeId);
        } else {
            msgGoAhead(this.id, nodeId, this.timestamp);
            reqList.set(nodeId, 1);
        }
    }

    public void handleGoAhead(int nodeId, int timestamp) {
        this.timestamp = Math.max(this.timestamp, timestamp) + 1;
        reqList.set(nodeId, 2);
        if (Collections.frequency(this.reqList, 2) == N - 1) {
            this.hasCS = true;
            System.out.println("Node " + this.id + " entered critical section.");
            for (int i = 0; i < N; i++) {
                if (reqList.get(i) == 1) {
                    msgGoAhead(this.id, i, this.timestamp);
                    reqList.set(i, 2);
                }
            }
        }
    }
}



    // print request lists for all nodes
    public static  void printReqLists() {
        for (int i = 0; i < N; i++) {
            System.out.println("Node " + i + " request list: " + nodes[i].reqList.toString());
        }
    }
   
}