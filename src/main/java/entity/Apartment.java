package entity;

import dao.OwnerDAO;
import dao.ResidentDAO;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "number", nullable = false)
    private int number;
//    @Positive
    @Column(name = "area", nullable = false)
    private double area;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @OneToMany(mappedBy = "apartment")
    private Set<Resident> residents;
    @Column(name = "pet", nullable = false)
    private boolean hasPet;

    public Apartment() {

        this.residents = new TreeSet<>(Resident.ResidentID);

    }

    public Apartment(int number, double area, Building building, Owner owner, boolean hasPet) {
        this.number = number;
        this.area = area;
        this.building = building;
        this.owner = owner;
        this.residents = new TreeSet<>(Resident.ResidentID);
        this.hasPet = hasPet;
    }


    public void addResident(Resident resident){
        residents.add(resident);

    }
    public void removeResident(Resident resident){
        residents.remove(resident);
    }


    public int getNumber() {
        return number;
    }

    public double getArea() {
        return area;
    }

    public Building getBuilding() {
        return building;
    }

    public Owner getOwner() {
        return owner;
    }

    public Set<Resident> getResidents() {
        return residents;
    }


    public boolean  hasPet() {
        return hasPet;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "entity.Apartment{" +
                "number=" + number +
                ", area=" + area +
//                ", owner=" + owner +
//                ", residents=" + residents +
                ", hasPet=" + hasPet +
                '}';
    }
}
