	/*    sorting     */
	public void sort() { mergesort(0, this.size() - 1); }
	private void mergesort(int low, int high) {
		if (low < high) {
			if (high-low > 1) {
				int middle = (low + high) / 2;
				mergesort(low, middle);
				mergesort(middle + 1, high);
				merge(low, middle, high);
			} else {
				if (this.get(low).compareTo(this.get(high)) > 0) {
					T tmp = this.get(high);
					this.remove(high);
					this.add(low, tmp);
				}
			}
		}
	}
	private void merge(int low, int middle, int high) {
		int iLeft = low, iRight = middle+1;
		while (iLeft <= high && iRight <= high) {
			T right = get(iRight);
			if (get(iLeft).compareTo(right) > 0) {
				remove(iRight);
				add(iLeft, right);
				iRight++;
			} else iLeft++;
		}
	}