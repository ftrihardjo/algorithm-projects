import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private class Node {
        Node left, right;
	Point2D point;
	boolean orientation;
	Node(Point2D p, boolean orientation) {
	    point = p;
	    left = null;
	    right = null;
	    this.orientation = !orientation;
	}
    }
    private Node root;
    private int length;
    private ArrayList<Point2D> points;
    private Point2D q;
    private double d;
    private void traverse_range(Node node, RectHV rect) {
	if (node != null && node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax() && node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax()) {
	    points.add(node.point);
	}
	if (node != null && (node.orientation && rect.xmax() < node.point.x() || !node.orientation && rect.ymax() < node.point.y())) {
	    traverse_range(node.left,rect);
	} else if (node != null && (node.orientation && rect.xmin() > node.point.x() || !node.orientation && rect.ymin() > node.point.y())) {
	    traverse_range(node.right,rect);
	} else if (node != null) {
	    traverse_range(node.left,rect);
	    traverse_range(node.right,rect);
	}
    }
    private void traversal_draw(Node node, double x, double y, double X, double Y) {
	if (node != null) {
	    StdDraw.setPenRadius(0.01);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    node.point.draw();
	    StdDraw.setPenRadius();
	    if (node.orientation) {
		StdDraw.setPenColor(StdDraw.RED);
	        StdDraw.line(node.point.x(),y,node.point.x(),Y);
		traversal_draw(node.left,x,y,node.point.x(),Y);
		traversal_draw(node.right,node.point.x(),y,X,Y);
	    } else {		
		StdDraw.setPenColor(StdDraw.BLUE);
	        StdDraw.line(x,node.point.y(),X,node.point.y());
		traversal_draw(node.left,x,y,X,node.point.y());
		traversal_draw(node.right,x,node.point.y(),X,Y);
	    }
	}
    }
    private void nearest_traversal(Node node, Point2D p, double x, double y, double X, double Y) {
        if (node != null && new RectHV(x,y,X,Y).distanceSquaredTo(p) < d) {
	    if (node.point.distanceSquaredTo(p) < d) {
		q = node.point;
		d = node.point.distanceSquaredTo(p);
	    }
	    if (node.orientation && node.point.x() >= p.x()) {
		nearest_traversal(node.left,p,x,y,node.point.x(),Y);
		nearest_traversal(node.right,p,node.point.x(),y,X,Y);
	    } else if (!node.orientation && node.point.y() >= p.y()) {
		nearest_traversal(node.left,p,x,y,X,node.point.y());
		nearest_traversal(node.right,p,x,node.point.y(),X,Y);
	    } else if (node.orientation && node.point.x() < p.x()) {
		nearest_traversal(node.right,p,node.point.x(),y,X,Y);
		nearest_traversal(node.left,p,x,y,node.point.x(),Y);
	    } else if (!node.orientation && node.point.y() < p.y()) {
		nearest_traversal(node.right,p,x,node.point.y(),X,Y);
		nearest_traversal(node.left,p,x,y,X,node.point.y());
	    }
	}
    }
    public KdTree() { // construct an empty set of points
	root = null;
	length = 0;
    }
    public boolean isEmpty() { // is the set empty?
	return length == 0;
    }
    public int size() { // number of points in the set
	return length;
    }
    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
	if (p == null) {
	    throw new IllegalArgumentException("no point");
	} else if (isEmpty()) {
	    root = new Node(p,false);
	    length = 1;
	} else if (!contains(p)) {
	    Node node = root;
	    while (true) {
		double x = node.point.x(), X = p.x(), y = node.point.y(), Y = p.y();
		if (node.orientation && x >= X && node.left == null) {
		    node.left = new Node(p,node.orientation);
		    length += 1;
		    break;
		} else if (node.orientation && x >= X) {
		    node = node.left;
		} else if (node.orientation && x < X && node.right == null) {
		    node.right = new Node(p,node.orientation);
		    length += 1;
		    break;
		} else if (node.orientation && x < X) {
		    node = node.right;
		} else if (!node.orientation && y >= Y && node.left == null) {
		    node.left = new Node(p,node.orientation);
		    length += 1;
		    break;
		} else if (!node.orientation && y >= Y) {
		    node = node.left;
		} else if (!node.orientation && y < Y && node.right == null) {
		    node.right = new Node(p,node.orientation);
		    length += 1;
		    break;
		} else if (!node.orientation && y < Y) {
		    node = node.right;
		}
	    }
	}
    }
    public boolean contains(Point2D p) { // does the set contain point p?
	if (p == null) {
	    throw new IllegalArgumentException("no point");
	} else {
	    Node node = root;	    
	    while (node != null) {
		double x = node.point.x(), X = p.x(), y = node.point.y(), Y = p.y();
		if (node.point.equals(p)) {
		    return true;
		} else if (node.orientation && x >= X) {
		    node = node.left;
		} else if (node.orientation && x < X) {
		    node = node.right;
		} else if (!node.orientation && y >= Y) {
		    node = node.left;
		} else if (!node.orientation && y < Y) {
		    node = node.right;
		}
	    }
	    return false;
	}
    }
    public void draw() { // draw all points to standard draw
	traversal_draw(root,0.0,0.0,1.0,1.0);
    }
    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
	if (rect == null) {
	    throw new IllegalArgumentException("no rectangle");
	} else {
	    points = new ArrayList<Point2D>();
	    traverse_range(root,rect);
	    return points;
	}
    }
    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
	if (p == null ) {
	    throw new IllegalArgumentException("no point");
	} else {
	    d = Double.POSITIVE_INFINITY;
	    q = null;
	    nearest_traversal(root,p,0.0,0.0,1.0,1.0);
	    return q;
	}
    }
}
