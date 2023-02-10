package entity;




import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "employee")
public class Employee implements Comparable<Employee> {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "Company name cannot be blank!")
//    @Size(max = 20, message = "Company name has to be with up to 20 characters!")
    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @NotBlank(message = "Employee name cannot be blank!")
//    @Size(max = 20, message = "Employee name has to be with up to 20 characters!")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "age")
    private int age;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "number_of_buildings")
    private int numberOfBuildingsManaged;

    @OneToMany(mappedBy = "employee")

    private List<Building> buildingList;

    @Transient

    private Map<Building,Map<Apartment,Double>> buildingsPaid;
    @Transient

    private Map<Building,Map<Apartment,Double>> buildingsNotPaid;


    public Employee() {
        this.buildingsPaid = new HashMap<>();
        this.buildingsNotPaid = new HashMap<>();
        this.buildingList = new ArrayList<>();
    }

    public Employee(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.buildingsPaid = new HashMap<>();
        this.buildingsNotPaid = new HashMap<>();
        this.buildingList = new ArrayList<>();
        this.numberOfBuildingsManaged = 0;
    }

    public static Comparator<Employee> getEmployeeNumberBuildings() {
        return EmployeeNumberBuildings;
    }

    public static Comparator<Employee> getEmployeeLastName() {
        return EmployeeLastName;
    }

    public static Comparator<Employee> EmployeeFirstName = (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName());
    public static Comparator<Employee> EmployeeNumberBuildings = (o1, o2) -> Integer.compare(o1.getNumberOfBuildingsManaged(),o2.getNumberOfBuildingsManaged());
    public static Comparator<Employee> EmployeeLastName = (o1, o2) -> o1.getLastName().compareTo(o2.getLastName());
    public static Comparator<Employee> EmployeeID = (o1, o2) -> Long.compare(o1.id,o2.id);

    public void addBuilding(Building building) {
        buildingsNotPaid.put(building, new HashMap());
        buildingsPaid.put(building,new HashMap<>());
        buildingList.add(building);
    }

    public void addBuildingToEmployee(Building building){
        if(company.getBuildings().contains(building)) {
            Map<Apartment, Double> apartmentsTax = new HashMap<>();
            for (Apartment apartment : building.getApartments().values()) {
                apartmentsTax.put(apartment, building.getBuildingAgreement().getCompany().taxApartment(apartment));

            }
            addBuilding(building);
            buildingsNotPaid.get(building).putAll(apartmentsTax);
            numberOfBuildingsManaged++;
        }
    }

    public void addBuildingsToEmployee(List<Building> buildingList){
        for(Building building: buildingList){
            addBuildingToEmployee(building);
        }
    }

    public long getId() {
        return id;
    }
    public int getNumberOfBuildingsManaged(){
        return numberOfBuildingsManaged;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public int getAge() {
        return age;
    }

    public Company getCompany() {
        return company;
    }

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public Map<Building, Map<Apartment, Double>> getBuildingsPaid() {
        return buildingsPaid;
    }

    public Map<Building, Map<Apartment, Double>> getBuildingsNotPaid() {
        return buildingsNotPaid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    // преместване от списък с неплатени такси към списък с платени или обратното
    public void moveFromListToList(Map<Building,Map<Apartment,Double>> list1, Map<Building,Map<Apartment,Double>> list2, Apartment apartment, Double taxOrPayment){

        Double currentTax = 0.0;
        Building building = apartment.getBuilding();
        Map<Apartment,Double> apartments = new HashMap<>();



        if(list2.containsKey(building) ){

            list2.get(building).put(apartment, taxOrPayment);
        }
        else{
            apartments.put(apartment, taxOrPayment);
            list2.put(building,apartments);
        }
        list1.get(building).remove(apartment,list1.get(building).get(apartment));


    }
    // Такси за нов месец
    public void newTaxMonth(Building building){
        Double newMonthTax = 0.0;
        Double currentTax = 0.0;

        if(building.getBuildingAgreement().getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
            if(buildingsNotPaid.containsKey(building)){
                for (Apartment apartment: buildingsNotPaid.get(building).keySet()){

                    newMonthTax = apartment.getBuilding().getBuildingAgreement().getCompany().taxApartment(apartment);
                    currentTax = buildingsNotPaid.get(building).get(apartment);
                    buildingsNotPaid.get(building).replace(apartment,  currentTax + newMonthTax );
                }}

            if(buildingsPaid.containsKey(building)){
                for (Apartment apartment: buildingsPaid.get(building).keySet()){
                    newMonthTax = building.getBuildingAgreement().getCompany().taxApartment(apartment);
                    moveFromListToList(buildingsPaid , buildingsNotPaid, apartment, newMonthTax);

                }}



        }

    }
    // Записване на плащането
    public void savePaidTax(Apartment apartment, double sum, LocalDate date){
        Double currentTax;

        if(buildingsNotPaid.keySet().contains(apartment.getBuilding())){

            currentTax = buildingsNotPaid.get(apartment.getBuilding()).get(apartment);
            if(currentTax - sum <= 0 ) {

                moveFromListToList(buildingsNotPaid, buildingsPaid,apartment,sum );
            this.company.writeToFilePayment(this,apartment.getBuilding(),apartment,date);
            }

        }
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", company=" + company +
                ", numberOfBuildingsManaged=" + numberOfBuildingsManaged +
                ", buildingsPaid=" + buildingsPaid +
                ", buildingsNotPaid=" + buildingsNotPaid +
                '}';
    }

    @Override
    public int compareTo(Employee o) {
        return this.firstName.compareTo(o.firstName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
