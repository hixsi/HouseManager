package dao;

import configuration.SessionFactoryUtil;
import entity.Apartment;
import entity.Company;
import entity.Employee;
import entity.Resident;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class ApartmentDAO {
    public static void saveApartment(Apartment apartment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(apartment);
            transaction.commit();
        }
    }

    public static void saveOrUpdateApartment(Apartment apartment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(apartment);
            transaction.commit();
        }
    }

    public static void saveCompanies(List<Apartment> apartmentList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartmentList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Apartment> getApartments() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Apartment", Apartment.class).list();
        }
    }

    public static Apartment getApartment(long id) {
        Transaction transaction = null;
        Apartment apartment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Apartment entity using get() method
            apartment = session.get(Apartment.class, id);
            transaction.commit();
        }
        return apartment;
    }


    public static Apartment getApartmentById(long id) {
        Transaction transaction = null;
        Apartment apartment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Apartment entity using byId() method
            apartment = session.byId(Apartment.class).getReference(id);
            transaction.commit();
        }
        return apartment;
    }

    public static Set<Resident> getApartmentResidents(long id) {
        Apartment apartment;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartment = session.createQuery(
                            "select a from Apartment a" +
                                    " join fetch a.residents" +
                                    " where a.id = :id",
                            Apartment.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();
        }
        return apartment.getResidents();
    }



    public static void deleteApartment(Apartment apartment) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(apartment);
            transaction.commit();
        }
    }


}
