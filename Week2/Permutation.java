import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> dq = new RandomizedQueue<>();

        int i = 1;
        while (!StdIn.isEmpty()) {
            String x = StdIn.readString();
            int val = StdRandom.uniform(1, i+1);
            if (i <= k) dq.enqueue(x);
            else if (val <= k) {
                dq.dequeue();
                dq.enqueue(x);
            }
            i++;
        }

        for (String x : dq) StdOut.println(x);


    }

}
