boolean gefunden = false;
int last = (array.length-1);
if (array[last]==x) {
  gefunden=true;
} else {
  int tmp = array[last];
  array[last]=x;
  int i=0;
  while(array[i] != x) i++;
  gefunden = (i < last);
  array[last] = tmp;
}