import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * 12n + 3 * 4 = 12n + 12
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> old = first;
        first = new Node<>(item, null, old);
        if (old == null) {
            last = first;
        } else {
            old.prev = first;
        }
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> old = last;
        last = new Node<>(item, old, null);
        if (old == null) {
            first = last;
        } else {
            old.next = last;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> old = first;
        first = old.next;
        old.next = null;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        --size;
        return old.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> old = last;
        last = old.prev;
        old.prev = null;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        --size;
        return old.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }

    /**
     * 3 * 4 bytes = 12 bytes
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> prev = null;
        private Node<Item> next = null;

        public Node(Item item, Node<Item> prev, Node<Item> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private static class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item next = this.current.item;
            current = this.current.next;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        boolean isAddFirst = true;
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            switch (input) {
                case "-rf":
                {
                    StdOut.println(deque.removeFirst());
                    break;
                }
                case "-rl":
                {
                    StdOut.println(deque.removeLast());
                    break;
                }
                case "-af":
                {
                    isAddFirst = true;
                    break;
                }
                case "-al":
                {
                    isAddFirst = false;
                    break;
                }
                default:
                {
                    if (isAddFirst) {
                        deque.addFirst(input);
                    } else {
                        deque.addLast(input);
                    }
                }
            }
        }
        StdOut.println();
        for (String item : deque) {
            StdOut.println(item);
        }
    }

}