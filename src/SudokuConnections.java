import java.util.*;
public class SudokuConnections {
    public Graph graph;
    private int rows;
    private int cols;
    private int totalBlocks;
    private Set<Integer> allIds;

    public SudokuConnections() {
        graph = new Graph();
        rows = 9;
        cols = 9;
        totalBlocks = rows * cols;
        generateGraph();
        connectEdges();
        allIds = graph.getAllNodesIds();
    }

    private void generateGraph() {
        for (int idx = 1; idx <= totalBlocks; idx++) {
            graph.addNode(Integer.parseInt(String.valueOf(idx)));
        }
    }

    public void connectEdges() {
        String[][] matrix = getGridMatrix();
        Map<String, List<String>> headConnections = new HashMap<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String head = matrix[row][col];
                List<String> connections = whatToConnect(matrix, row, col);
                headConnections.put(head, connections);
            }
        }

        connectThose(headConnections);
    }

    private void connectThose(Map<String, List<String>> headConnections) {
        for (String head : headConnections.keySet()) {
            List<String> connections = headConnections.get(head);
            for (String connection : connections) {
                if (connection.equals("rows") || connection.equals("cols") || connection.equals("blocks")) {
                    continue; // Skip the non-numeric connection types
                }

                int v = Integer.parseInt(connection);
                graph.addEdge(Integer.parseInt(head), v, 0);
            }
        }
    }

    private List<String> whatToConnect(String[][] matrix, int row, int col) {
        List<String> connections = new ArrayList<>();

        List<String> rowConnections = new ArrayList<>();
        List<String> colConnections = new ArrayList<>();
        List<String> blockConnections = new ArrayList<>();

        // ROWS
        for (int c = col + 1; c < cols; c++) {
            rowConnections.add(matrix[row][c]);
        }

        // COLS
        for (int r = row + 1; r < rows; r++) {
            colConnections.add(matrix[r][col]);
        }

        // BLOCKS
        int blockRowStart = (row / 3) * 3;
        int blockColStart = (col / 3) * 3;
        for (int r = blockRowStart; r < blockRowStart + 3; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if (r != row || c != col) {
                    blockConnections.add(matrix[r][c]);
                }
            }
        }

        connections.add("rows");
        connections.add("cols");
        connections.add("blocks");

        connections.addAll(rowConnections);
        connections.addAll(colConnections);
        connections.addAll(blockConnections);

        return connections;
    }

    private String[][] getGridMatrix() {
        String[][] matrix = new String[rows][cols];
        int count = 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                matrix[r][c] = String.valueOf(count);
                count++;
            }
        }
        return matrix;
    }

    public void testConnections() {
        SudokuConnections sudoku = new SudokuConnections();
        sudoku.connectEdges();
        System.out.println("All node Ids : ");
        System.out.println(sudoku.graph.getAllNodesIds());
        System.out.println();
        for (Integer idx : sudoku.graph.getAllNodesIds()) {
            System.out.println(idx + " Connected to->" + sudoku.graph.getAllNodes().get(idx).getConnections());
        }
    }
}