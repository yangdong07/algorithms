import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public boolean isEmpty() { return n == 0; }
    public int size() { return n; }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[n++] = item;                        // add item
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int x = StdRandom.uniform(n);
        Item item = q[x];
        q[x] = q[--n];
        q[n] = null;   // to avoid loitering
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return q[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedIterator implements Iterator<Item> {
        private int i = 0;
        private int[] permutation;

        public RandomizedIterator() { permutation = StdRandom.permutation(n); }
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[permutation[i++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        String[] inputs = new String[] {
                "0", "1", "2", "3", "4",
                "5", "6", "7", "8", "9"
        };

        // test enqueue
        for (int i = 0; i < inputs.length; ++i) {
            queue.enqueue(inputs[i]);
        }
        // test iterator
        for (String s: queue) {
            StdOut.print(s + " ");
        }
        StdOut.println("");
        // test sample
        for (int i = 0; i < 20; ++i) {
            StdOut.print(queue.sample() + " ");
        }
        StdOut.println("");
        // test dequeue
        for (int i = 0; i < inputs.length; ++i) {
            StdOut.print(queue.dequeue() + " ");
        }
        // test size
        StdOut.println("(" + queue.size() + " left on queue)");
    }
}
