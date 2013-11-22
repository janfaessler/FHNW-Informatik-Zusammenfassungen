// J2SE: using factory
EntityManagerFactory emf =
	Persistence.createEntityManagerFactory("movierental");
EntityManager em = emf.createEntityManager();

// J2EE: injected by container
@PersistenceUnit(name="movierental")
EntityManagerFactory emf = null;

@PersistenceContext(unitName="movierental")
private EntityManager em;
// => container managed entity manager