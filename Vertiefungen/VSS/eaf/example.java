@Entity
public class Rental implements Serializable { @Id
	private int id;
	@ManyToOne // Rental is the owner of the relationship
	@JoinColumn(name="USER_FK") // optional
	// JPA macht bei OneToMany und ManyToOne eine Zwischentable. 
	// Mit Keyword JoinColumn wird dies unterbunden. => Foreign Key = Owning side
	private User user;
	public Rental(){}
	public user getUser() { return user; }
	public void setUser (Customer user) {
		this.user = user;
	}
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
}