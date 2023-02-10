package entity;



import org.hibernate.engine.spi.Managed;

import javax.persistence.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;


@Entity

@Table(name = "company")
public class Company implements Comparable<Company>{
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "Company name cannot be blank!")
//    @Size(max = 20, message = "Company name has to be with up to 20 characters!")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tax_for_pet", nullable = false)
    private double taxPet;

    @Column(name = "tax_for_elevator", nullable = false)
    private double taxElevator;

    @Transient
    private Map<Integer,Double> taxArea;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company")
    private List<Building> buildings;

    @OneToMany(mappedBy = "company")
    private List<BuildingAgreement> buildingAgreements;


    public Company() {

        this.taxArea = new TreeMap<>();
        this.employees = new TreeSet<>(Employee.EmployeeID);
        this.buildings = new ArrayList<>();
        this.buildingAgreements = new ArrayList<>();
    }

    public Company(String name, double taxPet, double taxElevator) {
        this.name = name;
        this.taxPet = taxPet;
        this.taxElevator = taxElevator;
        this.taxArea = new TreeMap<>();
        this.employees = new TreeSet<>(Employee.EmployeeID);
        this.buildings = new ArrayList<>();
        this.buildingAgreements = new ArrayList<>();
    }

    public List<BuildingAgreement> getBuildingAgreements() {
        return buildingAgreements;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getTaxPet() {
        return taxPet;
    }

    public double getTaxElevator() {
        return taxElevator;
    }

    public Map<Integer, Double> getTaxArea() {
        return taxArea;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setTaxArea(Map<Integer, Double> taxArea) {
        this.taxArea = taxArea;
    }

    public void addTaxArea(){
        double initialTax = 0;
        for (int i = 50; i < 300; i += 50) {
            taxArea.put(i, initialTax+3);
        }

    }

    public void addEmployee(Employee employee){
        employee.setCompany(this);
        employees.add(employee);
    }


    public void addBuilding(Building building, BuildingAgreement buildingAgreement){


        if(!employees.isEmpty()){
            buildings.add(building);

            building.setEmployee(employees.stream().min(Employee.EmployeeNumberBuildings).get());
            employees.stream().min(Employee.EmployeeNumberBuildings).get().addBuildingToEmployee(building);
            building.setCompany(this);
            buildingAgreements.add(buildingAgreement);
        }

    }



    public void deleteEmployee(Employee employee){

//

        for(Building building: buildings){
            if(building.getEmployee().equals(employee))
                building.setEmployee(employees.stream().min(Employee.EmployeeNumberBuildings).get());
                employees.stream().min(Employee.EmployeeNumberBuildings).get().addBuildingToEmployee(building);
        }

        employees.remove(employee);

    }

    public double taxApartment(Apartment apartment){
        double tax = 0;
        Set<Map.Entry<Integer,Double>> set = taxArea.entrySet();
        for(Map.Entry<Integer,Double> me:set){
            if(apartment.getArea() < me.getKey()){
                tax = me.getValue();
                break;}

        }

        for (Resident resident: apartment.getResidents()){
            if(resident.getAge() > 7 && resident.useElevator())
                tax += taxElevator;
        }

        if(apartment.hasPet())
            tax += taxPet;

        return tax;
    }

    public void writeToFilePayment(Employee employee, Building building, Apartment apartment, LocalDate date){
        File file = new File("payments.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            if (!file.exists()) {
                file.createNewFile();
            }
            writer.write(employee.getId()+ " " + employee.getFirstName() +" "+ employee.getLastName()+ " ");
            writer.write(building.getId()+ " " + building.getAddress() + " ");
            writer.write(apartment.getNumber()+ " " );
            writer.write(date.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public void showAllBuildingsManagedByEmployees(){
        for(Employee employee: this.employees){
            System.out.println("Company " + this.name+ " and managed buildings by employee " + employee.getFirstName()+" "+ employee.getLastName() + " :");
            for(Building building: employee.getBuildingList()){
                System.out.println(building.getAddress() + building.getApartments());
            }

            //            for(Building building: employee.getBuildingsNotPaid().keySet()){
//                if(!employee.getBuildingsPaid().containsKey(building)){
//                    System.out.println(building.getAddress() + building.getApartments());
//                }
//            }
//            for(Building building:employee.getBuildingsPaid().keySet()){
//                System.out.println(building.getAddress() + building.getApartments());
//            }
        }
    }

    public void showNotPaidTaxes(){
        double sumCompany = 0;
        double sumBuilding ;
        double sumEmployee ;
        System.out.println("Company " + this.name + " not paid taxes");
        for(Employee employee: this.employees){

            sumEmployee = 0;
            for(Building building: employee.getBuildingsNotPaid().keySet()){
                sumBuilding = 0;
                for(Apartment apartment: employee.getBuildingsNotPaid().get(building).keySet()) {

                    sumBuilding += employee.getBuildingsNotPaid().get(building).get(apartment);

                }
                sumEmployee += sumBuilding;
                System.out.println("For Building " + building.getId() + " managed by employee " +employee.getId() + " sum is: " + sumBuilding);
            }


            sumCompany += sumEmployee;
            System.out.println("Employee " + employee.getId() + " sum is: " + sumEmployee);
            System.out.println("===========");

        }
        System.out.println("Company " + this.name + " sum is: " + sumCompany);

    }

    public double paidTaxes(){
        double sumCompany = 0;
        double sumBuilding ;
        double sumEmployee ;
        System.out.println("Company " + this.name + " paid taxes");
        for(Employee employee: this.employees){

            sumEmployee = 0;
            if(!employee.getBuildingsPaid().isEmpty())
                for(Building building: employee.getBuildingsPaid().keySet()){
                    sumBuilding = 0;
                    for(Apartment apartment: employee.getBuildingsPaid().get(building).keySet()) {

                        sumBuilding += employee.getBuildingsPaid().get(building).get(apartment);

                    }
                    sumEmployee += sumBuilding;
                    System.out.println("Building " + building.getAddress() + " sum is: " + sumBuilding);
                }

            sumCompany += sumEmployee;
            System.out.println("Employee " + employee.getId() + " sum is: " + sumEmployee);
            System.out.println("===========");
        }
        System.out.println("Company " + this.name + " sum is: " + sumCompany);
        return sumCompany;

    }



    public void sortEmployeesByNameAndBuildings(){
        employees.stream().sorted(Employee.EmployeeFirstName.thenComparing(Employee.EmployeeNumberBuildings)).forEach(System.out::println);

    }



    public  void sortResidentsByNameAndAge(Building building){
        List<Resident> residentList = new ArrayList<>();
        if(buildings.contains(building)) {
            for (Apartment apartment : building.getApartments().values()) {
                residentList.addAll(apartment.getResidents());
            }

            residentList.stream().sorted(Resident.ResidentFirstName.thenComparing(Resident.ResidentAge)).forEach(System.out::println);
        }
    }



    @Override
    public int compareTo(Company o) {
        return Double.compare(this.paidTaxes(), o.paidTaxes());
    }
}


//    public void addBuilding(Building building){
//        Employee employee = new Employee();
//        int minBuildings = 0;
//        Set<Map.Entry<Employee,Integer>> set = employees.entrySet();
//        for(Map.Entry<Employee,Integer> me: set){
//           if (me.getValue() < minBuildings){
//               minBuildings = me.getValue();
//               employee = me.getKey();}
//
//        }
//
//        buildings.put(building,employee);  // insert building and employee with the least number of buildings
//        employees.replace(employee, employees.get(employee) + 1);
//        employee.setNumberOfManagedBuildings(employee.getNumberOfManagedBuildings() + 1);// increase number of buildings with 1
//    }
//
//    public void setPositionSalary(Position position, double salary) {
//
//        salaryList.put(position, salary);
//    }
//
//    private void setPositionSalaries() {
//        double startSalary = 1000;
//        for (Position position : Position.values()) {
//            setPositionSalary(position, startSalary += 500);
//        }
//    }