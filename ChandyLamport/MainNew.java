import java.io.IOException;

public class MainNew {
    public static void main(String[] args) {
        try {
             // Read the input graph from a file
            String filename = "inputCL.txt";
            int[][] graph = InputReader.readGraph(filename);
            GraphNew graphObj = new GraphNew(graph);

             // Select the initiator node and marker value
            NodeNew initiator = graphObj.nodes.get(0);
            int markerValue = 23;
            System.out.println("Initiating Chandy-Lamport State Recording Algorithm.\n\n");

            // Start the Chandy-Lamport State Recording Algorithm
            graphObj.startAlgorithm(initiator, markerValue);
            System.out.println("State Recording Algorithm Terminated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}