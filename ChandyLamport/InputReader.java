import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The InputReader class is responsible for reading the input graph from a file.
 */

public class InputReader {

    /**
     * Reads the input graph from the specified file and returns it as a 2D array.
     *
     * @param filename  The name of the input file.
     * @return The input graph as a 2D array.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static int[][] readGraph(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        String[] parts = line.split(" ");
        int numNodes = Integer.parseInt(parts[0]);
        int numEdges = Integer.parseInt(parts[1]);
        int[][] graph = new int[numNodes][numEdges];
        for (int i = 0; i < numEdges; i++) {
            line = reader.readLine();
            parts = line.split(" ");
            int node = Integer.parseInt(parts[0]);
            for (int j = 1; j < parts.length; j++) {
                graph[node][i] = Integer.parseInt(parts[j]);
            }
        }
        line = reader.readLine();
        parts = line.split(" ");
        int initiator = Integer.parseInt(parts[0]);
        int markerValue = Integer.parseInt(parts[1]);
        reader.close();
        return graph;
    }
}