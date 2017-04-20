import java.util.Iterator;

/**
 * @author kandronovs
 */
public class Deque<Item> implements Iterable<Item> {
	private int size; // size of the stack
	private Node first, last; // top of the stack

	// Helper linked list class
	private class Node {
		private Item item;
		private Node next;
		private Node previous;
	}

	/**
	 * Initializes an empty queue.
	 */
	public Deque() {
		first = null;
		last = null;
		size = 0;
		assert check();
	}

	/**
	 * is the deque empty?
	 * 
	 * @return true if this queue is empty; false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Return the number of items on the deque.
	 * 
	 * @return the number of items in the deque
	 */
	public int size() {
		return size;
	}

	// add the item to the front
	/**
	 * Adds the item to this deque in first position.
	 * 
	 * @param item
	 *            the item to add at first position
	 */
	public void addFirst(Item item) {

		if (item == null) {
			throw new java.lang.NullPointerException();
		}

		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		first.previous = null;

		if (isEmpty()) {
			last = first;
		} else {
			oldfirst.previous = first;
		}

		size++;
		assert check();
	}

	// add the item to the end
	/**
	 * Adds the item to this deque in the last position.
	 * 
	 * @param item
	 *            the item to add to deque in last position
	 */
	public void addLast(Item item) {

		if (item == null) {
			throw new java.lang.NullPointerException();
		}

		Node oldfirst = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.previous = oldfirst;

		if (isEmpty()) {
			first = last;
		} else {
			oldfirst.next = last;
		}

		size++;
		assert check();
	}

	/**
	 * Remove and return the item from the front.
	 * 
	 * @return the first item of the deque
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item removeFirst() {

		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Item item = first.item;
		first = first.next;
		size--;

		if (isEmpty()) {
			last = first;
		} else {
			first.previous = null;
		}
		return item;
	}

	/**
	 * Remove and return the item from the end.
	 * 
	 * @return the last item of the deque.
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item removeLast() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}

		Item item = last.item;
		last = last.previous;
		size--;

		if (isEmpty()) {
			first = last;
		} else {
			last.next = null;
		}
		return item;
	}

	/**
	 * Return an iterator over items in order from front to end
	 * 
	 * @return an iterator to this deque that iterates through the items in LIFO
	 *         order.
	 */
	public Iterator<Item> iterator() {
		return new DequeIterator() {
		};
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// check internal invariants
	private boolean check() {
		if (size < 0) {
			return false;
		}
		if (size == 0) {
			if (first != null)
				return false;
		} else if (size == 1) {
			if (first == null)
				return false;
			if (first.next != null)
				return false;
		} else {
			if (first == null)
				return false;
			if (first.next == null)
				return false;
		}

		int numberOfNodes = 0;
		for (Node x = first; x != null && numberOfNodes <= size; x = x.next) {
			numberOfNodes++;
		}
		if (numberOfNodes != size)
			return false;

		return true;
	}
}
