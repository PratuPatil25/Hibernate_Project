package electricity_24_7.com.OnlineElectricitySystem.dao;

import java.util.ArrayList;
import java.util.List;

//import com.mysql.cj.Session;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;


public class BillDao {
	
	// Fetches all outstanding (unpaid) bills for a given customer based on their customer number.
	public List<Bill> getOutstandingBills(String customerNumber) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Query<Bill> query = session.createQuery(
	            "SELECT b FROM Bill b WHERE b.customer.customerNumber = :customerNumber AND b.paymentStatus = 'Unpaid'",
	            Bill.class
	        );
	        query.setParameter("customerNumber", customerNumber);
	        return query.list();
	    } catch (Exception e) {
	       e.printStackTrace();
	        return new ArrayList<>();
	    }
	}
	
    // Saves a new bill to the database.
    //If an exception occurs, the transaction is rolled back to maintain data integrity.
	    public void saveBill(Bill bill) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.save(bill);// Save the bill object to the database.
	            transaction.commit();// Commit the transaction.
	            System.out.println("Bill added successfully!");
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();// Rollback the transaction in case of an error.
	            }
	            e.printStackTrace();
	        }
	    }
	


	//Fetches all bills associated with a specific customer based on their customer ID.
	public List<Bill> getBillsByCustomer(int customerId) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("FROM Bill WHERE customer.custId = :customerId", Bill.class)
	                    .setParameter("customerId", customerId)
	                    .list();
	        }
	    }
	     //Fetches all bills associated with a specific meter based on its meter ID.
	    public List<Bill> getBillsByMeter(Long meterId) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("FROM Bill WHERE meter.id = :meterId", Bill.class)
	                    .setParameter("meterId", meterId)
	                    .list();
	        }
	    }

	    //  Fetches all bills available in the database.
	    public List<Bill> getAllBills() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("FROM Bill", Bill.class).list();
	        }
	    }
	    
	    

	}

