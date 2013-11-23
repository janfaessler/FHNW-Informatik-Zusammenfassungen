Punkt<? extends Number> ref;
ref = new Punkt<Integer>(1,2);
// ref = new Punkt<String>("hoi", "du");

Object o = ref.getX();
Number n = ref.getX();
// Integer i = ref.getX();
// ref.setX(5);