import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int n, row, column, Hamming, Manhattan;
    public Board(int[][] blocks) { // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
	n = blocks[0].length;
        Hamming = 0;
	Manhattan = 0;
	board = new int[n][n];
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		board[i][j] = blocks[i][j];
		if (blocks[i][j] != 0 && i*n+j+1 != blocks[i][j]) {
		    Hamming += 1;
		    Manhattan += Math.abs(i-(blocks[i][j]-1)/n)+Math.abs(j-(blocks[i][j]-1)%n);
		} else if (blocks[i][j] == 0) {
		    row = i;
		    column = j;
		}
	    }
	}
    }
    public int dimension() { // board dimension n
	return n;
    }
    public int hamming() { // number of blocks out of place
	return Hamming;
    }
    public int manhattan() { // sum of Manhattan distances between blocks and goal
	return Manhattan;
    }
    public boolean isGoal() { // is this board the goal board?
	return manhattan() == 0 || hamming() == 0;
    }
    public Board twin() { // a board that is obtained by exchanging any pair of blocks
	int row1, column1, row2, column2;
        int temp = row*n+column;
	if (temp == 0) {
	    row1 = (temp+1)/n;
	    column1 = (temp+1)%n;
	    row2 = (temp+2)/n;
	    column2 = (temp+2)%n;
	} else if (temp == n*n-1) {
	    row1 = (temp-1)/n;
	    column1 = (temp-1)%n;
	    row2 = (temp-2)/n;
	    column2 = (temp-2)%n;
	} else {
	    row1 = (temp+1)/n;
	    column1 = (temp+1)%n;
	    row2 = (temp-1)/n;
	    column2 = (temp-1)%n;
	}
	temp = board[row1][column1];
	board[row1][column1] = board[row2][column2];
	board[row2][column2] = temp;
        Board x = new Board(board);
	temp = board[row1][column1];
	board[row1][column1] = board[row2][column2];
	board[row2][column2] = temp;
	return x;
    }
    public boolean equals(Object y) { // does this board equal y?
	if (y instanceof Board && ((Board)y).n == n) {
	    for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
		    if (board[i][j] != ((Board)y).board[i][j]) {
			return false;
		    }
		}
	    }
	    return true;
	}
	return false;
    }
    public Iterable<Board> neighbors() { // all neighboring boards
	ArrayList<Board> neighbours = new ArrayList<Board>();
	int temp;
	if (row > 0) {
	    temp = board[row-1][column];
	    board[row-1][column] = board[row][column];
	    board[row][column] = temp;
	    neighbours.add(new Board(board));
	    temp = board[row-1][column];
	    board[row-1][column] = board[row][column];
	    board[row][column] = temp;
	}
	if (row < n-1) {
	    temp = board[row+1][column];
	    board[row+1][column] = board[row][column];
	    board[row][column] = temp;
	    neighbours.add(new Board(board));
	    temp = board[row+1][column];
	    board[row+1][column] = board[row][column];
	    board[row][column] = temp;
	}
	if (column > 0) {
	    temp = board[row][column-1];
	    board[row][column-1] = board[row][column];
	    board[row][column] = temp;
	    neighbours.add(new Board(board));
	    temp = board[row][column-1];
	    board[row][column-1] = board[row][column];
	    board[row][column] = temp;
	}
	if (column < n-1) {
	    temp = board[row][column+1];
	    board[row][column+1] = board[row][column];
	    board[row][column] = temp;
	    neighbours.add(new Board(board));
	    temp = board[row][column+1];
	    board[row][column+1] = board[row][column];
	    board[row][column] = temp;
	}
	return neighbours;
    }
    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
	s.append(n + "\n");
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		s.append(String.format("%2d ", board[i][j]));
	    }
	    s.append("\n");
	}
	return s.toString();
    }
}
