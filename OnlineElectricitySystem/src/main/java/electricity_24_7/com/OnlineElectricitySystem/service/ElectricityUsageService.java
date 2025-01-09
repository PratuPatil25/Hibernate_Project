
package electricity_24_7.com.OnlineElectricitySystem.service;

import java.time.LocalDate;
import java.util.List;

import electricity_24_7.com.OnlineElectricitySystem.dao.CustomerDao;
import electricity_24_7.com.OnlineElectricitySystem.dao.ElectricityUsageDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.ElectricityUsage;

public class ElectricityUsageService {

	
		    private final ElectricityUsageDao usageDao = new ElectricityUsageDao();
	
		  
		    // Initialize the ElectricityUsageDao to interact with the database
		        private final ElectricityUsageDao electricityUsageDao;
	
		        
		        // Constructor to initialize the ElectricityUsageDao
		        public ElectricityUsageService() {
		            this.electricityUsageDao = new ElectricityUsageDao();
		        }
		        //Retrieves the electricity usage records for a specific customer.
		        public List<ElectricityUsage> getUsage(String customerNumber) {
		        	// Fetch electricity usage for the given customer using the DAO
		            return electricityUsageDao.getElectricityUsageByCustomerNumber(customerNumber);
		        }
		       // Adds electricity usage information for a customer.
		        public String addElectricityUsage(String customerNumber, double meterReading, LocalDate usageDate, double unitsConsumed) {
		            try {
		                // Fetch the customer entity using customerNumber
		                CustomerRegistration customer = CustomerDao.getCustomerByCustomerNumber(customerNumber);
		                if (customer == null) {
		                    return "Error: Customer not found.";
		                }
		                
	
		                // Create a new ElectricityUsage entity
		                ElectricityUsage usage = new ElectricityUsage();
		                usage.setMeterReading(meterReading);
		                usage.setUsageDate(usageDate);
		                usage.setUnitsConsumed(unitsConsumed);
		                usage.setCustomer(customer);
	
		                // Save the entity
		                electricityUsageDao.saveElectricityUsage(usage);
		                return "Electricity usage added successfully!";
		            } catch (Exception e) {
		                e.printStackTrace();
		                return "Error: Unable to add electricity usage.";
		            }
		        }
		    }
	
		
	
