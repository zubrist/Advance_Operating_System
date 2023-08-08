import java.util.*;
public class HRMDD {

    private static int numProcesses;
    private int numResources;
    private int[][] allocation;
    private int[][] request;
     private static int[][] waitfor;

    public HRMDD(int numProcesses, int numResources) {
        HRMDD.numProcesses = numProcesses;
        this.numResources = numResources;
        this.allocation = new int[numProcesses][numResources];
        this.request = new int[numProcesses][numResources];
        HRMDD.waitfor = new int[numProcesses][numProcesses];
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
     public void unionWaitforGraph(int[][] waitfor1, int[][] waitfor2) {
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numProcesses; j++) {
                waitfor[i][j] = waitfor1[i][j] | waitfor2[i][j];
            }
        }
    }
     public boolean isDeadlocked() {
        List<Integer> visit = new ArrayList<>();
        for (int processId = 0; processId < numProcesses; processId++) {
            if (!visit.contains(processId)) {
                if (hasCycle(processId, visit, new ArrayList<>())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCycle(int processId, List<Integer> visit, List<Integer> stack) {
        visit.add(processId);
        stack.add(processId);

        for (int otherProcessId = 0; otherProcessId < numProcesses; otherProcessId++) {
            if (waitfor[processId][otherProcessId] == 1) {
                if (!visit.contains(otherProcessId)) {
                    if (hasCycle(otherProcessId, visit, stack)) {
                        return true;
                    }
                } else if (stack.contains(otherProcessId)) {
                    return true;
                }
            }
        }

        stack.remove(stack.size() - 1);
        return false;
    }
    

    public static void main(String[] args) {

        System.out.println("************SITE 1***************");
        HRMDD site1 = new HRMDD(4, 3);
        site1.setAllocation(0, 0, 1);
        site1.setAllocation(1, 1, 1);
        site1.setAllocation(2, 2, 1);
        
        site1.setRequest(0, 1, 1);
        site1.setRequest(1, 2, 1);
        site1.setRequest(2, 1, 1);
        site1.setRequest(3, 0, 1);
        site1.printGraph();

        site1.convertWaitforGraph();
        int[][] waitfor1 = site1.waitfor;
        
        System.out.println("\nWait-for graph for SITE 1:");
        System.out.println("\n   P0\tP1\tP2\tP3");
        for (int processId = 0; processId < numProcesses; processId++) {
            System.out.print("P" + processId + ": ");
            for (int otherProcessId = 0; otherProcessId < numProcesses; otherProcessId++) {
                System.out.print(waitfor[processId][otherProcessId] + " \t");
            }
            System.out.println();
        }
        if (site1.isDeadlocked()) {
            System.out.println("\nDeadlock detected\n");
        } else {
            System.out.println("No deadlock detected\n");
        }
        System.out.println("************SITE 2***************");
        HRMDD site2 = new HRMDD(3, 3);

        site2.setAllocation(0, 0, 1);
        site2.setAllocation(1, 1, 1);
       
        
        site2.setRequest(0, 1, 1);
        site2.setRequest(1, 2, 1);
        
        site2.printGraph();

        site2.convertWaitforGraph();
        int[][] waitfor2 = site2.waitfor;
        
        System.out.println("\nWait-for graph for SITE 2:");
        System.out.println("\n   P0\tP1\tP2\tP3");
        for (int processId = 0; processId < numProcesses; processId++) {
            System.out.print("P" + processId + ": ");
            for (int otherProcessId = 0; otherProcessId < numProcesses; otherProcessId++) {
                System.out.print(waitfor[processId][otherProcessId] + " \t");
            }
            System.out.println();
        }
        if (site2.isDeadlocked()) {
            System.out.println("\nDeadlock detected");
        } else {
            System.out.println("No deadlock detected");
        }

        site1.unionWaitforGraph(waitfor1, waitfor2);
        System.out.println("\nFinal Wait-for Graph:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numProcesses; j++) {
                if (waitfor[i][j] == 1) {
                    System.out.print("P" + i + " -> P" + j + "\t");
                }
            }
            System.out.println();
        }
        if (site1.isDeadlocked()) {
            System.out.println("\n<-|| Deadlock detected ||->");
        } else {
            System.out.println("\n <-|| No deadlock detected ||->");
        }

    }
    }

