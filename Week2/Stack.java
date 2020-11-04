import java.util.Iterator;
public class Stack<Item>  implements Iterable<Item> {
    private int N;
    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    public Stack() {
        N = 0;
    }

    public Item pop() {
        Item x = first.item;
        first = first.next;
        return x;
    }

    public void push(Item item) {
        if (N == 0) {
            first = new Node();
            first.item = item;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
        }

        N++;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {
        private Node curr = first;
        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
        }

        public Item next() {
            Item x = curr.item;
            curr = curr.next;
            return x;
        }


    }


    public static void main(String[] args) {
        Stack<Integer> st = new Stack<Integer>();
        st.push(2);
        st.push(3);
        st.push(1);
        st.push(3);
        System.out.println(st.pop());
    }

}
