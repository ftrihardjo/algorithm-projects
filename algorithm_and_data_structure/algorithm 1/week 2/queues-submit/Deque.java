import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
	Node next;
	Node previous;
	Item item;
    }
    private class DequeIterator implements Iterator<Item>  {
	private Node node = head;
	public boolean hasNext() {
	    return node != null;
	}
	public void remove() {
	    throw new UnsupportedOperationException("unallowed removal");
	}
	public Item next() {
	    if (hasNext()) {
		Item item = node.item;
		node = node.next;
		return item;
	    } else {
		throw new NoSuchElementException("empty");		
	    }
	}
    }
    private Node head, tail;
    private int length;
    public Deque() { // construct an empty deque
	head = null;
	tail = null;
	length = 0;
    }
    public boolean isEmpty() { // is the deque empty?
	return length == 0;
    }
    public int size() { // return the number of items on the deque
	return length;
    }
    public void addFirst(Item item) { // add the item to the front
	if (item == null) {
	    throw new IllegalArgumentException("no item");
	} else {
	    Node node = new Node();
	    node.item = item;
	    node.previous = null;
	    node.next = head;
	    head = node;
	    length += 1;
	    if (tail == null) {
		tail = head;
	    } else {
		node.next.previous = node;
	    }
	}
    }
    public void addLast(Item item) { // add the item to the end
	if (item == null) {
	    throw new IllegalArgumentException("no item");
	} else {
	    Node node = new Node();
	    node.item = item;
	    node.next = null;
	    node.previous = tail;
	    tail = node;
	    length += 1;
	    if (head == null) {
		head = tail;
	    } else {
		node.previous.next = node;
	    }
	}
    }
    public Item removeFirst() { // remove and return the item from the front
	if (length == 0) {
	    throw new NoSuchElementException("empty");
	} else {
	    Item item = head.item;
	    head = head.next;
	    length -= 1;
	    if (length == 0) {
		tail = null;
	    } else {
		head.previous = null;
	    }
	    return item;	    
	}
    }
    public Item removeLast() { // remove and return the item from the end
	if (length == 0) {
	    throw new NoSuchElementException("empty");
	} else {
	    Item item = tail.item;
	    tail = tail.previous;
	    length -= 1;
	    if (length == 0) {
		head = null;
	    } else {
		tail.next = null;
	    }
	    return item;
	}
    }
    public Iterator<Item> iterator() { // return an iterator over items in order from front to end
	return new DequeIterator();
    }
}
