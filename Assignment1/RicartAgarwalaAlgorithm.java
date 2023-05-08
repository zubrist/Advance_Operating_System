import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Node {
    int id;
    int local_time;
    boolean[] granted;
    boolean[] deferred;
    Node[] nodes;
    Queue<Integer>[] queue;

    public Node(int id, int num_nodes) {
        this.id = id;
        local_time = 0;
        granted = new boolean[num_nodes];
        deferred = new boolean[num_nodes];
        Arrays.fill(granted, false);
        Arrays.fill(deferred, false);
        nodes = new Node[num_nodes];
        queue = new Queue[num_nodes];
        for (int i = 0; i < num_nodes; i++) {
            queue[i] = new LinkedList<Integer>();
        }
    }

    public void request_critical_section() {
        local_time++;
        for (int i = 0; i < nodes.length; i++) {
            if (i != id) {
                nodes[i].receive_request(id, local_time);
            }
        }
        boolean deferred_request = true;
        while (deferred_request) {
            deferred_request = false;
            for (int i = 0; i < nodes.length; i++) {
                if (i != id && queue[i].size() > 0 && !granted[i] && !deferred[i]) {
                    deferred[i] = true;
                    deferred_request = true;
                }
            }
        }
        boolean granted_request = true;
        while (granted_request) {
            granted_request = false;
            for (int i = 0; i < nodes.length; i++) {
                if (i != id && queue[i].size() > 0 && !granted[i] && !deferred[i]) {
                    granted_request = true;
                    break;
                }
            }
            if (!granted_request) {
                break;
            }
            int max_time = local_time;
            for (int i = 0; i < nodes.length; i++) {
                if (i != id && queue[i].size() > 0 && !granted[i] && !deferred[i]) {
                    max_time = Math.max(max_time, queue[i].peek());
                }
            }
            if (max_time < local_time) {
                granted_request = false;
            } else {
                for (int i = 0; i < nodes.length; i++) {
                    if (i != id && queue[i].size() > 0 && !granted[i] && !deferred[i] && queue[i].peek() == max_time) {
                        deferred[i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < nodes.length; i++) {
            if (i != id && deferred[i]) {
                nodes[i].receive_deferred_request(id, local_time);
            }
        }
        for (int i = 0; i < nodes.length; i++) {
            if (i != id && queue[i].size() > 0 && !granted[i] && !deferred[i]) {
                nodes[i].receive_grant(id, local_time);
            }
        }
        boolean all_granted = true;
        for (int i = 0; i < nodes.length; i++) {
            if (i != id && queue[i].size() > 0 && !granted[i]) {
                all_granted = false;
                break;
            }
        }
        if (all_granted) {
            critical_section();
            for (int i = 0; i < nodes.length; i++) {
                if (i != id && queue[i].size() > 0 && granted[i]) {
                    nodes[i].receive_release(id);
                }
            }
        }
    }

    public void receive_request(int sender_id, int sender_time) {
        queue[sender_id].add(sender_time);
        if (!using_critical_section() && !deferred[sender_id]) {
            nodes[sender_id].receive_grant(id, local_time);
        }
    }

    public void receive_deferred_request(int sender_id, int sender_time) {
        queue[sender_id].add(sender_time);
    }

    public void receive_grant(int sender_id, int sender_time) {
        granted[sender_id] = true;
    }

    public void receive_release(int sender_id) {
        granted[sender_id] = false;
        deferred[sender_id] = false;
        queue[sender_id].poll();
    }

    private void critical_section() {
        System.out.println("Node " + id + " enters critical section.");
    }

    private boolean using_critical_section() {
        for (int i = 0; i < nodes.length; i++) {
            if (i != id && granted[i]) {
                return true;
            }
        }
        return false;
    }
}

public class RicartAgarwalaAlgorithm {
    public static void main(String[] args) {
        int num_nodes = 5;
        int id;
        Node[] nodes = new Node[num_nodes];
        for (int i = 0; i < num_nodes; i++) {
            nodes[i] = new Node(i, num_nodes);
            nodes[i].nodes = nodes;
        }
        int num_requests = 10;
        Random random = new Random();
        for (int i = 0; i < num_requests; i++) {
            int node_id = random.nextInt(num_nodes);
            nodes[node_id].request_critical_section();
            System.out.println("Request lists:");
            for (int j = 0; j < num_nodes; j++) {
                System.out.println("Node " + j + ": " + nodes[j].queue[node_id]);
            }
        }
    }
}