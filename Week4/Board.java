import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] tiles; 
    private final int dimension;

    // helper class to convert 2d -> 1d and vice versa
    private class Position {
        public final int x; // x in grid
        public final int y; // y in grid
        public final int n; // pos in 1d

        public Position(int x, int y) {
            if ((x == dimension - 1) && (y == dimension - 1)) {
                this.x = x;
                this.y = y;
                this.n = 0;
                return;
            }

            this.x = x;
            this.y = y;
            this.n = x * dimension + (y + 1);
        }

        public Position(int n) {
            if (n == 0) {
                this.x = dimension-1;
                this.y = dimension-1;
                this.n = 0;
                return;
            }
            this.x = (n-1) / dimension;
            if (n % dimension == 0) this.y = dimension - 1;
            else this.y = (n % dimension) - 1;
            this.n = n;
        }

    }
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] t) {
        dimension = t[0].length;
        tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                tiles[i][j] = t[i][j];
    }

    // number of tiles out of place
    public int hamming() {
        // to check for goal board we want the "n" in position class of each pos
        int dist = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != new Position(i, j).n) 
                    dist++;
            }

        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < dimension; i++) 
            for (int j = 0; j < dimension; j++) {
                // the number it should be
                if (this.tiles[i][j] == 0) continue;
                Position correct = new Position(this.tiles[i][j]);
                //StdOut.println("correct: " + correct.x + " " + correct.y);
                //StdOut.println("is: " + i + " " + j);
                //StdOut.println("correct x: " + correct.x);
                //StdOut.println("is: " + i);
                //StdOut.println("correct y: " + correct.y);
                //StdOut.println("is: " + j);
                //int dx = Math.abs(correct.x - i) + Math.abs(correct.y - j);
                //if (dx > 0) {
                    //StdOut.println(tiles[i][j]);
                    //StdOut.println("correct: " + correct.x + " " + correct.y);
                    //StdOut.println("is: " + i + " " + j);
                //}
                dist += Math.abs(correct.x - i) + Math.abs(correct.y - j);
            }

        return dist;
    }

    // is this board the goal board
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y? 
    public boolean equals(Object that) {
        // java rules for equals taken from Date.java from specification
        if (that == this) return true;
        if (that == null) return false;
        if (that.getClass() != this.getClass()) return false;
        Board other = (Board) that;
        if (this.dimension != other.dimension) 
            return false;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) 
                if (this.tiles[i][j] != other.tiles[i][j])
                    return false;
        return true;
    }


    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder(Integer.toString(dimension) + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                str.append(tiles[i][j] + " ");
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        int x = -1, y = -1;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    x = i; y = j;
                }
            }

        int[] dx = {-1, 1};
        int[] dy = {-1, 1};
        for (int k : dx) {
            if (x + k >= 0 && x + k < dimension) {
                int[][] copy = copy();
                int temp = copy[x+k][y];
                copy[x+k][y] = 0;
                copy[x][y] = temp;
                Board neighbor = new Board(copy);
                boards.push(neighbor);
            }
        }
        for (int k : dy) {
            if (y + k >= 0 && y + k < dimension) {
                int[][] copy = copy();
                int temp = copy[x][y+k];
                copy[x][y+k] = 0;
                copy[x][y] = temp;
                Board neighbor = new Board(copy);
                boards.push(neighbor);
            }
        }

        return boards;
    }

    private int[][] copy() {
        int[][] copy = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) 
            for (int j = 0; j < dimension; j++) 
                copy[i][j] = tiles[i][j];
        return copy;
    }

    public Board twin() {
        int[][] copy = copy();

        if (copy[0][0] != 0 && copy[0][1] != 0) {
            int t = copy[0][0];
            copy[0][0] = copy[0][1];
            copy[0][1] = t;
        } 
        else {
           int t = copy[1][0];
           copy[1][0] = copy[1][1];
           copy[1][1] = t;
        }

        return new Board(copy);
    }

    public static void main(String[] args) {
        // create initial board from file
            //In in = new In(args[0]);
            //int n = in.readInt();
            //int[][] tiles = new int[n][n];
            //for (int i = 0; i < n; i++)
                //for (int j = 0; j < n; j++)
                    //tiles[i][j] = in.readInt();
            //Board initial = new Board(tiles);
            //StdOut.println(initial.manhattan());


        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        //int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        //int[][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 6, 8}};
        Board b = new Board(tiles);
        StdOut.println(b);

        //StdOut.println("hamming: " + b.hamming());
        StdOut.println("manhattan: " + b.manhattan());
        tiles[0][0] = 9;
        //Board b = new Board(tiles);
        StdOut.println(b);

        //StdOut.println("hamming: " + b.hamming());
        StdOut.println("manhattan: " + b.manhattan());
        //StdOut.println("neighbors: ");
        //StdOut.println(b.neighbors());
        //StdOut.println("twin: ");
        //StdOut.println(b.twin());
    //}
    }

}
