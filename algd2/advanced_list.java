public class AdvancedComparableList<T extends Comparable<T>> implements Iterable<T> {
	private ListElement<T> head, foot;
	private int size = 0;
	public T getFirst() { return head.data; }
	public T getLast() { return foot.data; }
	public int size() { return size; }
	public boolean contains(T data) {
		boolean found = false;
		CListIterator<T> it = this.iterator();
		while (!found && it.hasNext()) if (it.next().equals(data)) found = true;
		return found;
	}
	public void add(T data) { add(size, data); }
	public void add(int index, T data) {
		if (index > size) throw new IndexOutOfBoundsException();
		else if (!this.contains(data)) {
			ListElement<T> newElement = new ListElement<T>(data);
			ListElement<T> current = head;
			if (size == 0) {
				head = newElement;
				foot = head;
			} else if (index == size) {
				newElement.last = foot;
				foot.next = newElement;
				foot = newElement;
			} else if(index == 0) {
				newElement.next = current;
				current.last = newElement;
				head = newElement;
			} else {
				for (int i=0; i<index; i++) current = current.next;
				newElement.next = current;
				newElement.last = current.last;
				if (current.last != null) current.last.next = newElement;
				current.last = newElement;
			}
			size++;
		}
	}
	public T remove() { return remove(size-1); }
	public T remove(T data) throws Exception {
		if (this.contains(data)) return remove(this.indexOf(data));
		else throw new Exception("Element "+data+" does not exist.");
	}
	public T remove(int index) {
		if (index >= size) throw new IndexOutOfBoundsException();
		T element = get(index);
		if (index == 0) {
			if (size > 1) head = head.next;
			else { head = null; foot = null; }
		} else if (index == (size-1)) {
			foot.last.next = null;
			foot = foot.last;		
		} else {
			ListElement<T> current = head;
			for (int i=0; i<index; i++) current = current.next;
			current.last.next = current.next;
			if (current.next != null) current.next.last = current.last;
			current = null;
		}
		return element;
	}
	public int indexOf(T data) {
		int index = -1;
		if (this.contains(data)) { index++; while(!this.get(index).equals(data)) index++; }
		return index;
	}
	public T get(int index) {
		T element = null;
		if (index >= 0 && index < size) element = this.iterator(index).next();
		return element;
	}
	public class ListElement<T extends Comparable<T>> implements Comparable<T> {
		public ListElement<T> next, last;
		public T data;
		public ListElement(T input) { data = input; }
		public int compareTo(T o) { return data.compareTo(o); }
		public String toString() { return String.valueOf(data) + ">"+String.valueOf(next); }
	}
}
