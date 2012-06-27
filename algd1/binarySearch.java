boolean binarySearch(double[] array, double x) {
  int first=0, last=array.length-1, m;
  while(first <= last) {
    m = first + (last - first) / 2;  //schneller (m=(first+last)>>>1)
    if(array[m] == x) return true;
    else if (array[m] < x) first=m+1;
    else last=m-1;
  }
}