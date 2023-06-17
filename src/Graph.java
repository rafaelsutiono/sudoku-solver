import java.util.*;

class Node {
    private int id;
    private int data;
    private HashMap<Integer, Integer> connectedTo;

    public Node(int idx, int data) {
        id = idx;
        this.data = data;
        connectedTo = new HashMap<>();
    }

    public void addNeighbour(Node neighbour, int weight) {
        if (!connectedTo.containsKey(neighbour.getID())) {
            connectedTo.put(neighbour.getID(), weight);
        }
    }

    public void setData(int data) {
        this.data = data;
    }

    public Set<Integer> getConnections() {
        return connectedTo.keySet();
    }

    public int getID() {
        return id;
    }

    public int getData() {
        return data;
    }

    public int getWeight(Node neighbour) {
        return connectedTo.get(neighbour.getID());
    }

    public String toString() {
        return data + " Connected to: " + connectedTo.keySet();
    }
}

class Graph {
    public static int totalV = 0;
    private HashMap<Integer, Node> allNodes;

    public Graph() {
        allNodes = new HashMap<>();
    }

    public Node addNode(int idx) {
        if (allNodes.containsKey(idx)) {
            return null;
        }

        totalV++;
        Node node = new Node(idx, 0);
        allNodes.put(idx, node);
        return node;
    }

    public void addNodeData(int idx, int data) {
        if (allNodes.containsKey(idx)) {
            Node node = allNodes.get(idx);
            node.setData(data);
        } else {
            System.out.println("No ID to add the data.");
        }
    }

    public void addEdge(int src, int dst, int wt) {
        if (allNodes.containsKey(src) && allNodes.containsKey(dst)) {
            Node source = allNodes.get(src);
            Node destination = allNodes.get(dst);
            source.addNeighbour(destination, wt);
            destination.addNeighbour(source, wt);
        }
    }

    public boolean isNeighbour(int u, int v) {
        if (u >= 1 && u <= 81 && v >= 1 && v <= 81 && u != v) {
            if (allNodes.containsKey(u)) {
                Node node = allNodes.get(u);
                return node.getConnections().contains(v);
            }
        }
        return false;
    }

    public void printEdges() {
        for (int idx : allNodes.keySet()) {
            Node node = allNodes.get(idx);
            for (int con : node.getConnections()) {
                System.out.println(node.getID() + " --> " + con);
            }
        }
    }

    public Node getNode(int idx) {
        if (allNodes.containsKey(idx)) {
            return allNodes.get(idx);
        }
        return null;
    }

    public Set<Integer> getAllNodesIds() {
        return allNodes.keySet();
    }

    public void DFS(int start) {
        boolean[] visited = new boolean[totalV];

        if (allNodes.containsKey(start)) {
            DFSUtility(start, visited);
        } else {
            System.out.println("Start Node not found");
        }
    }

    private void DFSUtility(int node_id, boolean[] visited) {
        visited[node_id] = true;
        System.out.print(allNodes.get(node_id).getID() + " ");

        for (int i : allNodes.get(node_id).getConnections()) {
            if (!visited[allNodes.get(i).getID()]) {
                DFSUtility(allNodes.get(i).getID(), visited);
            }
        }
    }

    public void BFS(int start) {
        boolean[] visited = new boolean[totalV];
        Queue<Integer> queue = new LinkedList<>();

        if (allNodes.containsKey(start)) {
            BFSUtility(start, visited, queue);
        } else {
            System.out.println("Start Node not found");
        }
    }

    private void BFSUtility(int node_id, boolean[] visited, Queue<Integer> queue) {
        visited[node_id] = true;
        queue.offer(node_id);

        while (!queue.isEmpty()) {
            int x = queue.poll();
            System.out.print(allNodes.get(x).getID() + " ");

            for (int i : allNodes.get(x).getConnections()) {
                int idx = allNodes.get(i).getID();
                if (!visited[idx]) {
                    queue.offer(idx);
                    visited[idx] = true;
                }
            }
        }
    }

    public HashMap<Integer, Node> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(HashMap<Integer, Node> allNodes) {
        this.allNodes = allNodes;
    }
}
