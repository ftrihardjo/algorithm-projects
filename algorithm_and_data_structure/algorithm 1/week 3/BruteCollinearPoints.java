import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
	int p, q, r, s, maximum, minimum;
	boolean valid_points, null_points;
	Point start, end;
	segments = new ArrayList<LineSegment>();
	for (p = 0, null_points = false; points != null && p < points.length && !null_points; p++) {
	    null_points = (points[p] == null);
	}
	for (p = 0, valid_points = true; points != null && p < points.length && valid_points && !null_points; p++) {
	    for (q = p+1; q < points.length && valid_points; q++) {
	        valid_points = (points[p].compareTo(points[q]) != 0);
	    }
	}
	if (points != null && valid_points && !null_points) {
	    for (p = 0; p < points.length; p++) {
		for (q = p+1; q < points.length; q++) {
		    for (r = q+1; r < points.length; r++) {
			for (s = r+1; s < points.length; s++) {
			    if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
				start = points[p];
				end = points[p];
				if (start.compareTo(points[q]) > 0) {
				    start = points[q];
				}
				if (start.compareTo(points[r]) > 0) {
				    start = points[r];
				}
				if (start.compareTo(points[s]) > 0) {
				    start = points[s];
				}
				if (end.compareTo(points[q]) < 0) {
				    end = points[q];
				}
				if (end.compareTo(points[r]) < 0) {
				    end = points[r];
				}
				if (end.compareTo(points[s]) < 0) {
				    end = points[s];
				}
				segments.add(new LineSegment(start,end));
			    }
			}
		    }
		}
	    }
	} else {
	    throw new IllegalArgumentException("invalid points");
	}
    }
    public int numberOfSegments() { // the number of line segments
	return segments.size();
    }
    public LineSegment[] segments() { // the line segments
	return segments.toArray(new LineSegment[numberOfSegments()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	for (LineSegment segment : collinear.segments()) {
	    StdOut.println(segment);
	    segment.draw();
	}
	StdDraw.show();
    }
}
