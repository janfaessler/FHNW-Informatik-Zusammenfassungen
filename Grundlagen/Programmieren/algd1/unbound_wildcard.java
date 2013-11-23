Punkt<?>[] array = new Punkt<?>[5];

array[0] = new Punkt<Integer>(1,2);
array[1] = new Punkt<Double>(1.5, 2.5);
array[2] = new Punkt<String>("hoi", "du");

Object o = array[0].getX();

// Number n = array[0].getX();
// Integer i = array[0].getX();
// array[0].getX(5);