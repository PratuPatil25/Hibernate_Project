package electricity_24_7.com.OnlineElectricitySystem.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import electricity_24_7.com.OnlineElectricitySystem.entity.AdminLogin;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;

public class AdminDao {

	//Validates the admin login credentials by checking if a matching record exists in the database.
    public boolean validateAdminLogin(String username, String password) {
    	// Obtain a session from the Hibernate session factory
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	// HQL query to find a matching admin record with the provided username and password
            String hql = "FROM AdminLogin WHERE username = :username AND password = :password";
            Query<AdminLogin> query = session.createQuery(hql, AdminLogin.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            AdminLogin admin = query.uniqueResult();
            return admin != null; // Returns true if admin exists
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        finally {
            session.close();
        }
    }
}
