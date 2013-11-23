for(int first=1; first<a.length;first++) {
  tmp=a[first];
  k=first-1;
  while(k>=0 && a[k]>tmp) {
    a[k+1]=a[k];
    k--;
  }
  a[k+1] = tmp;
}