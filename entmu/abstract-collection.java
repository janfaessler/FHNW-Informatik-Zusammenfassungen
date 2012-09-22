abstract class AbstractCollection<E> implements Collection<E> {
	public abstract Iterator<E> iterator();
	public abstract boolean add(E x);
	public boolean isEmpty() { return size() == 0; }
	public int size() {
		int n = 0; Iterator<E> it = iterator();
		while (it.hasNext()) { 
			it.next(); n++; 
		}
		return n;
	}
	public boolean contains(Object o) {
		Iterator<E> e = iterator();
		while (e.hasNext()) { 
			Object x = e.next();
			if(x == o || (o != null) && o.equals(x)) return true;
		}
		return false;
	}
	public void clear() {
		Iterator<E> it = iterator();
		while (it.hasNext()) { 
			it.next(); 
			it.remove();
		}
	}
	public boolean remove(Object o) {
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			Object x = it.next();
			if(x == o || (o != null && o.equals(x))) {
				it.remove();
				return true;
			}
		}
		return false;
	}
	public boolean containsAll(Collection<?> c) { 
		Iterator<?> it = c.iterator();
		while (it.hasNext()) {
			if(!contains(it.next())) return false;
		}
		return true;
	}
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		Iterator<? extends E> it = c.iterator();
		while (it.hasNext()) { 
			if(add(it.next())) modified = true;
		}
		return modified;
	}