import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (oldFirst == null) last = first;
        else         oldFirst.prev = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (oldLast == null) first = last;
        else                 oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (first == null) last = null;   // to avoid loitering
        else         first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (last == null) first = null;   // to avoid loitering
        else          last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        String[] inputs = new String[] {
          "one", "two", "three", "four", "five",
          "six", "seven", "eight", "nine", "ten"
        };

        for (int i = 0; i < inputs.length; ) {
            deque.addFirst(inputs[i++]);
            deque.addLast(inputs[i++]);
        }
        for (String s: deque) {
            StdOut.print(s + " ");
        }
        StdOut.println("(" + deque.size() + " left on queue)");
        for (int i = 0; i < 5; ++i) {
            StdOut.print(deque.removeLast() + " ");
            StdOut.print(deque.removeFirst() + " ");
        }
        StdOut.println("(" + deque.size() + " left on queue)");
    }
}
