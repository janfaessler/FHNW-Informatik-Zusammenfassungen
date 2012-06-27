int maxTSumme(int[] a, int left, int right) {
	if (left > right) return 0;
	if (left == right) return (a[left] >= 0 ? A[left] : 0);
	int m = (left + right) >>> 1;
	int v1 = maxTSumme(a, left, m)
	int v2 = maxTSumme(a, m+1, right);
	int sum=0; rmax=0; lmax=0;
	for (int i=m+1; i <= right; i++) {
		sum += a[i];
		if (sum > rmax) rmax =  sum;
	}
	sum = 0;
	for (int i=m; i>=left; i--) {
		sum += a[i];
		if (sum > lmax) lmax = sum;
	}
	int v3 = lmax + rmax;
	return max(v1, v2, v3);
}