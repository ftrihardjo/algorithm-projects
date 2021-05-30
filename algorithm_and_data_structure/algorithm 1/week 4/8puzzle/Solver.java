import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private class Chapman implements Comparable<Chapman> {
	Chapman predecessor;
	Board board;
	int priority, distance, move;
	Chapman(Chapman predecessor, Board board, int distance, int move) {
	    this.predecessor = predecessor;
	    this.board = board;
	    this.distance = distance;
	    this.move = move;
	    priority = move+distance;
	}
	public int compareTo(Chapman x) {
	    return priority-x.priority;
	}
	public boolean equals(Object x) {
	    return board.equals(x);
	}
    }
    private class Comparable_Board implements Comparable<Comparable_Board> {
	Board board;
	Comparable_Board(Board board)  {
	    this.board = board;
	}
	public int compareTo(Comparable_Board x) {
	    return board.manhattan()-x.board.manhattan();
	}
	public boolean equals(Object x) {
	    return board.equals(x);
	}
    }
    private boolean solved;
    private int steps;
    private Stack<Board> stack;
    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
	if (initial == null) {
	    throw new IllegalArgumentException("no board");
	} else {
	    Board twin = initial.twin();
	    MinPQ<Chapman> x, y;
	    x = new MinPQ<Chapman>();
	    y = new MinPQ<Chapman>();
	    if (initial.isGoal()) {
		solved = true;
		steps = 0;
		stack = new Stack<Board>();
	        stack.push(initial);
	    } else if (twin.isGoal()) {
		solved = false;
		steps = -1;
	    } else {
		for (Board z : initial.neighbors()) {
		    x.insert(new Chapman(new Chapman(null,initial,initial.manhattan(),0),z,z.manhattan(),1));
		}
		for (Board z : twin.neighbors()) {
		    y.insert(new Chapman(new Chapman(null,twin,twin.manhattan(),0),z,z.manhattan(),1));
		}
		Chapman X = x.delMin(), Y = y.delMin();
		while (!X.board.isGoal() && !Y.board.isGoal()) {
		    for (Board z : X.board.neighbors()) {
			if (!z.equals(X.predecessor.board)) {
			    x.insert(new Chapman(X,z,z.manhattan(),X.move+1));
			}
		    }
		    for (Board z : Y.board.neighbors()) {
			if (!z.equals(Y.predecessor.board)) {
			    y.insert(new Chapman(Y,z,z.manhattan(),Y.move+1));
			}
		    }
		    X = x.delMin();
		    Y = y.delMin();
		}
		if (X.board.isGoal()) {
		    solved = true;
		    steps = X.move;
		    stack = new Stack<Board>();
		    while (X != null) {
			stack.push(X.board);
			X = X.predecessor;
		    }
		} else {
		    solved = false;
		    steps = -1;
		    stack = null;
		}
	    }
	}
    }
    public boolean isSolvable() { // is the initial board solvable?
	return solved;
    }
    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
	return steps;
    }
    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
	return stack;
    }
    public static void main(String[] args) {
	// create initial board from file
	In in = new In(args[0]);
	int n = in.readInt();
	int[][] blocks = new int[n][n];
	for (int i = 0; i < n; i++)
	    for (int j = 0; j < n; j++)
		blocks[i][j] = in.readInt();
	Board initial = new Board(blocks);
	// solve the puzzle
	Solver solver = new Solver(initial);
	// print solution to standard output
	if (!solver.isSolvable())
	    StdOut.println("No solution possible");
	else {
	    StdOut.println("Minimum number of moves = " + solver.moves());
	    for (Board board : solver.solution())
		StdOut.println(board);
	}
    }
}
