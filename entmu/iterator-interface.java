interface Iterator<E> { 
	boolean hasNext();     // there is an element which can be jumped over
	E next();              // returns the jumped over element
	void remove();         // removes the last element returned by next
}