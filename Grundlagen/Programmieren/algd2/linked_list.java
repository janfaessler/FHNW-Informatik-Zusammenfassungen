public class LinkedList<T> {
	private Element<T> head = null;
	private Element<T> last = null;
	public void add(T data) {
		last.next = new Element<T>(data);
		last = last.next;
	}
	public void remove(T data) {
		Element<T> current = head;
		while (current != null && current.data != data) {
			current = current.next;
			if (current != null && current.data == data) {
				current.last = current.next;
				current = null;
			}
		}
	}
	public T getFirst() {
		return head.data;
	}
	public T getLast() {
		return last.data;
	}
	public class Element<E> {
		public Element<E> next;
		public Element<E> last;
		public E data;	
		public Element(E input) {
			data = input;
		}
	}
}