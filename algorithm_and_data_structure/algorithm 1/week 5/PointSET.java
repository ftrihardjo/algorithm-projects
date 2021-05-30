import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;
    public PointSET() { // construct an empty set of points
	points = new SET<Point2D>();
	StdDraw.setPenRadius(0.01);
	StdDraw.setPenColor(StdDraw.BLACK);
    }
    public boolean isEmpty() { // is the set empty?
	return points.isEmpty();
    }
    public int size() { // number of points in the set
	return points.size();
    }
    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
	if (p == null) {
	    throw new IllegalArgumentException("no point");
	} else {
	    points.add(p);
	}
    }
    public boolean contains(Point2D p) { // does the set contain point p?
	if (p == null) {
	    throw new IllegalArgumentException("no point");
	} else {
	    return points.contains(p);
	}
    }
    public void draw() { // draw all points to standard draw
	for (Point2D p : points) {
	    p.draw();
	}
    }
    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
	if (rect == null) {
	    throw new IllegalArgumentException("no rectangle");
	} else {
	    ArrayList<Point2D> rectangle = new ArrayList<Point2D>();
	    for (Point2D p : points) {
		if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
		    rectangle.add(p);
		}
	    }
	    return rectangle;
	}
    }
    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
	if (p == null ) {
	    throw new IllegalArgumentException("no point");
	} else {
	    double d, min_distance = Double.POSITIVE_INFINITY;
	    Point2D r = null;
	    for (Point2D q : points) {
		if ((d = p.distanceSquaredTo(q)) < min_distance) {
		    r = q;
		    min_distance = d;
		}
	    }
	    return r;
	}
    }
}
