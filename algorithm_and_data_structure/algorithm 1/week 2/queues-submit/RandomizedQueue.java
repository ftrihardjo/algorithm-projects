import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueIterator implements Iterator<Item>  {
	private int m;
	private Object[] copy;
	public RandomizedQueueIterator() {
	    copy = new Object[length];
	    for (int i = 0; i < length; i++) {
		copy[i] = random[i];
	    }
	    m = length;
	}
	public boolean hasNext() {
	    return m != 0;
	}
	public void remove() {
	    throw new UnsupportedOperationException("unallowed removal");
	}
	public Item next() {
	    if (hasNext()) {
		int n = StdRandom.uniform(m);
		Object item = copy[n];
		copy[n] = copy[--m];
		return (Item)item;
	    } else {
		throw new NoSuchElementException("empty");		
	    }
	}
    }
    private Object[] random;
    private int length, capacity;
    public RandomizedQueue() { // construct an empty randomized queue
	random = new Object[1];
	length = 0;
	capacity = 1;
    }
    public boolean isEmpty() { // is the randomized queue empty?
	return length == 0;
    }
    public int size() { // return the number of items on the randomized queue
	return length;
    }
    public void enqueue(Item item) { // add the item
	if (item == null) {
	    throw new IllegalArgumentException("empty");
	} else {
	    if (capacity == length) {
		capacity *= 2;
		Object[] copy = new Object[capacity];
		for (int i = 0; i < length; i++) {
		    copy[i] = random[i];
		}
		random = copy;
	    }
	    random[length++] = item;
	}
    }
    public Item dequeue() { // remove and return a random item
	if (isEmpty()) {
	    throw new NoSuchElementException("empty");
	} else {
	    int n = StdRandom.uniform(length);
	    Object item = random[n];
	    random[n] = random[--length];
	    random[length] = null;
	    if (length > 0 && length == capacity/4) {
		capacity /= 2;
		Object[] copy = new Object[capacity];
		for (int i = 0; i < length; i++) {
		    copy[i] = random[i];
		}
		random = copy;
	    }
	    return (Item)item;
	}
    }
    public Item sample() { // return a random item (but do not remove it)
	if (isEmpty()) {
	    throw new NoSuchElementException("empty");
	} else {
	    return (Item)random[StdRandom.uniform(size())];
	}
    }
    public Iterator<Item> iterator() { // return an independent iterator over items in random order
	return new RandomizedQueueIterator();
    }
}
