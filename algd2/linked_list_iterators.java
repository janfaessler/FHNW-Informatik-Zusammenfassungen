	public CListIterator<T> iterator() { return new CListIterator<T>(head, this); }
	public CListIterator<T> iterator(int index) { 
		if (index >= size) throw new IndexOutOfBoundsException();
		CListIterator<T> it = this.iterator();
		for (int i=0; i<index; i++) it.next();
		return it;
	}
	public static class CListIterator<E extends Comparable<E>> implements ListIterator<E> {
		private ListElement<E> nextElement, prevElement, lastReturned;
		private AdvancedComparableList<E> _list;
		private int index = 0;
		public CListIterator(ListElement<E> element, AdvancedComparableList<E> list) {
			_list = list;
			nextElement = element;
			prevElement = (element == null?null:element.last);
			ListElement<E> current = element;
			while (current != null && current.last != null) { 
				index++; 
				current = current.last;
			}
		}
		public boolean hasNext() {  return nextElement != null; }
		public E next() {
			index++;
			prevElement = nextElement;
			nextElement = nextElement.next;
			lastReturned = prevElement;
			return prevElement.data;
		}
		public boolean hasPrevious() { return prevElement != null; }
		public E previous() {
			index--;
			nextElement = prevElement;
			prevElement = prevElement.last;
			lastReturned = nextElement;
			return nextElement.data;
		}
		public int nextIndex() { return index; }
		public int previousIndex() { return index-1; }
		public void add(E data) { _list.add(previousIndex(), data); lastReturned = null; }
		public void remove() { _list.remove(previousIndex()); lastReturned = null; }
		public void set(E e) { lastReturned.data = e; }
	}
