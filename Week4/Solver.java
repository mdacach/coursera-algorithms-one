import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
public class Solver {
    private Board goal; 
    private Node end;
    private boolean isSolvable;

    private class Node implements Comparable<Node>{
        public Board board;
        public Node previous;
        public int moves;
        public int priority;

        //public static final Comparator<Node> BY_HAMMING = new ByHamming();
        //public static final Comparator<Node> BY_MANHATTAN = new ByManhattan();

        public Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(Node other) {
            return this.priority - other.priority;
        }

        //private static class ByHamming implements Comparator<Node> {
            //public int compare(Node a, Node b) {
                //int ax = a.moves + a.board.hamming();
                //int bx = b.moves + b.board.hamming();
                //return ax - bx;
            //}
        //}

        //private static class ByManhattan implements Comparator<Node> {
            //public int compare(Node a, Node b) {
                //int ax = a.moves + a.board.manhattan();
                //int bx = b.moves + b.board.manhattan();
                //return ax - bx;
            //}
        //}
    }

    public Solver(Board initial) {
        if (initial == null) 
            throw new IllegalArgumentException("initial board must not be null.");
        // create the goal board
        int cnt = 1;
        int n = initial.dimension();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n; j++) 
                grid[i][j] = cnt++;
        goal = new Board(grid);
        //StdOut.println()
                
        // check for unsolvable puzzles
        Board twin = initial.twin();

        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pq2 = new MinPQ<>();

        Node start = new Node(initial, null, 0);
        Node start2 = new Node(twin, null, 0);

        pq.insert(start);
        pq2.insert(start2);

        Node curr = pq.delMin();
        Node curr2 = pq2.delMin();

        while (curr.board.hamming() > 0 && curr2.board.hamming() > 0) {
            for (Board x : curr.board.neighbors()) {
                if (curr.previous != null && x.equals(curr.previous.board)) continue;
                Node next = new Node(x, curr, curr.moves+1);
                pq.insert(next);
            }
            for (Board x : curr2.board.neighbors()) {
                if (curr2.previous != null && x.equals(curr2.previous.board)) continue;
                Node next = new Node(x, curr2, curr2.moves+1);
                pq2.insert(next);
            }
            curr = pq.delMin();
            curr2 = pq2.delMin();
        }
        if (curr.board.hamming() == 0) {
            isSolvable = true;
            end = curr;
        }
        else {
            isSolvable = false;
            end = null;
        }

    }

    public int moves() {
        if (end == null) return -1;
        return end.moves; 
    }

    public Iterable<Board> solution() {
        if (end == null) return null;
        Node copy = end;
        Stack<Board> ans = new Stack<>();
        while (copy != null) {
            ans.push(copy.board);
            copy = copy.previous;
        }
        return ans;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public static void main(String[] args) {
    // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

         //print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        StdOut.println(solver.moves());
        StdOut.println(solver.isSolvable());
        StdOut.println(solver.isSolvable());
        StdOut.println(solver.isSolvable());
        StdOut.println(solver.isSolvable());
        StdOut.println(solver.solution());
        StdOut.println(solver.solution());
        

    }
}
