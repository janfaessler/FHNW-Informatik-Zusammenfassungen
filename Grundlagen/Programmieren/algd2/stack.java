public class Stack<T> extends LinkedList<T> {
	public T top() {
		return this.getLast();
	}
	public void push(T data) {
		this.add(data);
	}
	public T pop() {
		T last = this.getLast();
		this.remove(last);
		return last;
	}
	public boolean isEmpty() {
		return this.getFirst() == null ? true : false;
	}
}