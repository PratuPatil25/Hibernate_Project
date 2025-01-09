package electricity_24_7.com.OnlineElectricitySystem.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import electricity_24_7.com.OnlineElectricitySystem.entity.ElectricityUsage;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;
import jakarta.transaction.Transaction;

public class ElectricityUsageDao {
	
	//Fetches electricity usage records by customer number.
	
	public List<ElectricityUsage> getUsageByCustomer(String customerNumber) {
		 try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ElectricityUsage WHERE customerNumber = :customerNumber", ElectricityUsage.class)
                          .setParameter("customerNumber", customerNumber)
                          .list();
        }
    }
	
	 // Fetches electricity usage by customer number using the associated customer entity.
    public List<ElectricityUsage> getUsageByCustomerNumber(String customerNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM ElectricityUsage WHERE customer.customerNumber = :customerNumber", 
                    ElectricityUsage.class)
                          .setParameter("customerNumber", customerNumber)
                          .list();
        }
    }
    
        //Saves a new electricity usage record to the database.
        public void saveElectricityUsage(ElectricityUsage usage) {
            org.hibernate.Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                // Save the electricity usage record
                session.save(usage);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        
       //Fetches electricity usage records by customer number and orders them by usage date in descending order.
            public List<ElectricityUsage> getElectricityUsageByCustomerNumber(String customerNumber) {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    // Fetch usage records by customerNumber
                    Query<ElectricityUsage> query = session.createQuery(
                        "SELECT e FROM ElectricityUsage e WHERE e.customer.customerNumber = :customerNumber ORDER BY e.usageDate DESC",
                        ElectricityUsage.class
                    );
                    query.setParameter("customerNumber", customerNumber);

                    return query.list();
                } catch (Exception e) {
                   e.printStackTrace();
                    return new ArrayList<>();
                }
            }
        


}
