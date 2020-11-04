import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    private class Node {
        Item item;
        Node next;
        Node prv;
    }

    // construct an empty deque
    public Deque() {
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) 
            throw new IllegalArgumentException("item must not be null.");

        if (N == 0) {
            first = new Node();
            first.item = item;
            last = first;
            N++;
            return;
        }

        Node oldFirst = first; // save the old first
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        oldFirst.prv = first;
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) 
            throw new IllegalArgumentException("item must not be null.");

        if (N == 0) {
            last = new Node();
            last.item = item;
            first = last;
            N++;
            return;
        }

        Node newLast = new Node();
        newLast.item = item;
        last.next = newLast;
        newLast.prv = last;
        last = newLast;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (N == 0) 
            throw new NoSuchElementException("deque is empty.");

        Item x = first.item;
        //System.out.println("x: " + x);
        Node oldFirst = first;
        first = first.next;
        if (N == 1)
            last = null;
        else
            oldFirst.next.prv = null;
        N--;
        return x;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (N == 0) 
            throw new NoSuchElementException("deque is empty.");

        Item x = last.item;
        Node oldLast = last;
        last = last.prv;
        if (N == 1) first = null;
        else oldLast.prv.next = null;
        N--;
        return x; 
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is unsupported.");
        }

        public Item next() {
            if (current == null) 
                throw new NoSuchElementException("deque is empty.");

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        dq.isEmpty();
        dq.addFirst(4);
        dq.removeFirst();
        for (int x : dq) System.out.println(x);




        //Deque<Integer> dq = new Deque<Integer>();
        //dq.addLast(1);
        //dq.addFirst(2);
        //dq.addLast(3);
        //dq.removeFirst();
        //for (int x : dq) StdOut.println(x);


    }

}
