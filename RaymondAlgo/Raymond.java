//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.Queue;
//
//class Raymond {
//    private int ID;                     // Unique id of the node
//    private boolean USING;              // True if using critical section
//    private boolean ASKED;              // True if request is sent
//    private Raymond HOLDER;             // Next node to reach TOKEN
//    private Queue<Raymond> REQUEST_Q;    // Queue to store node requests
//
//    // Constructor
//    public Raymond(int id, Raymond currentDir) {
//        ID = id;
//        USING = false;
//        ASKED = false;
//        HOLDER = currentDir;
//        REQUEST_Q = new LinkedList<>();
//    }
//
//    // Handles the use of TOKEN
//    private void assignPrivilege() {
//        if (HOLDER == this && !USING && !REQUEST_Q.isEmpty()) {
//            HOLDER = REQUEST_Q.poll();
//            ASKED = false;
//            if (HOLDER == this) {
//                USING = true;
//                // Start executing its critical section
//                System.out.println("Node " + ID + " has entered critical section");
//                execute();
//                handleEvent("exit critical section", null);
//            } else {
//                System.out.println("Node " + ID + " passed the privilege to Node " + HOLDER.getID());
//                HOLDER.handleEvent("privilege", null);
//            }
//        }
//    }
//
//    // Make a request for the resource to the HOLDER
//    private void makeRequest() {
//        if (HOLDER != this && !REQUEST_Q.isEmpty() && !ASKED) {
//            ASKED = true;
//            HOLDER.handleEvent("request", this);
//        }
//    }
//
//    // Critical Section Code
//    private void execute() {
//        System.out.println("Node " + ID + " has executed");
//    }
//
//    // Message handling mechanism
//    public void handleEvent(String message, Raymond sender) {
//        if (message.equals("enter critical section")) {
//            REQUEST_Q.add(this);
//            assignPrivilege();
//            makeRequest();
//        }
//        if (message.equals("request")) {
//            System.out.println("Node " + ID + " received request from Node " + sender.getID());
//            REQUEST_Q.add(sender);
//            assignPrivilege();
//            makeRequest();
//        }
//        if (message.equals("privilege")) {
//            HOLDER = this;
//            assignPrivilege();
//            makeRequest();
//        }
//        if (message.equals("exit critical section")) {
//            System.out.println("Node " + ID + " exits critical section");
//            USING = false;
//            assignPrivilege();
//            makeRequest();
//        }
//    }
//
//    // Getter for ID
//    public int getID() {
//        return ID;
//    }
//
//    public static void main(String[] args) throws IOException {
//        // Input format validation
//        if (args.length != 2) {
//            System.out.println("Usage: java Raymond <input.txt> <nodeCount>");
//            return;
//        }
//
//        // Local variables
//        int i;
//
//        FileWriter outFile = new FileWriter("output.txt");
//        FileWriter logFile = new FileWriter("log.txt");
//
//        // Creating Nodes
//        int nodeCount = Integer.parseInt(args[1]);
//        Raymond[] nodeArray = new Raymond[nodeCount];
//
//        // Create tree structure from nodes
//        nodeArray[0] = new Raymond(0, null);
//        nodeArray[0].handleEvent("privilege", null);
//        for (i = 1; i < nodeCount - 1; i += 2) {
//            nodeArray[i] = new Raymond(i, nodeArray[i / 2]);
//            nodeArray[i + 1] = new Raymond(i + 1, nodeArray[i / 2]);
//        }
//        if ((nodeCount & 1) == 0)
//            nodeArray[i] = new Raymond(i, nodeArray[i / 2]);
//
//        // Input processing from text file
//        String inputFile = args[0];
//        java.util.Scanner file = new java.util.Scanner(new java.io.File(inputFile));
//
//        while (file.hasNextInt()) {
//            int linebuffer = file.nextInt();
//            // Input checking and error handling
//            if (linebuffer >= 0 && linebuffer < nodeCount) {
//                logFile.write("Node " + linebuffer + " wants to enter critical section\n");
//                nodeArray[linebuffer].handleEvent("enter critical section", null);
//            }
//        }
//        file.close();
//        outFile.close();
//        logFile.close();
//    }
//}
