int k, max; //k = index of max ; max = value of max
for (int last = a.length - 1; last > 0; last--) {
  k = 0;
  max = a[k];
  for (int j = 1; j <= last; j++) {
    if (a[j] > max) {
      max = a[k = j];
    }
  }
  if(k!=last){
    a[k] = a[last];
    a[last] = max;
  }
}