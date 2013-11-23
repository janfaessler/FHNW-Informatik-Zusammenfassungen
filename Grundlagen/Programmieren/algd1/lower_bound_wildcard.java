Punkt<? super Integer> ref;
ref = new Punkt<Integer>(1,2);
// ref = new Punkt<Double>(1.5, 2.5);

Object o = ref.getX();
// Number n = ref.getX();
// Integer i = ref.getX();
ref.setX(5);