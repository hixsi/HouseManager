package dao;

import configuration.SessionFactoryUtil;
import entity.Apartment;
import entity.Building;
import entity.Company;
import entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;

public class BuildingDAO {

    public static void saveBuilding(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(building);
            transaction.commit();
        }
    }

    public static void saveOrUpdateBuilding(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(building);
            transaction.commit();
        }
    }

    public static void saveBuilding(List<Building> buildingList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            buildingList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Building> getBuildings() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Building", Building.class).list();
        }
    }

    public static Building getBuilding(long id) {
        Transaction transaction = null;
        Building building;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Company entity using get() method
            building = session.get(Building.class, id);
            transaction.commit();
        }
        return building;
    }


    public static Building getBuildingById(long id) {
        Transaction transaction = null;
        Building building;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Company entity using byId() method
            building = session.byId(Building.class).getReference(id);
            transaction.commit();
        }
        return building;
    }

    public static Map<Integer, Apartment> getBuildingApartments(long id) {
        Building building;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            building = session.createQuery(
                            "select b from Building b" +
                                    " join fetch b.apartments" +
                                    " where b.id = :id",
                            Building.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();
        }
        return building.getApartments();
    }

    public static void deleteBuilding(Building building) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(building);
            transaction.commit();
        }

    }
}
