import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final int size; 
    private final boolean[][] grid; 
    private int openSites; 
    private final WeightedQuickUnionUF UF;  // this UF will handle checking if percolates in O(logN)
    private final WeightedQuickUnionUF UF2; // this UF will keep track of which sites are full


    // creates a n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (!validate(n)) throw new IllegalArgumentException("n must be >= 0"); 

        size = n; 
        grid = new boolean[n+2][n+2]; // all false
        openSites = 0; 
        UF = new WeightedQuickUnionUF(n*n+2); // all n*n grids plus the two virtual
        UF2 = new WeightedQuickUnionUF(n*n+1); // all n*n grids plus the top virtual

        // create two virtual nodes to see if percolates
        // 0 is the upper virtual 
        // n*n + 1 is the lower virtual, connected to all bottom row 
        // the system percolates if 0 is connected to n*n+1
        
        // connect all bottom row to virtual bottom 
        for (int i = 1; i <= n; i++) 
            UF.union(n*n+1, (n-1)*n + i); 
        
        // we will connect top row to virtual top when they are opened
    }

    // transition from 2d (grid) to 1d (UF element) starting at 1 and ending at N
    private int translate2d(int row, int col) {
        // (1, 1) -> 1,
        // (2, 2) -> 4,
        return (row-1) * size + col; 
    }


    // validate for one input
    private boolean validate(int n) {
        return n > 0;
    }

    // validate for two inputs
    private boolean validate(int row, int col) {
        boolean rowOK = row >= 1 && row <= size; 
        boolean colOK = col >= 1 && col <= size;
        return rowOK && colOK; 
    }
        

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException("row and col must be between [1, n]"); 

        // if already open, do nothing
        if (grid[row][col]) {
            return;
        }

        openSites++; 
        grid[row][col] = true;

        // UF element 
        int thisElement = translate2d(row, col); 

        // special case first row
        if (row == 1) {
            UF.union(thisElement, 0); 
            UF2.union(thisElement, 0); 
        }

        // for each neighbor, if neighbor is open, connect them
        int[] distX = {-1, 1}; 
        int[] distY = {-1, 1}; 

        for (int x : distX) {
            if (grid[row+x][col]) {
                int otherElement = translate2d(row+x, col); 
                UF.union(thisElement, otherElement); 
                UF2.union(thisElement, otherElement); 
            }
        }
        for (int y : distY) {
            if (grid[row][col+y]) {
                int otherElement = translate2d(row, col+y); 
                UF.union(thisElement, otherElement); 
                UF2.union(thisElement, otherElement); 
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) 
            throw new IllegalArgumentException("row and col must be between [1, n]"); 

        return grid[row][col];
    }

    // is the site (row, col) full? 
    public boolean isFull(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException("row and col must be between [1, n]"); 
        
        int thisElement = translate2d(row, col); 
        if (!isOpen(row, col)) return false; // if not open return
        
        // this keeps track if ACTUAL site is connected to virtual top
        // if we use the same UF we will get false positives if the system percolates,
        // as all bottom row is connected to the virtual bottom and thus connected to 
        // virtual top.
        return UF2.find(thisElement) == UF2.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        // keep a variable to be able to answer it in O(1)
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // do the two virtual elements connect
        // with top and bottom virtual nodes we can answer it in O(logN)
        return UF.find(size*size + 1) == UF.find(0) && numberOfOpenSites() >= 1; 
    }

    // test client 
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println(p.percolates());
        p.open(4, 3);
        p.open(2, 3);
        p.open(2, 1);
        p.open(5, 5);
        p.open(5, 4);
        p.open(1, 4);
        p.open(5, 2);
        p.open(4, 3);
        p.open(3, 4);
        p.open(2, 2);
        p.open(2, 4);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        System.out.println(p.percolates());
    }
}
