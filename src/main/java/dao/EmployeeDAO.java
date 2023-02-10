package dao;

import configuration.SessionFactoryUtil;
import entity.Building;
import entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class EmployeeDAO {
    public static void saveEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
    }

    public static void saveOrUpdateEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    public static void saveCompanies(List<Employee> employeeList) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employeeList.stream().forEach((com) -> session.save(com));
            transaction.commit();
        }
    }

    public static List<Employee> getEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    public static Employee getEmployee(long id) {
        Transaction transaction = null;
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Employee entity using get() method
            employee = session.get(Employee.class, id);
            transaction.commit();
        }
        return employee;
    }



    public static Employee getEmployeeById(long id) {
        Transaction transaction = null;
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // get Employee entity using byId() method
            employee = session.byId(Employee.class).getReference(id);
            transaction.commit();
        }
        return employee;
    }


    public static List<Building> getEmployeeBuildings(long id) {
        Employee employee;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.createQuery(
                            "select e from Employee e" +
                                    " join fetch e.buildingList " +
                                    "where e.id = :id",
                            Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();
        }
        return employee.getBuildingList();
    }

    public static void deleteEmployee(Employee employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }
}
