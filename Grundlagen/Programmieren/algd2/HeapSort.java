public class HeapSort {
	static int counter = 0;
	public static void main(String[] args) {
		int[] array = {3, 5, 1, 8, 2, 4, 7 , 6, 10, 12, 1337};
		heapsort(array);
	}
	private static int[] heapsort(int[] a) {
		int n  = a.length;
		// bring the array into heap form
		for(int i = n/2-1; i >= 0; i--) siftDown(a, i, n-1);
		// sort the heap
		for(int i = n-1; i >= 1; i --) {
			swap(a, 0, i);
			siftDown(a, 0, i-1);
		}
		return a;
	}
	private static void swap(int[] a, int i, int j) {
		int temp = a[i]; a[i] = a[j]; a[j] = temp;
	}
	private static void siftDown(int[] a, int i, int m) {
		int j;
		while(2*i+1 < m) {
			j = 2*i+1;
			if(j < m) {
				// check if right is bigger
				if(a[j] < a[j+1]) j++;
				// check if the element has to be swapped
				if(a[i] < a[j]) { swap(a, i, j); i = j; } 
				else { i = m; }
			}
		}
	}
}
