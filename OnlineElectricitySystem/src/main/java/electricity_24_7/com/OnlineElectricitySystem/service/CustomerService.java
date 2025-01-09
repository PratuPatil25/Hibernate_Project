package electricity_24_7.com.OnlineElectricitySystem.service;

import electricity_24_7.com.OnlineElectricitySystem.dao.CustomerDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import jakarta.transaction.Transactional;

public class CustomerService {

    private CustomerDao customerDAO = new CustomerDao();
    // Registers a new customer.
    public boolean registerCustomer(CustomerRegistration registration) {
        // Generate Customer Number: Prefix "CUST" + Auto-increment ID
        registration.setCustomerNumber("CUST" + System.currentTimeMillis() % 100000);
        return customerDAO.saveCustomerRegistration(registration);
    }
    
    // Validates customer credentials (customer number and password).
    public CustomerRegistration validateCustomer(String customerNumber, String password) {
        CustomerRegistration customer = customerDAO.getCustomerByNumber(customerNumber);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer; // Return the customer if valid
        } else {
            return null; // Return null if invalid
        }
    }    
    
    // Fetch customer details by customer number
    public CustomerRegistration getCustomerDetails(String customerNumber) {
        return customerDAO.getCustomerByCustomerNumber(customerNumber);
    }   


   
}





   

