import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
	String s = BinaryStdIn.readString();
        char[] string = new char[s.length()];
        CircularSuffixArray CSA = new CircularSuffixArray(s);
	int i;
	for (i = 0; CSA.index(i) != 0; i++) {
	    string[i] = s.charAt(CSA.index(i)-1);
	}
	for (string[i] = s.charAt(CSA.length()-1), BinaryStdOut.write(i++); i < CSA.length(); i++) {
	    string[i] = s.charAt(CSA.index(i)-1);
	}
	BinaryStdOut.write(new String(string));
	BinaryStdOut.close();
	BinaryStdIn.close();
    }
    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
	// read first and t[]
	int first = BinaryStdIn.readInt();
	String s = BinaryStdIn.readString();
	int N = s.length();
	int R = 256;
	// allocate the ending array
	char[] t = new char[N];
	for (int i = 0; i < N; i++)
	    t[i] = s.charAt(i);
	// allocate an array to store the next array
	int[] next = new int[N]; 
	// allocate an array to store 1st char of the sorted suffixes
	char[] f = new char[N]; 
	// an array to store the total count for each character
	int[] count = new int[R+1];
	// do key-index counting, but store values in the next[] array
	for (int i = 0; i < N; i++)
	    count[t[i]+1]++;
	for (int r = 0; r < R; r++)
	    count[r+1] += count[r];
	for (int i = 0; i < N; i++) {
	    next[count[t[i]]] = i;
	    f[count[t[i]]++] = t[i];
	}
	// write out
	for (int i = 0, current = first; i < N; i++, current = next[current])
	    BinaryStdOut.write(f[current]);
	BinaryStdIn.close();
	BinaryStdOut.close();
    }
    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
	if (args[0].equals("-")) {
	    transform();
	} else {
	    inverseTransform();
	}
    }
}
