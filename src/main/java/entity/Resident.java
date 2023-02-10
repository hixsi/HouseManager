package entity;



import javax.persistence.*;
import java.util.Comparator;


@Entity
@Table(name = "resident")
public class Resident implements Comparable<Resident> {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "Resident name cannot be blank!")
//    @Size(max = 20, message = "Resident name has to be with up to 20 characters!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @NotBlank(message = "Resident name cannot be blank!")
//    @Size(max = 20, message = "Resident name has to be with up to 20 characters!")
    @Column(name = "last_name", nullable = false)
    private String lastName;

//    @Positive
    @Column(name = "age", nullable = false)
    private int age;
    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
    @Column(name = "elevator", nullable = false)
    private boolean useElevator;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Resident() {

    }
    public Resident(String firstName, String lastName, int age, Apartment apartment, boolean useElevator) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.apartment = apartment;
        this.useElevator = useElevator;
    }

    public static Comparator<Resident> ResidentAge = (o1, o2) -> Integer.compare(o1.getAge(),o2.getAge());
    public static Comparator<Resident> ResidentFirstName = (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName());
    public static Comparator<Resident> ResidentID = (o1, o2) -> Long.compare(o1.id,o2.id);

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public boolean useElevator() {
        return useElevator;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", useElevator=" + useElevator +
                '}';
    }

    @Override
    public int compareTo(Resident o) {
        return this.firstName.compareTo(o.getFirstName());
    }
}
