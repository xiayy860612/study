import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] array;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        array[size++] = item;
        if (size == array.length) {
            resize(2 * size);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item item = get(index);
        array[index] = array[--size];
        array[size] = null;
        if (size <= array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        return get(index);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(this);
    }

    private void resize(int capacity) {
        Object[] newArray = new Object[capacity];
        for (int i = 0; i < size; ++i) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    private Item get(int index) {
        return (Item) array[index];
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private final RandomizedQueue<Item> queue;
        private final int[] order;
        private int index;

        public RandomizedQueueIterator(RandomizedQueue<Item> queue) {
            this.queue = queue;
            this.order = new int[queue.size];
            for (int i = 0; i < queue.size; ++i) {
                order[i] = i;
            }
            StdRandom.shuffle(this.order);
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < order.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return queue.get(this.order[index++]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> items = new RandomizedQueue<>();
        items.enqueue("hello");
        items.enqueue("world");
        items.enqueue("test");
        for (String item : items) {
            StdOut.println(item);
        }
    }

}
