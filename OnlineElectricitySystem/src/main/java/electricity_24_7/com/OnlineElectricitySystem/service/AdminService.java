package electricity_24_7.com.OnlineElectricitySystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import jakarta.validation.constraints.*; // For validation annotations like @Size and @NotBlank
import lombok.Data;
import lombok.Getter; // For Getter annotation
import lombok.Setter; // For Setter annotation*/

import electricity_24_7.com.OnlineElectricitySystem.dao.AdminDao;
import electricity_24_7.com.OnlineElectricitySystem.dao.BillDao;
import electricity_24_7.com.OnlineElectricitySystem.dao.CustomerDao;
import electricity_24_7.com.OnlineElectricitySystem.dao.MeterDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;

public class AdminService {
	
    private AdminDao adminDAO = new AdminDao();
    private CustomerDao customerDao = new CustomerDao();
    private BillDao billDao = new BillDao();
    private MeterDao  meterDao = new MeterDao ();
    
    
    // Hardcoded Admin credentials
    private static final String username = "Admin@123";
    private static final String password = "Admin@123";
   
    
 // Validate Admin Credentials
    public static boolean validateAdmin(String username, String password) {
        return "Admin@123".equals(username) && "Admin@123".equals(password);
    }

    //Adds a new bill for a customer.
    public void addBill() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer Number: ");
        String customerNumber = scanner.nextLine(); // Use nextLine() to handle alphanumeric input

        System.out.print("Enter Meter Number: ");
        String meterNumber = scanner.nextLine(); // Use nextLine() for alphanumeric input

        System.out.print("Enter Billing Amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter Payment Status (Paid/Unpaid): ");
        scanner.nextLine(); // Consume the leftover newline
        String paymentStatus = scanner.nextLine();

        try {
            CustomerRegistration customer = customerDao.getCustomerByNumber(customerNumber); // Use the correct method
            Meter meter = meterDao.getMeterByNumber(meterNumber); // Use the correct method

            if (customer == null || meter == null) {
                System.out.println("Error: Invalid Customer Number or Meter Number.");
                return;
            }

            Bill bill = new Bill();
            bill.setCustomer(customer);
            bill.setMeter(meter);
            bill.setAmount(amount);
            bill.setBillingDate(LocalDate.now());
            bill.setPaymentStatus(paymentStatus);

            billDao.saveBill(bill); // Save the bill to the database
            System.out.println("Bill added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while adding the bill.");
        }
    }

    //Fetches customer details using customer number.
    public CustomerRegistration getCustomerByNumber(String customerNumber) {
        return customerDao.getCustomerByCustomerNumber(customerNumber);
    }
    
    //Fetches meter details using meter number
    public Meter getMeterByNumber(String meterNumber) {
        return meterDao.findByMeterNumber(meterNumber);
    }

    
    // Fetch All Customers
    public List<CustomerRegistration> getAllCustomers() {
        return customerDao.getAllCustomers();
    }
    // Display Customers in Tabular Format
    public void displayCustomers(List<CustomerRegistration> customers) {
        System.out.println("\n================================================================================");
        System.out.println("         Customer Information");
        System.out.println("=================================================================================");
        System.out.printf("%-10s %-25s %-25s %-20s%n", "CustID", "Full Name", "Electricity No", "Customer Number");
        System.out.println("------------------------------------------------------------------------------------");

        for (CustomerRegistration customer : customers) {
            String fullName = customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getSurname();
            System.out.printf("%-10d %-25s %-25s %-20s%n", 
                customer.getCustId(), 
                fullName, 
                customer.getElectricityNo(), 
                customer.getCustomerNumber());
        }

        System.out.println("------------------------------------------------------------------------------------");
    }
    
 // Fetch all bills
    public List<Bill> getAllBills() {
        return billDao.getAllBills();
    }
    
    
 // Display bills in tabular format
    public void displayBills(List<Bill> bills) {
        System.out.println("\n==================================================================================");
        System.out.println("                                Bill Information");
        System.out.println("===================================================================================");
        System.out.printf("%-10s %-18s %-15s %-15s %-15s %-10s%n", 
                "Bill ID", "Customer Name", "Meter ID", "Billing Date", "Amount", "Payment Status");
        System.out.println("------------------------------------------------------------------------------------------");

        for (Bill bill : bills) {
            System.out.printf("%-10d %-18s %-15s %-15s ₹%-10.2f %-15s%n",
                    bill.getBillId(),
                    bill.getCustomer().getFirstName() + " " + bill.getCustomer().getSurname(),
                    bill.getMeter().getMeterNumber(),
                    bill.getBillingDate(),
                    bill.getAmount(),
                    bill.getPaymentStatus());
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }
 // Generate a revenue report
    public void generateReport() {
        List<Bill> bills = getAllBills();
        double totalRevenue = bills.stream()
                                   .mapToDouble(Bill::getAmount)
                                   .sum();

        long unpaidBills = bills.stream()
                                 .filter(bill -> !bill.getPaymentStatus().equalsIgnoreCase("Paid"))
                                 .count();

        System.out.println("\n==========================================");
        System.out.println("              Revenue Report");
        System.out.println("==========================================");
        System.out.println("Total Bills Generated: " + bills.size());
        System.out.println("Total Revenue: ₹" + totalRevenue);
        System.out.println("Unpaid Bills: " + unpaidBills);
        System.out.println("==========================================");
    }
}