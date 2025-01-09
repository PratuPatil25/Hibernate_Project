package electricity_24_7.com.OnlineElectricitySystem.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.cj.Query;

import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;

public class MeterDao {


	 public Meter getMeterByNumber(String meterNumber) {
	        Transaction transaction = null;
	        Meter meter = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            meter = session.createQuery("FROM Meter WHERE meterNumber = :meterNumber", Meter.class)
	                            .setParameter("meterNumber", meterNumber)
	                            .uniqueResult();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	           // e.printStackTrace();
	        }
	        return meter;
	    }
	 
	 
	 

    public static boolean saveMeter(Meter meter) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(meter);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            //e.printStackTrace();
            return false;
        }
    }
    
    public Meter findByMeterNumber(String meterNumber) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Meter WHERE meter_number = :meterNumber", Meter.class)
                    .setParameter("meterNumber", meterNumber)
                    .uniqueResult();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
      
    public static String addMeterInfo(String customerNumber, String meterNumber, String installationDate, String status) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String result = ""; // To hold the result message

        try {
            transaction = session.beginTransaction();

            // Fetch the customer by customer number (customerNumber is a String now)
            CustomerRegistration customer = session.createQuery(
            	    "FROM CustomerRegistration WHERE customerNumber = :customerNumber", 
            	    CustomerRegistration.class
            	).setParameter("customerNumber", customerNumber).uniqueResult();


            if (customer != null) {
                // Create a new Meter object and set its properties
                Meter meter = new Meter();
                meter.setMeterNumber(meterNumber);
                meter.setInstallationDate(installationDate);
                meter.setStatus(status);
                meter.setCustomer(customer); // Associate the customer with this meter

                // Save the meter information to the database
                session.save(meter);
                transaction.commit();
                

                // After successful saving, prepare the result string to show the full meter details
                result = String.format("Meter Number: %s\nInstallation Date: %s\nStatus: %s\nCustomer Number: %s",
                        meter.getMeterNumber(), meter.getInstallationDate(), meter.getStatus(), customer.getCustomerNumber());

            } else {
                result = "Customer with the provided customer number does not exist.";
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
           // e.printStackTrace();
            result = "An error occurred while adding the meter information.";
        } finally {
            session.close();
        }

        return result ;
    }
}




