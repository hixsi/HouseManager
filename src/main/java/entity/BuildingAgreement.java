package entity;

import dao.BuildingDAO;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "building_agreement")
public class BuildingAgreement {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
    @OneToOne
    @JoinColumn(name = "building_id", unique = true, nullable = false)
    Building building;

//    @PastOrPresent(message = "Contact date cannot be in the future!")
    @Column(name = "date")
    LocalDate date;


    public BuildingAgreement() {

    }

    public BuildingAgreement(Company company, Building building,LocalDate date) {
        this.company = company;
        this.building = building;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public Building getBuilding() {
        return building;
    }

    public LocalDate getDate() {
        return date;
    }
}
