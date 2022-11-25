package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**	
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element 
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		if(last == null) {
			last = new QueueNode<>(e);
			last.next = last;
		}
		else {
			var newNode = new QueueNode<>(e);
			newNode.next = last.next;
			last.next = newNode;
			last = newNode;
		}

		size += 1;
		return true;
	}
	
	/**	
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queue
	 */
	public int size() {		
		return size;
	}
	
	/**	
	 * Retrieves, but does not remove, the head of this queue, 
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null 
	 * 			if this queue is empty
	 */
	public E peek() {
		if(last == null) return null;
		return last.next.element;
	}

	/**	
	 * Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty 
	 */
	public E poll() {
		if(size == 0) return null;
		if(size == 1) {
			var e = last.element;
			last = null;
			size -= 1;
			return e;
		}

		var top = last.next;
		last.next = top.next;
		size -= 1;
		return top.element;
	}

	/**
	 * Appends the specified queue to this queue
	 * post: all elements from the specified queue are appended
	 * to this queue. The specified queue (q) is empty after the call.
	 * @param q the queue to append
	 * @throws IllegalArgumentException if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) throws IllegalArgumentException {
		if(this == q) throw new IllegalArgumentException("You cannot append this to this");

		if(q.last == null) return;
		if(last == null) {
			last = q.last;
			size = q.size;
		}
		else {
			var qBegin = q.last.next;
			q.last.next = last.next;
			last.next = qBegin;
			last = q.last;
			size += q.size;
		}

		q.last = null;
		q.size = 0;
	}
	
	/**	
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */	
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> m_pos;

		private QueueIterator() {
			m_pos = last;
		}
		public boolean hasNext() {
			return m_pos != null;
		}
		public E next() {
			if(!hasNext()) throw new NoSuchElementException("No new next node");

			m_pos = m_pos.next;
			var e =m_pos.element;

			if(m_pos == last) m_pos = null;
			return e;
		}
	}
	
	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

}
