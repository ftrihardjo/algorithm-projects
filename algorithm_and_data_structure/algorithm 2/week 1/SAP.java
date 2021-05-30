import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    private Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
	if (G == null) {
	    throw new IllegalArgumentException("no graph");
	} else {
	    this.G = new Digraph(G);
	}
    }
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
	BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(G,v), y = new BreadthFirstDirectedPaths(G,w);
	int d, shortest_path = -1;
	for (int i = 0; i < G.V(); i++) {
	    if (x.hasPathTo(i) && y.hasPathTo(i)) {
		d = x.distTo(i)+y.distTo(i);
		if (d < shortest_path || shortest_path == -1) {
		    shortest_path = d;
		}
	    }
	}
	return shortest_path;
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
	BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(G,v), y = new BreadthFirstDirectedPaths(G,w);
	int d, shortest_path = -1, closest_ancestor = -1;
	for (int i = 0; i < G.V(); i++) {
	    if (x.hasPathTo(i) && y.hasPathTo(i)) {
		d = x.distTo(i)+y.distTo(i);
		if (d < shortest_path || shortest_path == -1) {
		    shortest_path = d;
		    closest_ancestor = i;
		}
	    }
	}
	return closest_ancestor;
    }
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null || invalid_vertices(v) || invalid_vertices(w)) {
	    throw new IllegalArgumentException("empty sets");
	} else {
	    BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(G,v), y = new BreadthFirstDirectedPaths(G,w);
	    int shortest_path = -1, d;
	    for (int i = 0; i < G.V(); i++) {
		if (x.hasPathTo(i) && y.hasPathTo(i)) {
		    d = x.distTo(i)+y.distTo(i);
		    if (d < shortest_path || shortest_path == -1) {
			shortest_path = d;
		    }
		}
	    }
	    return shortest_path;
	}
    }
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
	if (v == null || w == null || invalid_vertices(v) || invalid_vertices(w)) {
	    throw new IllegalArgumentException("empty sets");
	} else {
	    BreadthFirstDirectedPaths x = new BreadthFirstDirectedPaths(G,v), y = new BreadthFirstDirectedPaths(G,w);
	    int shortest_path = -1, d, closest_ancestor = -1;
	    for (int i = 0; i < G.V(); i++) {
		if (x.hasPathTo(i) && y.hasPathTo(i)) {
		    d = x.distTo(i)+y.distTo(i);
		    if (d < shortest_path || shortest_path == -1) {
			shortest_path = d;
			closest_ancestor = i;
		    }
		}
	    }
	    return closest_ancestor;
	}
    }
    private boolean invalid_vertices(Iterable<Integer> v) {
	for (Integer w : v) {
	    if (w == null || w.intValue() >= G.V()) {
		return true;
	    }
	}
	return false;
    }
}
