import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int EXTENDED_ASCII = 256, BYTE = 8;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] ascii = new char[EXTENDED_ASCII];
	int[] ascii_index = new int[EXTENDED_ASCII];
	for (int i = 0; i < EXTENDED_ASCII; i++) {
	    ascii[i] = (char)(i+'\0');
	    ascii_index[i] = i;
	}
	while (!BinaryStdIn.isEmpty()) {
	    int c = BinaryStdIn.readChar()-'\0';
	    BinaryStdOut.write(ascii_index[c],BYTE);
	    char temp = ascii[ascii_index[c]];	    
	    for (int i = ascii_index[c]; i > 0; ascii[i] = ascii[--i], ascii_index[ascii[i]]++);
	    ascii_index[c] = 0;
	    ascii[ascii_index[c]] = temp;
	}
	BinaryStdIn.close();
	BinaryStdOut.close();
    }
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
	char[] ascii = new char[EXTENDED_ASCII];
	for (int i = 0; i < EXTENDED_ASCII; i++) {
	    ascii[i] = (char)(i+'\0');
	}
	while (!BinaryStdIn.isEmpty()) {
	    int index = BinaryStdIn.readInt(BYTE);	    
	    char temp = ascii[index];
	    for (BinaryStdOut.write(ascii[index]); index > 0; ascii[index] = ascii[--index]);
	    ascii[index] = temp;
	}
	BinaryStdIn.close();
	BinaryStdOut.close();
    }
    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
	if (args[0].equals("-")) {
	    encode();
	} else {
	    decode();
	}
    }
}
