import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numSegments;

    private void resize(int n) {
        LineSegment[] copy = new LineSegment[n];
        for (int i = 0; i < numSegments; i++) {
            copy[i] = segments[i];
        }
        segments = copy;
    }

    public FastCollinearPoints(Point[] points) {

        if (points == null) 
            throw new IllegalArgumentException("argument may not be null");
        for (Point x : points)
            if (x == null) 
                throw new IllegalArgumentException("point may not be null");

        int n = points.length;

        Point[] ordered_copy = new Point[n];
        for (int i = 0; i < n; i++) 
            ordered_copy[i] = points[i];

        Arrays.sort(ordered_copy); // sorted by coordinates

        // check duplicates
        Point previous = ordered_copy[0];
        for (int i = 1; i < n; i++) {
            if (ordered_copy[i].equals(previous))
                throw new IllegalArgumentException("points may not be repeated");
            previous = ordered_copy[i];
        }

        // resizing array as answer can get very big
        segments = new LineSegment[2];

        // array to actually sort and stuff
        Point[] copy = new Point[n];
        for (int i = 0; i < n; i++) 
            copy[i] = ordered_copy[i];

        //StdOut.println("order by coordinates: ");
        //for (Point x : ordered_copy) StdOut.println(x);

        // for every point
        for (int i = 0; i < n; i++) {
            Point origin = ordered_copy[i]; // take it as origin
            //StdOut.println("ORIGIN: " + origin);

            // sort it by slope with the lowest first
            Arrays.sort(copy); // stable by coordinates
            Arrays.sort(copy, origin.slopeOrder()); // sort by slope

             //starting at p = i + 2 check three for three
            //for (Point x : copy) StdOut.println(x);
            //StdOut.println("slopes");
            //for (Point x : copy) StdOut.println(origin.slopeTo(x));

            // for all points 
            for (int p = 0; p < n; p++) {
                //StdOut.println("testing point " + p + copy[p]);
                int pt = p;
                int cnt = 1;
                // get the maximal segment with same slope
                while (pt < n-1 && origin.slopeTo(copy[pt]) == origin.slopeTo(copy[pt+1])) {
                    //StdOut.println("slopes: ");
                    //StdOut.println(origin.slopeTo(copy[pt]));
                    //StdOut.println(origin.slopeTo(copy[pt+1]));
                    cnt++;
                    pt++;
                }
                //StdOut.println("cnt " + cnt);
                if (cnt >= 3) { // how many with same slope
                    // if some point in this segment is smallest than origin,
                    // we have considered this segment when calculating for 
                    // origin before (as we calculate by sorted order)
                    if (origin.compareTo(copy[p]) < 0) { 
                        //StdOut.println(origin + " vs " + copy[p]);
                        //StdOut.println("add segment");
                        if (numSegments == segments.length) 
                            resize(segments.length * 2);
                        segments[numSegments++] = new LineSegment(origin, copy[p+cnt-1]);
                    }
                    //else StdOut.println("repeated segment");
                    p = p + cnt - 1;
                    //StdOut.println("p: " + p);

                }
            }
        }
    }

    public int numberOfSegments() {
        return numSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOfRange(segments, 0, numSegments);
    }
    public static void main (String[] args) {
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        FastCollinearPoints fc = new FastCollinearPoints(points);
        for (LineSegment x : fc.segments()) StdOut.println(x);

    
    }
}
