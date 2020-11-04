import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;


import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N; // size
    private int capacity;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        capacity = 2;
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // resize the items array
    private void resize(int N) {
        //StdOut.println("resizing with" +  N);
        Item[] copy = (Item[]) new Object[N];
        int it = 0;
        for (int i = 0; i < capacity; i++) {
            if (items[i] != null) {
                copy[it++] = items[i];
            }
        }
        capacity = N;
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item must not be null.");

        if (N == capacity) resize(capacity*2);
        items[N++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) 
            throw new NoSuchElementException("queue is empty.");

        if (N == capacity/4) resize(capacity/2);
        
        int rn = StdRandom.uniform(N);
        Item item = items[rn]; // will return this item
        items[rn] = items[--N];  // put the last one in its place
        items[N] = null;        // will not use this anymore
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0)
            throw new NoSuchElementException("queue is empty.");

        int rn = StdRandom.uniform(N);
        return items[rn];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int idx;
        private int[] rn; 

        public RandomizedQueueIterator() {
            idx = 0;

            rn = new int[N];
            for (int i = 0; i < N; i++) 
                rn[i] = i;

            StdRandom.shuffle(rn);
        }

        public boolean hasNext() {
            return idx < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[rn[idx++]];
        }
            
    }


    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("is empty?: " + rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(2);
        System.out.println("size: " + rq.size());
        System.out.println("is empty?: " + rq.isEmpty());
        rq.enqueue(20);
        rq.enqueue(25);
        System.out.println("dequeue: " + rq.dequeue());
        System.out.println("sample: " + rq.sample());
        rq.enqueue(3);
        rq.enqueue(5);
        for (int x : rq) System.out.print(x + " ");
    }

}
