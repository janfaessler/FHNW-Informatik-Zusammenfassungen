em.getTransaction().begin();
Customer c = new Customer();
Order o1 = new Order();
Order o2 = new Order();
List<Order> orders = new LinkedList<Order>();
orders.add(o1); orders.add(o2);
c.setOrders(orders);
em.persist(c);
em.getTransaction().commit();