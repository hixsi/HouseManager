package dao;

import configuration.SessionFactoryUtil;
import entity.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OwnerDAO {

    public static void saveOwner(Owner owner) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(owner);
            transaction.commit();
        }
    }

    public static void saveOrUpdateOwner(Owner owner) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(owner);
            transaction.commit();
        }
    }

    public static void saveCompanies(List<Owner> ownerList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            ownerList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Owner> getOwners() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM owner", Owner.class).list();
        }
    }

    public static Owner getOwner(long id) {
        Transaction transaction = null;
        Owner owner;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get owner entity using get() method
            owner = session.get(Owner.class, id);
            transaction.commit();
        }
        return owner;
    }



    public static Owner getOwnerById(long id) {
        Transaction transaction = null;
        Owner owner;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get owner entity using byId() method
            owner = session.byId(Owner.class).getReference(id);
            transaction.commit();
        }
        return owner;
    }

    public static void deleteOwner(Owner owner) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(owner);
            transaction.commit();
        }
    }
}
