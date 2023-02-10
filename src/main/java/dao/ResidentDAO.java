package dao;

import configuration.SessionFactoryUtil;
import entity.Resident;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ResidentDAO {

    public static void saveResident(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(resident);
            transaction.commit();
        }
    }

    public static void saveOrUpdateResident(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(resident);
            transaction.commit();
        }
    }

    public static void saveResidents(List<Resident> residentList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            residentList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Resident> getResidents() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Resident", Resident.class).list();
        }
    }

    public static Resident getResident(long id) {
        Transaction transaction = null;
        Resident resident;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Resident entity using get() method
            resident = session.get(Resident.class, id);
            transaction.commit();
        }
        return resident;
    }



    public static Resident getResidentById(long id) {
        Transaction transaction = null;
        Resident resident;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Resident entity using byId() method
            resident = session.byId(Resident.class).getReference(id);
            transaction.commit();
        }
        return resident;
    }

    public static void deleteResident(Resident resident) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(resident);
            transaction.commit();
        }
    }
}
