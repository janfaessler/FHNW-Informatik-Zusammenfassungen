TypedQuery<Movie> q = em.createQuery(
	"select m from Movie m where m.title = :title",
Movie.class);
q.setParameter("title", title);
List<Movie> movies = q.getResultList();

@NamedQueries({
	@NamedQuery(name="movie.all", query="from Movie"),
	@NamedQuery(name="movie.byTitle",
		query="select m from Movie m where m.title = :title")
})
class Movie {...}

TypedQuery<Movie> q = em.createNamedQuery(
	"movie.byTitle", Movie.class);
q.setParameter("title", title);
List<Movie> movies = q.getResultList();

SELECT c FROM Customer c WHERE c.address.city = 'Basel'
SELECT c.name, c.prename FROM Customer c
SELECT DISTINCT c.address.city FROM Customer c
SELECT NEW ch.fhnw.edu.Person(c.name,c.prename) FROM Customer c
SELECT pk FROM PriceCategory pk

TypedQuery<Movie> q = em.createQuery(
	"select m from Movie m order by m.name", Movie.class);
q.setFirstResult(20);
q.setMaxResults(10);
List<Movie> movies = q.getResultList();

Query q = em.createQuery(
	"delete from Movie m where m.id > 1000");
int result = q.executeUpdate();