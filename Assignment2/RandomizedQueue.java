import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by kandronovs on 17.19.4.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    // number of elements
    private int size;
    // array of items
    private Item[] array;

    // construct an empty randomized queue
    /**
     * Initializes an empty queue.
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
    }

    // an iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {

        private int i = size;
        private int[] randomOrder;

        public RandomQueueIterator() {
            randomOrder = new int[i];
            for (int j = 0; j < i; ++j) {
                randomOrder[j] = j;
            }
            StdRandom.shuffle(randomOrder);
        }

        public boolean hasNext() {

            return i > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return array[randomOrder[--i]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Is this queue empty?
     * 
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the queue.
     * 
     * @return the number of items on the queue
     */
    public int size() {
        return size;
    }

    // resize the underlying array holding the elements.
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= size;

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    // add the item
    /**
     * Adds the item.
     * 
     * @param item
     *            the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        array[size++] = item; // Add item
        if (size == array.length) {
            resize(2 * array.length); // double size of array if necessary
        }

    }

    /**
     * Removes and returns the item.
     * 
     * @return the item most recently added
     * @throws java.util.NoSuchElementException
     *             if this queue is empty
     */
    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int random = StdRandom.uniform(size);
        Item item = array[random];
        array[random] = array[size - 1];

        // Avoid loitering
        array[--size] = null;

        // shrink size of array if necessary
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }

        return item;
    }

    /**
     * Return (but do not remove) a random item.
     * 
     * @return the item
     * @throws java.util.NoSuchElementException
     *             if this queue is empty
     */
    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int random = StdRandom.uniform(size);
        return array[random];
    }

    /*
     * Return an independent iterator over items in random order.
     * 
     * @return an independent iterator over items in random order.
     */
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    /**
     * Unit tests the {@code Queue} data type.
     *
     * @param args
     *            the command-line arguments
     */
    // public static void main(String[] args) {
    // RandomizedQueue<String> queue = new RandomizedQueue<>();
    // while (!StdIn.isEmpty()) {
    // String item = StdIn.readString();
    // if (!item.equals("-"))
    // queue.enqueue(item);
    // else if (!queue.isEmpty())
    // StdOut.print(queue.dequeue() + " ");
    // }
    // StdOut.println("(" + queue.size() + ") left on queue");
    // }
}
