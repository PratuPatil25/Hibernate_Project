package electricity_24_7.com.OnlineElectricitySystem.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.entity.PaymentHistory;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;
import jakarta.transaction.Transaction;

public class PaymentHistoryDao {
	
	// Saves a new payment history record to the database.
	public void savePaymentHistory(Bill bill, double amountPaid, String paymentMethod) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        org.hibernate.Transaction transaction = session.beginTransaction();

	        // Create a new PaymentHistory object
	        PaymentHistory paymentHistory = new PaymentHistory();
	        paymentHistory.setPaymentDate(LocalDate.now());
	        paymentHistory.setAmountPaid(amountPaid);
	        paymentHistory.setCustomer(bill.getCustomer());
	        // Add additional fields as necessary

	        // Save the PaymentHistory object
	        session.save(paymentHistory);

	        transaction.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// Retrieves payment history records for a specific customer.
	public static List<PaymentHistory> getPaymentHistoryByCustomer(String customerNumber) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Query<PaymentHistory> query = session.createQuery(
	            "SELECT p FROM PaymentHistory p WHERE p.customer.customerNumber = :customerNumber",
	            PaymentHistory.class
	        );
	        query.setParameter("customerNumber", customerNumber);

            // Execute the query and return the list of results
	        return query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();// Return an empty list in case of an error
	    }
	}


    }


