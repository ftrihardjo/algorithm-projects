import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet) { // constructor takes a WordNet object
	if (wordnet == null) {
	    throw new IllegalArgumentException("no wordnet");
	} else {
	    this.wordnet = wordnet;
	}
    }
    public String outcast(String[] nouns) { // given an array of WordNet nouns, return an outcast
	if (nouns == null || invalid_noun(nouns)) {
	    throw new IllegalArgumentException("invalid noun");
	} else {
	    int[] d = new int[nouns.length];
	    for (int i = 0; i < nouns.length; i++) {
		for (String x : nouns) {
		    d[i] += wordnet.distance(nouns[i],x);
		}
	    }
	    int t = 0;
	    for (int i = 1; i < nouns.length; i++) {
		if (d[t] < d[i]) {
		    t = i;
		}
	    }
	    return nouns[t];
	}
    }
    public static void main(String[] args) { // see test client below
	WordNet wordnet = new WordNet(args[0], args[1]);
	Outcast outcast = new Outcast(wordnet);
	for (int t = 2; t < args.length; t++) {
	    In in = new In(args[t]);
	    String[] nouns = in.readAllStrings();
	    StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	}
    }
    private boolean invalid_noun(String[] nouns) {
	for (String noun : nouns) {
	    if (noun == null) {
		return true;
	    }
	}
	return false;
    }
}
