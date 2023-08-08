import java.util.ArrayList;
import java.util.List;

/**
 * The GraphNew class represents a graph. It contains a list of nodes and provides methods to start the algorithm and check termination.
 */
public class GraphNew {
    public List<NodeNew> nodes;

     /**
     * Constructs a GraphNew object with the given graph represented as an adjacency matrix.
     * Initializes the nodes list and creates NodeNew objects for each node in the graph.
     * Connects the nodes based on the adjacency matrix.
     *
     * @param graph The input graph represented as an adjacency matrix.
     */
    public GraphNew(int[][] graph) {
        nodes = new ArrayList<>();

        // Connect the nodes based on the adjacency matrix
        for (int i = 0; i < graph.length; i++) {
            NodeNew node = new NodeNew(i, graph[i].length);
            nodes.add(node);
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                int successorId = graph[i][j];
                if (successorId < nodes.size()) {
                    NodeNew successor = nodes.get(successorId);
                    NodeNew node = nodes.get(i);
                    node.successors.add(successor);
                } else {
                    System.err.println("Error: successor ID " + successorId + " is out of bounds.");
                }
            }
        }
    }
    /**
     * Starts the Chandy-Lamport State Recording Algorithm by initiating the algorithm on the given initiator node.
     *
     * @param initiator    The initiator node to start the algorithm from.
     * @param markerValue  The value associated with the marker.
     */

    public void startAlgorithm(NodeNew initiator, int markerValue) {
        initiator.receiveMarker(markerValue);
        checkTermination();
    }

    /**
     * Checks the termination condition of the algorithm. It waits until all nodes have recorded their states.
     */
    public void checkTermination() {
        boolean allNodesRecorded = false;

        while (!allNodesRecorded) {
            allNodesRecorded = true;
            for (NodeNew node : nodes) {
                if (!node.hasMarker) {
                    allNodesRecorded = false;
                    break;
                }
            }
        }
        Thread waitThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        });
        waitThread.start();
        System.out.println("\n State Recording  Completed \n");

        printStacks();

    }
    /**
     * Prints the recorded state stacks and message logs of each node in the graph.
     */
    public void printStacks() {
                System.out.println("State stacks recorded:\n");
        
                for (NodeNew node : nodes) {
                    System.out.print("State recorded by Node " + node.id + ": ");
                    for (int value : node.state) {
                        
                        System.out.print(value + " ");
                        
                    }
                    System.out.println();
                    
                    node.printMessageLog();
                    
                }
            }
}