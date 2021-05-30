public class CircularSuffixArray {
    private final int length;
    private int[] index;
	
    private final int CUTOFF =  15;   // cutoff to insertion sort

    private boolean unary_alphabet(String s) {
	for (int i = 1; i < s.length(); i++) {
	    if (s.charAt(i) != s.charAt(i-1)) {
		return false;
	    }
	}
	return true;
    }

    private void sort(String s) {
	if (!unary_alphabet(s)) {
	    sort(s, 0, s.length()-1, 0);
	}
    }

    private int charAt(String s, int d) { 
        return s.charAt(d%s.length());
    }

    // 3-way string quicksort a[lo..hi] starting at dth character
    private void sort(String s, int lo, int hi, int d) { 

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            for (int i = lo; i <= hi; i++)
		for (int j = i; j > lo && less(s, index[j], index[j-1], d); j--)
		    exch(j, j-1);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(s, index[lo]+d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(s, index[i]+d);
            if      (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else	i++;
        }
        sort(s, lo, lt-1, d);
        if (v >= 0) sort(s, lt, gt, d+1);
        sort(s, gt+1, hi, d);
    }

    // exchange a[i] and a[j]
    private void exch(int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }

    // is v less than w, starting at character d
    private boolean less(String s, int j, int k, int d) {
        for (int i = d; i < s.length() - d; i++) {
            if (charAt(s,j+i) < charAt(s,k+i)) return true;
            if (charAt(s,j+i) > charAt(s,k+i)) return false;
        }
        return false;
    }
    // circular suffix array of s
    public CircularSuffixArray(String s) {
	if (s == null) {
	    throw new IllegalArgumentException("invalid string");
	} else {
	    length = s.length();
	    if (length > 0) {
		this.index = new int[length];
		for (int i = 0; i < length; i++)
		    index[i] = i;
		sort(s);
	    }
	}
    }
    // length of s
    public int length() {
	return length;
    }
    // returns index of ith sorted suffix
    public int index(int i) {
	if (i < 0 || i >= length()) {
	    throw new IllegalArgumentException("invalid index");
	} else {
	    return index[i];
	}
    }
    // unit testing (required)
    public static void main(String[] args) {
	CircularSuffixArray CSA  = new CircularSuffixArray(args[0]);
	System.out.println(CSA.length());
	for (int i = 0; i < CSA.length(); i++) {
	    System.out.print(CSA.index(i)+" ");
	}
    }
}
