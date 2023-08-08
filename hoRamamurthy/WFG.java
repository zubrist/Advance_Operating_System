public class WFG {

    private static int numProcesses;
    private int numResources;
    private int[][] allocation;
    private int[][] request;
     private static int[][] waitfor;

    public WFG(int numProcesses, int numResources) {
        WFG.numProcesses = numProcesses;
        this.numResources = numResources;
        this.allocation = new int[numProcesses][numResources];
        this.request = new int[numProcesses][numResources];
        WFG.waitfor = new int[numProcesses][numProcesses];
    }

    public void setAllocation(int processId, int resourceId, int value) {
        this.allocation[processId][resourceId] = value;
    }

    public void setRequest(int processId, int resourceId, int value) {
        this.request[processId][resourceId] = value;
    }

     public void convertWaitforGraph() {
        for (int processId = 0; processId < numProcesses; processId++) {
            for (int resourceId = 0; resourceId < numResources; resourceId++) {
                if (request[processId][resourceId] > allocation[processId][resourceId]) {
                    for (int otherProcessId = 0; otherProcessId < numProcesses; otherProcessId++) {
                        if (allocation[otherProcessId][resourceId] == 1) {
                            waitfor[processId][otherProcessId] = 1;
                        }
                    }
                }
            }
        }
    }

    public void printGraph() {
        System.out.println("Resource Status Graph:");
        System.out.println("   R0\tR1\tR2");
        for (int processId = 0; processId < numProcesses; processId++) {
            System.out.print("P" + processId + ": ");
            for (int resourceId = 0; resourceId < numResources; resourceId++) {
                System.out.print(allocation[processId][resourceId] + "\t");
            }
            System.out.println();
        }
        System.out.println("Process Status Graph:");
        System.out.println("   P0\tP1\tP2\tP3");
        for(int resourceId=0; resourceId<numResources;resourceId++){
            
            System.out.print("R"+resourceId+": ");
            for(int processId=0;processId<numProcesses;processId++){
                System.out.print(request[processId][resourceId]+"\t ");
            }
            System.out.println();

        }
    }
    

    public static void main(String[] args) {
        WFG graph = new WFG(4, 3);
        graph.setAllocation(0, 0, 1);
        graph.setAllocation(1, 1, 1);
        graph.setAllocation(2, 2, 1);
        
        graph.setRequest(0, 1, 1);
        graph.setRequest(1, 2, 1);
        graph.setRequest(2, 1, 1);
        graph.setRequest(3, 0, 1);
        graph.printGraph();

        graph.convertWaitforGraph();
        
        System.out.println("Wait-for graph:");
        System.out.println("\n   P0\tP1\tP2\tP3");
        for (int processId = 0; processId < numProcesses; processId++) {
            System.out.print("P" + processId + ": ");
            for (int otherProcessId = 0; otherProcessId < numProcesses; otherProcessId++) {
                System.out.print(waitfor[processId][otherProcessId] + " \t");
            }
            System.out.println();
        }
    }
    }

