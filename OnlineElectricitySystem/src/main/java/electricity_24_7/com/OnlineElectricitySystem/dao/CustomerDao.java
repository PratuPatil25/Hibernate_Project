package electricity_24_7.com.OnlineElectricitySystem.dao;

import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CustomerDao {

	//Saves a new customer registration to the database.
    public boolean saveCustomerRegistration(CustomerRegistration registration) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(registration);// Save the customer registration object
            transaction.commit(); // Commit the transaction.
            return true;
        } 
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();// Rollback the transaction in case of an error
            }
            e.printStackTrace();// Log the exception for debugging.
            return false;
        }
   }
    
    //Retrieves a customer by their unique customer number.
    public CustomerRegistration getCustomerByNumber(String customerNumber) {
        Transaction transaction = null;
        CustomerRegistration customer = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            customer = session.createQuery("FROM CustomerRegistration WHERE customerNumber = :customerNumber", CustomerRegistration.class)
                              .setParameter("customerNumber", customerNumber)
                              .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return customer;
    }
    
    // Fetch all customers from the database
    public List<CustomerRegistration> getAllCustomers() {
        List<CustomerRegistration> customers = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM CustomerRegistration"; // Ensure all fields are fetched
            Query<CustomerRegistration> query = session.createQuery(hql, CustomerRegistration.class);
            customers = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    // Updates an existing customer in the database.
    public static boolean updateCustomer(CustomerRegistration customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Update the customer details in the database
            session.update(customer);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
//Retrieves a customer by their customer number (alternative method).
    public static CustomerRegistration getCustomerByCustomerNumber(String customerNumber) {
        Transaction transaction = null;
        CustomerRegistration customer = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Fetch the customer using customerNumber
            Query<CustomerRegistration> query = session.createQuery("FROM CustomerRegistration WHERE customerNumber = :customerNumber", CustomerRegistration.class);
            query.setParameter("customerNumber", customerNumber);
            customer = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
           e.printStackTrace();
        }
        return customer;
    }
   
    // Finds a customer by their customer number.
    public CustomerRegistration findByCustomerNumber(String customerNumber) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM CustomerRegistration WHERE customer_number = :customerNumber", CustomerRegistration.class)
                    .setParameter("customerNumber", customerNumber)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
   
}



