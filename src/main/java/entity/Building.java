package entity;




import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "Building address cannot be blank!")
//    @Size(max = 20, message = "Building address has to be with up to 20 characters!")
    @Column(name = "address", nullable = false)
    private String address;

//    @Positive
    @Column(name = "stories", nullable = false)
    private int storiesNumber;

//    @Positive
    @Column(name = "number", nullable = false)
    private int apartmentsNumber;
    @OneToOne(mappedBy = "building")
    private BuildingAgreement buildingAgreement;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private Map<Integer,Apartment> apartments;

    public Building() {

        this.apartments = new HashMap<>();

    }

    public Building(String address, int storiesNumber, int apartmentsNumber) {
        this.address = address;
        this.storiesNumber = storiesNumber;
        this.apartmentsNumber = apartmentsNumber;
        this.apartments = new HashMap<>();

    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }

    public int getStoriesNumber() {
        return storiesNumber;
    }

    public int getApartmentsNumber() {
        return apartmentsNumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BuildingAgreement getBuildingAgreement() {
        return buildingAgreement;
    }

    public Map<Integer,Apartment> getApartments() {
        return apartments;
    }

    public  void showApartments(){
        apartments.values().forEach(System.out::println);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBuildingAgreement(BuildingAgreement buildingAgreement) {
        this.buildingAgreement = buildingAgreement;
    }


    public void addApartment(Apartment apartment, int floor){
        if(apartments.size() < apartmentsNumber)
            if(floor <= storiesNumber){
                apartments.put(floor,apartment);
                apartment.setBuilding(this);}

    }
    public void removeApartment(Apartment apartment, int floor){
        apartments.remove(apartment,floor);
    }
    public void showResidents(){
        apartments.values().forEach((apartment -> apartment.getResidents().forEach(System.out::println)));

    }


}

