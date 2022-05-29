import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        int n = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            ++n;
            if (q.size() < k) q.enqueue(item);
            else {
                int x = StdRandom.uniform(n);
                if (x < q.size()) {
                    q.dequeue();
                    q.enqueue(item);
                }
            }
        }
        for (String s: q) {
            StdOut.println(s);
        }
    }
}
