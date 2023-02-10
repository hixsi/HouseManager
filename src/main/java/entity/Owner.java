package entity;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "Owner name cannot be blank!")
//    @Size(max = 20, message = "Owner name has to be with up to 20 characters!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @NotBlank(message = "Owner name cannot be blank!")
//    @Size(max = 20, message = "Owner name has to be with up to 20 characters!")
    @Column(name = "last_name", nullable = false)
    private String lastName;
//    @OneToMany(mappedBy = "owner")
//    private Set<Apartment> apartments;



    public Owner( ) {

    }

    public Owner( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
//        this.apartments = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

//    public Set<Apartment> getApartments() {
//        return apartments;
//    }

    @Override
    public String toString() {
        return "Owner{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
//                ", apartments=" + apartments +
                '}';
    }
}
