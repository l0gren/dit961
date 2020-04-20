import java.util.*;
import java.util.HashMap;

// A priority queue.
public class PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<>();
	private Comparator<E> comparator;
	private HashMap<E, Integer> positions = new HashMap<>();

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	public void add(E elem) {
		heap.add(elem);
		int index = heap.size()-1;
		updatePosition(elem, index);
		siftUp(index);
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		this.positions.remove(heap.get(0));
		E lastElem = heap.get(heap.size()-1);
		heap.set(0, lastElem);
		updatePosition(lastElem, 0);
		heap.remove(heap.size()-1);
		if (heap.size() > 0) siftDown(0);
	}

	// Updates the value of elem in the queue
	public void update(E oldElem, E newElem) {
		Integer index = this.positions.get(oldElem);
		if(index == null)
			throw new NoSuchElementException();

		heap.set(index, newElem);
		updatePosition(newElem, index);
		if(comparator.compare(newElem, oldElem) < 0 ) {
			siftUp(index);
		} else {
			siftDown(index);
		}
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {
		int original = index; //See if item has been shifted
		E elem = heap.get(index);

		// Stop when parent is root
		while(parent(index) >= 0 && index > 0) {
			int parent = parent(index);
			E parentElem = heap.get(parent);
			if(comparator.compare(elem, parentElem) < 0) {
				heap.set(index, parentElem);
				updatePosition(parentElem, index);
				index = parent;
			} else break;
		}

		if(index != original) {
			heap.set(index, elem);
			updatePosition(elem, index);
		}
	}
     
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
		int original = index; //See if item has been shifted
		E elem = heap.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childElem = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightElem = heap.get(right);
				if (comparator.compare(childElem, rightElem) > 0) {
					child = right;
					childElem = rightElem;
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(elem, childElem) > 0) {
				heap.set(index, childElem);
				updatePosition(childElem, index);
				index = child;
			} else break;
		}


		if(index != original) {
			heap.set(index, elem);
			updatePosition(elem, index);
		}
	}

	// Update position in the hash map of registered indices of
	// elem with new position index
	private void updatePosition(E elem, Integer index) {
		// Check if elem is registered; put in map if not
//		System.out.println(elem.toString() + ", Index: " + index);
		if(this.positions.get(elem) == null) {
			this.positions.put(elem, index);
		} else {
			this.positions.replace(elem, index);
		}
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}
}
