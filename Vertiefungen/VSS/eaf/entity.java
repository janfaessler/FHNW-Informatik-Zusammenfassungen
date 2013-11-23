@Entity
@Table(name = "CUSTOMERS")
public class Customer implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstName;
	@Column(name="NAME")
	private String lastName;
	protected Customer(){}
	public Customer(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		// id is not set!
	}
	public int getId() { return this.id; } // read only
	public String getFirstName() { return this.firstName; }
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() { return this.lastName; }
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}