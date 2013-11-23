void quicksort (int[] a) {
	if (a.length > 1) sort(a,0,a.length-1);
}
void sort(int[] a, int l, int r) {
	int i=l, j=r;
	int p = Math.random(a, l, r),
	do {
		while (a[i] < p) i++;
		while (a[j] > p) j--;
		if (i <= j) {
			int tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
			i++;
			j--;
		}
	} while (i < j);
	if (l < j) sort(a, l, j);
	if (i < r) sort(a, i, r);
}