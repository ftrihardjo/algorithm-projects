import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private Digraph G;
    private SAP S;
    private HashMap<String,ArrayList<Integer>> BST;
    private ArrayList<String> ST; 
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
	if (synsets == null || hypernyms == null) {
	    throw new IllegalArgumentException("invalid files");
	} else {
	    In in = new In(synsets);
	    int V = 0;
	    String[] s = null;
	    BST = new HashMap<String,ArrayList<Integer>>();
	    ST = new ArrayList<String>();
	    while (in.hasNextLine()) {
		s = in.readLine().split(",");
		ST.add(new Integer(s[0]).intValue(),s[1]);
	        V = Math.max(V,new Integer(s[0]).intValue());
		for (String string : s[1].split(" ")) {
		    if (BST.containsKey(string)) {
			ArrayList<Integer> AL = BST.get(string);
			AL.add(new Integer(s[0]));
			BST.put(string,AL);
		    } else {
			ArrayList<Integer> AL = new ArrayList<Integer>();
			AL.add(new Integer(s[0]));
			BST.put(string,AL);
		    }
		}
	    }
	    G = new Digraph(V+1);
	    in = new In(hypernyms);
	    while (in.hasNextLine()) {
	        s = in.readLine().split(",");
		for (int i = 1; i < s.length; i++) {
		    G.addEdge(new Integer(s[0]).intValue(),new Integer(s[i]).intValue());
		}
	    }
	    if (new Topological(G).hasOrder() && rooted(G)) {		
		S = new SAP(G);
	    } else {
		throw new IllegalArgumentException("invalid graph");
	    }
	}
    }
    // returns all WordNet nouns
    public Iterable<String> nouns() {
	return BST.keySet();
    }
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
	if (word == null) {
	    throw new IllegalArgumentException("invalid word");
	} else {
	    return BST.containsKey(word);	    
	}
    }
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
	if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
	    throw new IllegalArgumentException("invalid nouns");
	} else {
	    return S.length(BST.get(nounA),BST.get(nounB));
	}
    }
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
	if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
	    throw new IllegalArgumentException("invalid nouns");
	} else {
	    return ST.get(S.ancestor(BST.get(nounA),BST.get(nounB)));
	}
    }
    private boolean rooted(Digraph G) {
	int roots = 0;
	for (int i = 0; i < G.V(); i++) {
	    if (G.outdegree(i) == 0) {
		roots += 1;
	    }
	}
        return roots == 1;
    }
}
