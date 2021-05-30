import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;

public class BoggleSolver
{
    private class Trie {
	private final int R = 26;

	private Node root;      // root of trie

	// R-way trie node
	private class Node {
	    Node[] next = new Node[R];
	}
	/**
	 * Does the set contain the given key?
	 * @param key the key
	 * @return {@code true} if the set contains {@code key} and
	 *     {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(String key) {
	    if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	    return get(root, key, 0) != null;
	}

	private Node get(Node x, String key, int d) {
	    if (x == null) return null;
	    if (d == key.length()) return x;
	    return get(x.next[key.charAt(d)-'A'], key, d+1);
	}

	/**
	 * Adds the key to the set if it is not already present.
	 * @param key the key to add
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void add(String key) {
	    if (key == null) throw new IllegalArgumentException("argument to add() is null");
	    root = add(root, key, 0);
	}

	private Node add(Node x, String key, int d) {
	    if (x == null) x = new Node();
	    if (d < key.length()) {
		char c = key.charAt(d);
		x.next[c-'A'] = add(x.next[c-'A'], key, d+1);
	    }
	    return x;
	}
    }
    private HashSet<String> valid_words, words;
    private Trie dictionary;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
	this.dictionary = new Trie();
	words = new HashSet<String>();
	for (int i = 0; i < dictionary.length; i++) {
	    this.dictionary.add(dictionary[i]);
	    words.add(dictionary[i]);
	}
    }
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
	valid_words = new HashSet<String>();
	boolean[][] visited = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
	    for (int j = 0; j < board.cols(); j++) {
		find_all_valid_words(board,visited,i,j,"");
	    }
	}
	return valid_words;
    }
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
	if (word.length() <= 2 || !words.contains(word)) {
	    return 0;
	} else if (word.length() == 3 || word.length() == 4) {
	    return 1;
	} else if (word.length() == 5) {
	    return 2;
	} else if (word.length() == 6) {
	    return 3;
	} else if (word.length() == 7) {
	    return 5;
	}
	return 11;
    }
    private void find_all_valid_words(BoggleBoard board, boolean[][] visited, int row, int column, String word) {
	if (board.getLetter(row,column) == 'Q') {
	    word += "QU";
	} else {
	    word += board.getLetter(row,column);
	}
	if (dictionary.contains(word)) {
	    if (word.length() >= 3 && words.contains(word)) {
		valid_words.add(word);
	    }
	    visited[row][column] = true;
	    if (row+1 < board.rows() && !visited[row+1][column]) {
		find_all_valid_words(board,visited,row+1,column,word);
	    }
	    if (row-1 >= 0 && !visited[row-1][column]) {
		find_all_valid_words(board,visited,row-1,column,word);
	    }
	    if (column+1 < board.cols() && !visited[row][column+1]) {
		find_all_valid_words(board,visited,row,column+1,word);
	    }
	    if (column-1 >= 0 && !visited[row][column-1]) {
		find_all_valid_words(board,visited,row,column-1,word);
	    }
	    if (row+1 < board.rows() && column+1 < board.cols() && !visited[row+1][column+1]) {
		find_all_valid_words(board,visited,row+1,column+1,word);
	    }
	    if (row-1 >= 0 && column+1 < board.cols() && !visited[row-1][column+1]) {
		find_all_valid_words(board,visited,row-1,column+1,word);
	    }
	    if (row+1 < board.rows() && column-1 >= 0 && !visited[row+1][column-1]) {
		find_all_valid_words(board,visited,row+1,column-1,word);
	    }
	    if (row-1 >= 0 && column-1 >= 0 && !visited[row-1][column-1]) {
		find_all_valid_words(board,visited,row-1,column-1,word);
	    }
	    visited[row][column] = false;
	}
    }
    public static void main(String[] args) {
	In in = new In(args[0]);
	String[] dictionary = in.readAllStrings();
	BoggleSolver solver = new BoggleSolver(dictionary);
	BoggleBoard board = new BoggleBoard(args[1]);
	int score = 0;
	for (String word : solver.getAllValidWords(board)) {
	    StdOut.println(word);
	    score += solver.scoreOf(word);
	}
	StdOut.println("Score = " + score);
    }
}
