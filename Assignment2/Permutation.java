import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by kandronovs on 17.19.4.
 */

public class Permutation {
	/**
	 * Unit tests the {@code Queue} data type.
	 *
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {
		RandomizedQueue<String> queue = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			queue.enqueue(StdIn.readString());
		}
		int k = Integer.parseInt(args[0]);
		for (int i = 0; i < k; i++) {
			StdOut.println(queue.dequeue());
		}
	}
}
