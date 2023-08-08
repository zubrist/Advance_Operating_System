import java.util.ArrayList;
import java.util.List;

/**
 * The NodeNew class represents a node in the graph. It contains information about the node's ID, state, successors, and message log.
 * It also provides methods for receiving and propagating markers, marking channels, and recording the node's state.
 */
public class NodeNew {
    public int id;
    public boolean hasMarker;
    public List<NodeNew> successors;
    public List<Integer> state;
    public List<Boolean> channelOccupied;
    public List<String> messageLog;
    public Object mtx;

    /**
     * Constructs a NodeNew object with the given node ID and number of channels.
     * Initializes the node's state, channel occupation, message log, and synchronization object.
     *
     * @param nodeId       The ID of the node.
     * @param numChannels  The number of channels (successors) of the node.
     */

    public NodeNew(int nodeId, int numChannels) {
        id = nodeId;
        hasMarker = false;
        successors = new ArrayList<>();
        state = new ArrayList<>();
        channelOccupied = new ArrayList<>();
        messageLog = new ArrayList<>();
        for (int i = 0; i < numChannels; i++) {
            channelOccupied.add(false);
        }
        mtx = new Object();
    }

    /**
     * Receives a marker with the given value. If the node hasn't received a marker before, it records its state,
     * adds a message to the message log, and propagates the marker to its successors.
     *
     * @param markerValue  The value associated with the marker.
     */
    public void receiveMarker(int markerValue) {
        if (!hasMarker) {
            synchronized (mtx) {
                hasMarker = true;
                System.out.println("Node " + id + " received marker with value " + markerValue + ".");
                state = getState();
                messageLog.add("Received marker: " + markerValue);
                propagateMarker(markerValue);
            }
        }
    }

     /**
     * Propagates the marker with the given value to the node's successors.
     * Marks the channel as occupied, sends a message to the successor, and marks the channel as empty.
     *
     * @param markerValue  The value associated with the marker.
     */
    public void propagateMarker(int markerValue) {
        for (NodeNew successor : successors) {
            if (!successor.hasMarker) {
                System.out.println("Propagating marker with value " + markerValue + " from Node " + id + " to Node " + successor.id);
                markChannelAsOccupied(successor.id);
                successor.receiveMarker(markerValue);
                markChannelAsEmpty(successor.id);
            }
        }
    }
/**
     * Marks the channel to the specified successor node as occupied.
     *
     * @param successorId  The ID of the successor node.
     */
    public void markChannelAsOccupied(int successorId) {
        synchronized (channelOccupied) {
            while (channelOccupied.size() <= successorId) {
                channelOccupied.add(false);
            }
            channelOccupied.set(successorId, true);
        }
        System.out.println("Channel " + id + " -> " + successorId + " marked as OCCUPIED.");
        Thread mcaoThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        });
        mcaoThread.start();

        messageLog.add("Sent message 'Hi' to Node " + successorId);
    }
    /**
     * Marks the channel to the specified successor node as empty.
     *
     * @param successorId  The ID of the successor node.
     */
    public void markChannelAsEmpty(int successorId) {
        channelOccupied.set(successorId, false);
        System.out.println("Channel " + id + " -> " + successorId + " marked as EMPTY.");
        // messageLog.add("Received message 'hello' from Node " + successorId);
    }
    /**
     * Prints the message log of the node.
     */
    public void printMessageLog() {
        System.out.println("Message Log for Node " + id + ":");
        for (String message : messageLog) {
            System.out.println(message);
        }
    }
     /**
     * Simulates recording the state of the node by waiting for a certain time and adding the state information to the currentState list.
     * This method is called when the node receives a marker and records its state.
     *
     * @return The recorded state of the node.
     */
    public List<Integer> getState() {
        List<Integer> currentState = new ArrayList<>();
        // Simulating recording the state of the node
        Thread stateRecordingThread = new Thread(() -> {
            try {
                // Sleep for 2 seconds to introduce a gap between node simulations
                Thread.sleep(2000);
                // Add the desired state information to the currentState list
                for (NodeNew successor : successors) {
                    currentState.add(successor.id);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // Start the state recording thread
        stateRecordingThread.start();
        // Wait for the state recording thread to finish
        try {
            stateRecordingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentState;
    }
     /**
     * Returns the ID of the node.
     *
     * @return The ID of the node.
     */

    public int getId() {
        return id;
    }
}