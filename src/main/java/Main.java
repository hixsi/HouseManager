import dao.*;
import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        // Creating object company and defining taxes
        Company company1 = new Company("House Management 1", 2,2);

        company1.addTaxArea();
        // Creating Employees
        Employee employee1 = new Employee("Peter","Ivanov",25);
        Employee employee2 = new Employee("Georgi","Petrov",24);
        employee1.setId(1);
        employee2.setId(2);
        company1.addEmployee(employee1);
        company1.addEmployee(employee2);
        // Creating buildings
        Building building1 = new Building("Drujba 2",2,2);
        Building building2 = new Building("Drujba 1",3,5);

        // Creating owners
        Owner owner1 = new Owner("Ivan","Kostov");
        Owner owner2 = new Owner ("Penka", "Vasileva");
        //Creating apartments and adding them to buildings
        Apartment apartment1 = new Apartment(1,80,building1,owner1,true);
        Apartment apartment2 = new Apartment(2,180,building1,owner1,true);
        Apartment apartment3 = new Apartment(1,60,building2,owner2,false);
        Apartment apartment4 = new Apartment(2,100,building2,owner2,true);
        building1.addApartment(apartment1,1);
        building1.addApartment(apartment2,2);

        building2.addApartment(apartment3,1);
        building2.addApartment(apartment4,2);

        // Creating Residents and adding residents to apartments
        Resident resident1 = new Resident("Petia","Ivanova",28,apartment1,false);
        Resident resident2 = new Resident("Karla","Ivanova",18,apartment2,false);
        Resident resident3 = new Resident("Dimityr","Lozanov",38,apartment3,false);
        Resident resident4 = new Resident("Ana","Hristova",20,apartment4,false);
        resident1.setId(1);
        resident2.setId(2);
        resident3.setId(3);
        resident4.setId(4);
        apartment1.addResident(resident1);
        apartment2.addResident(resident2);
        apartment3.addResident(resident3);
        apartment4.addResident(resident4);


        // Creating building contract
        BuildingAgreement buildingAgreement1 = new BuildingAgreement(company1,building1, LocalDate.of(2021,12,1));
        building1.setBuildingAgreement(buildingAgreement1);
        BuildingAgreement buildingAgreement2 = new BuildingAgreement(company1,building2, LocalDate.of(2020,10,3));
        building2.setBuildingAgreement(buildingAgreement2);

        company1.addBuilding(building1,buildingAgreement1);
        company1.addBuilding(building2,buildingAgreement2);

        System.out.println();

        System.out.println("Employees:--------------------------------------------------------- ");
        System.out.println(company1.getEmployees().size());
        System.out.println();
        System.out.println("Employees sorted:--------------------------------------------------------- ");
        company1.sortEmployeesByNameAndBuildings();
        System.out.println();
        System.out.println("____________________________________ ");
        System.out.println("Tax apartment1: ");
        System.out.println(company1.taxApartment(apartment1));
        System.out.println();
        System.out.println("List of all employee's buildings and NOT paid taxes: ---------------------------------------------------------");
        System.out.println(employee1.getBuildingsNotPaid());
        System.out.println();
        System.out.println("After paying for apartment 1 --------------------------------------------------------- ");
        employee1.savePaidTax(apartment1, 5, LocalDate.now());
        System.out.println();
        System.out.println("List of all employee's buildings and NOT paid taxes:--------------------------------------------------------- ");
        System.out.println(employee1.getBuildingsNotPaid());
        System.out.println();
        System.out.println("List of all employee's buildings and PAID taxes:--------------------------------------------------------- ");
        System.out.println(employee1.getBuildingsPaid());
        System.out.println();
        System.out.println("New tax month:--------------------------------------------------------- ");
        employee1.newTaxMonth(building1);
        System.out.println(employee1.getBuildingsNotPaid());
        System.out.println();
        System.out.println("Sort residents of building 1:---------------------------------------------------------");
//        System.out.println(building1.getApartments());
        company1.sortResidentsByNameAndAge(building1);
//
//
//
        System.out.println("Show all buildings managed by each employee:---------------------------------------------------------");
        company1.showAllBuildingsManagedByEmployees();

        System.out.println("Show all apartments in a building:---------------------------------------------------------");

        System.out.println("Building " + building1.getId() + " apartments:---------------------------------------------------------");
        building1.showApartments();

         System.out.println("Show all residents in a building:--------------------------------------------------------- ");
         building1.showResidents();


        System.out.println("Show sum of all money owned:---------------------------------------------------------");

        company1.showNotPaidTaxes();

        System.out.println("Show all paid---------------------------------------------------------");
        company1.paidTaxes();




// Save to database
//        OwnerDAO.saveOwner(owner1);
//        CompanyDAO.saveCompany(company1);
//        EmployeeDAO.saveEmployee(employee1);
//        BuildingDAO.saveBuilding(building1);
//        BuildingAgreementDAO.saveBuildingAgreement(buildingAgreement1);
//       ResidentDAO.saveResident(resident1);
//        ResidentDAO.saveResident(resident2);
//
//
//        OwnerDAO.saveOwner(owner2);
//        EmployeeDAO.saveEmployee(employee2);
//        BuildingDAO.saveBuilding(building2);
//        BuildingAgreementDAO.saveBuildingAgreement(buildingAgreement2);
//        ResidentDAO.saveResident(resident3);
//        ResidentDAO.saveResident(resident4);

        CompanyDAO.getCompanyEmployees(1).stream().forEach(System.out::println);

        EmployeeDAO.getEmployeeBuildings(1).stream().forEach(System.out::println);

        Set<Map.Entry<Integer,Apartment>> set = BuildingDAO.getBuildingApartments(1).entrySet();
        for(Map.Entry<Integer,Apartment> me: set){
            System.out.println(me.getKey()+ ",   " + me.getValue());

        }
        ApartmentDAO.getApartmentResidents(1).stream().forEach(System.out::println);

        // Updating
//Company company = CompanyDAO.getCompany(1);
//company.setName("Homes 1");
//CompanyDAO.saveOrUpdateCompany(company);

//Owner owner = OwnerDAO.getOwner(1);
//owner.setFirstName("Radostin");
//OwnerDAO.saveOrUpdateOwner(owner);
//
//Employee employee = EmployeeDAO.getEmployee(1);
//employee.setFirstName("Kosta");
//EmployeeDAO.saveOrUpdateEmployee(employee);
//
//Resident resident = ResidentDAO.getResident(1);
//resident.setFirstName("Luba");
//ResidentDAO.saveOrUpdateResident(resident);
//
//Building building = BuildingDAO.getBuilding(1);
//building.setAddress("Mladost 3");
//BuildingDAO.saveOrUpdateBuilding(building);
//
//Apartment apartment = ApartmentDAO.getApartment(1);
//apartment.setArea(67);
//ApartmentDAO.saveOrUpdateApartment(apartment);


// Deleting from database


//        Owner owner = OwnerDAO.getOwner(3);
//        OwnerDAO.deleteOwner(owner);







    }
}
