public class RAG {

    private int numProcesses;
    private int numResources;
    private int[][] allocation;
    private int[][] request;

    public RAG(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;
        this.allocation = new int[numProcesses][numResources];
        this.request = new int[numProcesses][numResources];
    }

    public void setAllocation(int processId, int resourceId, int value) {
        this.allocation[processId][resourceId] = value;
    }

    public void setRequest(int processId, int resourceId, int value) {
        this.request[processId][resourceId] = value;
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
        RAG graph = new RAG(4, 3);
        graph.setAllocation(0, 0, 1);
        graph.setAllocation(1, 1, 1);
        graph.setAllocation(2, 2, 1);
        
        graph.setRequest(0, 1, -1);
        graph.setRequest(1, 2, -1);
        graph.setRequest(2, 1, -1);
        graph.setRequest(3, 0, 0);
        graph.printGraph();
    }
}
