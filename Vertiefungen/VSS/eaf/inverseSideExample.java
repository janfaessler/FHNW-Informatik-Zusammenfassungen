@Entity
public class User implements Serializable {
	...
	@OneToMany(mappedBy="user") private Collection<Rental> rentals;
	// this is the inverse side of the relationship
	public Collection<Rental> getRentals() {
		return rentals;
	}
	public void setRentals(Collection<Rental> rentals) {
		this.rentals = rentals;
	}
}