package dao;

import configuration.SessionFactoryUtil;
import entity.BuildingAgreement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BuildingAgreementDAO {
    public static void saveBuildingAgreement(BuildingAgreement buildingAgreement) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(buildingAgreement);
            transaction.commit();
        }
    }

    public static void saveOrUpdateBuildingAgreement(BuildingAgreement buildingAgreement) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(buildingAgreement);
            transaction.commit();
        }
    }

    public static void saveBuildingAgreement(List<BuildingAgreement> buildingAgreementList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            buildingAgreementList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<BuildingAgreement> getBuildingAgreements() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM BuildingAgreement", BuildingAgreement.class).list();
        }
    }

    public static BuildingAgreement getBuildingAgreement(long id) {
        Transaction transaction = null;
        BuildingAgreement buildingAgreement;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Company entity using get() method
            buildingAgreement = session.get(BuildingAgreement.class, id);
            transaction.commit();
        }
        return buildingAgreement;
    }


    public static BuildingAgreement getBuildingAgreementById(long id) {
        Transaction transaction = null;
        BuildingAgreement buildingAgreement;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Company entity using byId() method
            buildingAgreement = session.byId(BuildingAgreement.class).getReference(id);
            transaction.commit();
        }
        return buildingAgreement;
    }

    public static void deleteBuildingAgreement(BuildingAgreement buildingAgreement) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(buildingAgreement);
            transaction.commit();
        }

    }
}
