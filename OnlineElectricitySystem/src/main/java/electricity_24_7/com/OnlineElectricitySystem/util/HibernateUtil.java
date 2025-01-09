package electricity_24_7.com.OnlineElectricitySystem.util;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import electricity_24_7.com.OnlineElectricitySystem.entity.AdminLogin;
import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.ElectricityUsage;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;
import electricity_24_7.com.OnlineElectricitySystem.entity.PaymentHistory;

public class HibernateUtil {
	private static SessionFactory sf;
	public static SessionFactory getSessionFactory() {
		if (sf == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				//useSSL=false---we can disable warning while connecting to DB to Java
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/online_electricity_system?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "Pratiksha@25");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				settings.put(Environment.SHOW_SQL, "false");
				//settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(AdminLogin.class);
				configuration.addAnnotatedClass(ElectricityUsage.class);
				configuration.addAnnotatedClass(CustomerRegistration.class);
				configuration.addAnnotatedClass(PaymentHistory.class);
				configuration.addAnnotatedClass(Bill.class);
				configuration.addAnnotatedClass(Meter.class);

//ServiceRegistry---used to interact with hibernate and making the services
//available through a lightweight container called ServiceRegister
				////build()-method to get an instance StandardServiceRegistry
				
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				
				sf = configuration.buildSessionFactory(serviceRegistry);
			} 
			catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return sf;
	}
}
