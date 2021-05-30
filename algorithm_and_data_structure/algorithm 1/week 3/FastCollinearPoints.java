import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private class Lines {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;

	private Node root;
	private int index = 0;
	private boolean created = false;
	private LineSegment[] segments, lines;
	private class Node {
	    Point start, end;
	    double slope;
	    Node left, right;
	    boolean color;
	    int size;

	    Node(Point start, Point end, boolean color, int size) {
		this.start = start;
		this.end = end;
		slope = start.slopeTo(end);
		this.color = color;
		this.size = size;
	    }
	}
	public Lines() {
	}
	private boolean isRed(Node x) {
	    if (x == null) return false;
	    return x.color == RED;
	}
	private int size(Node x) {
	    if (x == null) return 0;
	    return x.size;
	}
	public int size() {
	    return size(root);
	}
	public void put(Point start, Point end) {
	    if (start == null || end == null) throw new IllegalArgumentException("first argument to put() is null");
	    root = put(root, start, end, start.slopeTo(end));
	    root.color = BLACK;
	}
	private Node put(Node h, Point start, Point end, double slope) { 
	    if (h == null) return new Node(start, end, RED, 1);

	    int cmp;
	    if (slope > h.slope) {
		cmp = 1;
	    } else if (slope < h.slope) {
		cmp = -1;
	    } else {
		if ((cmp = start.compareTo(h.start)) != 0) {
		} else if ((cmp = end.compareTo(h.end)) != 0) {
		} else {
		    cmp = 0;
		}
	    }
	    if      (cmp < 0) h.left  = put(h.left,  start, end, slope); 
	    else if (cmp > 0) h.right = put(h.right, start, end, slope);

	    if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
	    if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
	    if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
	    h.size = size(h.left) + size(h.right) + 1;

	    return h;
	}
	private Node rotateRight(Node h) {
	    Node x = h.left;
	    h.left = x.right;
	    x.right = h;
	    x.color = x.right.color;
	    x.right.color = RED;
	    x.size = h.size;
	    h.size = size(h.left) + size(h.right) + 1;
	    return x;
	}
	private Node rotateLeft(Node h) {
	    Node x = h.right;
	    h.right = x.left;
	    x.left = h;
	    x.color = x.left.color;
	    x.left.color = RED;
	    x.size = h.size;
	    h.size = size(h.left) + size(h.right) + 1;
	    return x;
	}
	private void flipColors(Node h) {
	    h.color = !h.color;
	    h.left.color = !h.left.color;
	    h.right.color = !h.right.color;
	}
	public LineSegment[] create_segments() {
	    if (!created) {
		segments = new LineSegment[size()];
		lines = new LineSegment[size()];
		traverse(root);
		created = true;
	    }
	    for (int i = 0; i < size(); i++) {
		lines[i] = segments[i];
	    }
	    return lines;
	}
        private void traverse(Node h) {
	    if (h != null) {
		traverse(h.left);
		segments[index++] = new LineSegment(h.start,h.end);
		traverse(h.right);
	    }
	}
    }
    private Lines line_segments;
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
	boolean valid_points, null_points;
	int i, j, same;
        Point start, end;
	line_segments = new Lines();
	for (i = 0, null_points = false; points != null && i < points.length && !null_points; i++) {
	    null_points = (points[i] == null);
	}
	for (i = 0, valid_points = true; points != null && i < points.length && valid_points && !null_points; i++) {
	    for (j = i+1; j < points.length && valid_points; j++) {
		valid_points = (points[i].compareTo(points[j]) != 0);
	    }
	}
	if (points != null && valid_points && !null_points) {
	    Point[] new_points = new Point[points.length-1];
	    for (i = 0; i < points.length && points.length >= 4 && valid_points; i++) {
		for (j = 0; j < points.length && points[j].compareTo(points[i]) != 0; j++) {
		    new_points[j] = points[j];
		}
		for (j++; j < points.length; j++) {
		    new_points[j-1] = points[j];
		}
		Arrays.sort(new_points,points[i].slopeOrder());
		if (points[i].compareTo(new_points[0]) > 0) {
		    end = points[i];
		    start = new_points[0];
		} else {
		    end = new_points[0];
		    start = points[i];
		}
		for (j = 1, same = 2; j < new_points.length; j++) {
		    if (start.slopeTo(end) == start.slopeTo(new_points[j])) {
			same += 1;
			if (start.compareTo(new_points[j]) > 0) {
			    start = new_points[j];
		        }
			if (end.compareTo(new_points[j]) < 0) {
			    end = new_points[j];
			}
		    } else {
			if (same >= 4) {
			    line_segments.put(start,end);
			}
		        if (points[i].compareTo(new_points[j]) > 0) {
			    end = points[i];
			    start = new_points[j];
			} else {
			    end = new_points[j];
			    start = points[i];
			}
			same = 2;
		    }
		}
		if (same >= 4) {
		    line_segments.put(start,end);
		}
	    }
	} else {
	    throw new IllegalArgumentException("invalid points");
	}
    }
    public int numberOfSegments() { // the number of line segments
	return line_segments.size();
    }
    public LineSegment[] segments() { // the line segments
	return line_segments.create_segments();
    }
    public static void main(String[] args) {
	// read the n points from a file
	In in = new In(args[0]);
	int n = in.readInt();
	Point[] points = new Point[n];
	for (int i = 0; i < n; i++) {
	    int x = in.readInt();
	    int y = in.readInt();
	    points[i] = new Point(x, y);
	}
	// draw the points
	StdDraw.enableDoubleBuffering();
	StdDraw.setXscale(0, 32768);
	StdDraw.setYscale(0, 32768);
	for (Point p : points) {
	    p.draw();
	}
	StdDraw.show();
	// print and draw the line segments
	FastCollinearPoints collinear = new FastCollinearPoints(points);
	for (LineSegment segment : collinear.segments()) {
	    StdOut.println(segment);
	    segment.draw();
	}
	StdDraw.show();
    }
}
