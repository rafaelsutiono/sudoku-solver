import java.util.*;
//this is for testing the algorithms for both graph and sudokuconnections classes
//public class Main {
//    public static void main(String[] args) {
//        Graph g = new Graph();
//        for (int i = 0; i < 6; i++) {
//            g.addNode(i);
//        }
//
//        System.out.println("Vertices: " + g.getAllNodesIds());
//
//        g.addEdge(0, 1, 5);
//        g.addEdge(0, 5, 2);
//        g.addEdge(1, 2, 4);
//        g.addEdge(2, 3, 9);
//        g.addEdge(3, 4, 7);
//        g.addEdge(3, 5, 3);
//        g.addEdge(4, 0, 1);
//        g.addEdge(5, 4, 8);
//        g.addEdge(5, 2, 1);
//
//        g.printEdges();
//
//        System.out.println("DFS (starting with 0):");
//        g.DFS(0);
//        System.out.println();
//
//        System.out.println("BFS (starting with 0):");
//        g.BFS(0);
//        System.out.println();
//        testConnections();
//    }
//
//    private static void testConnections() {
//        SudokuConnections sudoku = new SudokuConnections();
//        sudoku.connectEdges();
//        System.out.println("All node Ids: ");
//        System.out.println(sudoku.graph.getAllNodesIds());
//        System.out.println();
//
//        for (Integer idx : sudoku.graph.getAllNodesIds()) {
//            System.out.println(idx + " Connected to->" + sudoku.graph.getNode(idx).getConnections());
//        }
//    }
//}
public class SudokuBoard {
    private int[][] board;
    private SudokuConnections sudokuGraph;
    private int[][] mappedGrid;

    public SudokuBoard() {
        this.board = getBoard();
        this.sudokuGraph = new SudokuConnections();
        this.mappedGrid = getMappedMatrix();
    }

    private int[][] getMappedMatrix() {
        int[][] matrix = new int[9][9];
        int count = 1;
        for (int rows = 0; rows < 9; rows++) {
            for (int cols = 0; cols < 9; cols++) {
                matrix[rows][cols] = count;
                count++;
            }
        }
        return matrix;
    }

    private int[][] getBoard() {
        int[][] board = {
                {0, 6, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 9, 3, 0, 0, 2, 0, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 0},
                {8, 0, 0, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 6, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9},
                {2, 0, 0, 0, 0, 5, 0, 8, 0},
                {0, 0, 0, 0, 7, 0, 3, 0, 0},
                {0, 0, 0, 0, 9, 6, 0, 0, 0}
        };
        return board;
    }

    public void printBoard() {
        System.out.println("    1 2 3     4 5 6     7 8 9");
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0) {
                System.out.println("  - - - - - - - - - - - - - - ");
            }

            for (int j = 0; j < board[i].length; j++) {
                if (j % 3 == 0) {
                    System.out.print(" |  ");
                }
                if (j == 8) {
                    System.out.print(board[i][j] + " | " + (i + 1));
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  - - - - - - - - - - - - - - ");
    }

    public boolean isBlank() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] graphColoringInitializeColor() {
        int[] color = new int[sudokuGraph.graph.totalV + 1];
        List<Integer> given = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) {
                    int idx = mappedGrid[row][col];
                    color[idx] = board[row][col];
                    given.add(idx);
                }
            }
        }
        return color;
    }

    public boolean solveGraphColoring(int m) {
        int[] color = graphColoringInitializeColor();
        if (!graphColorUtility(m, color, 1, new ArrayList<>())) {
            System.out.println(":(");
            return false;
        }
        int count = 1;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = color[count];
                count++;
            }
        }
        return true;
    }

    private boolean graphColorUtility(int m, int[] color, int v, List<Integer> given) {
        if (v == sudokuGraph.graph.totalV + 1) {
            return true;
        }
        for (int c = 1; c <= m; c++) {
            if (isSafe2Color(v, color, c, given)) {
                color[v] = c;
                if (graphColorUtility(m, color, v + 1, given)) {
                    return true;
                }
            }
            if (!given.contains(v)) {
                color[v] = 0;
            }
        }
        return false;
    }

    private boolean isSafe2Color(int v, int[] color, int c, List<Integer> given) {
        if (given.contains(v) && color[v] == c) {
            return true;
        } else if (given.contains(v)) {
            return false;
        }
        for (int i = 1; i <= sudokuGraph.graph.totalV; i++) {
            if (color[i] == c && sudokuGraph.graph.isNeighbour(v, i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SudokuBoard s = new SudokuBoard();
        System.out.println("BEFORE SOLVING ...\n\n");
        s.printBoard();
        System.out.println("\nSolving ...\n\n\nAFTER SOLVING ...\n\n");
        s.solveGraphColoring(9);
        s.printBoard();
    }
}
