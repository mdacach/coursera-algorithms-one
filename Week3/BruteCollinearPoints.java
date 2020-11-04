import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Arrays;
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numSegments;

    private void resize(int n) {
        LineSegment[] copy = new LineSegment[n];
        for (int i = 0; i < numSegments; i++) 
            copy[i] = segments[i];
        segments = copy;
    }
    // find all line segments containing 4 points by brute force O(n^4)
    public BruteCollinearPoints(Point[] points) {
        if (points == null) 
            throw new IllegalArgumentException("argument may not be null");
        for (Point x : points)
            if (x == null) 
                throw new IllegalArgumentException("point may not be null");

        int n = points.length; 

        Point[] copy = new Point[n];
        for (int i = 0; i < n; i++) 
            copy[i] = points[i];

        Arrays.sort(copy);
        Point previous = copy[0];
        for (int i = 1; i < n; i++) {
            if (copy[i].equals(previous))
                throw new IllegalArgumentException("points may not be repeated");
            previous = copy[i];
        }

        segments = new LineSegment[2];
        for (int i = 0; i < n; i++) 
            for (int j = i+1; j < n; j++) 
                for (int k = j+1; k < n; k++)
                    for (int l = k+1; l < n; l++) {
                        Point[] curr = new Point[4];
                        curr[0] = copy[i];
                        curr[1] = copy[j];
                        curr[2] = copy[k];
                        curr[3] = copy[l];
                        Arrays.sort(curr);
                        //for (Point x : curr) System.out.println(x);
                        //StdOut.println();
                        double first = copy[i].slopeTo(copy[j]);
                        double second = copy[i].slopeTo(copy[k]);
                        double third = copy[i].slopeTo(copy[l]);
                        boolean allEqual = first == second && first == third;
                        if (allEqual) {
                            if (numSegments == segments.length) 
                                resize(segments.length * 2);
                            segments[numSegments++] = new LineSegment(curr[0], curr[3]);
                        }
                    }
    }

    // return number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // return the line segments
    public LineSegment[] segments() {
        //LineSegment[] copy = new LineSegment[numberOfSegments()];
        //for (int i = 0; i < numberOfSegments(); i++)
            //copy[i] = segments[i];
        //return copy;
        return Arrays.copyOfRange(segments, 0, numSegments);
    }

    public static void main(String[] args) {
        //StdOut.println("hello world");
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);

        }
        BruteCollinearPoints bc = new BruteCollinearPoints(points);
        LineSegment[] lineSegments = bc.segments();
        for (LineSegment x : lineSegments) System.out.println(x);

    }

}
