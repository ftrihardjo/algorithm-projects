import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
	RandomizedQueue<String> random = new RandomizedQueue<String>();
	while (!StdIn.isEmpty()) {
	    random.enqueue(StdIn.readString());
	}
	for (int n = new Integer(args[0]).intValue(); n > 0; n--) {
	    StdOut.println(random.dequeue());
	}
    }
}
