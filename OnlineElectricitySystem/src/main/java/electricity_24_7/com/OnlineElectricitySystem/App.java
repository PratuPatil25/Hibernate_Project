package electricity_24_7.com.OnlineElectricitySystem;

import electricity_24_7.com.OnlineElectricitySystem.dao.CustomerDao;
import electricity_24_7.com.OnlineElectricitySystem.dao.MeterDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.ElectricityUsage;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;
import electricity_24_7.com.OnlineElectricitySystem.entity.PaymentHistory;
import electricity_24_7.com.OnlineElectricitySystem.service.AdminService;
import electricity_24_7.com.OnlineElectricitySystem.service.BillService;
import electricity_24_7.com.OnlineElectricitySystem.service.CustomerService;
import electricity_24_7.com.OnlineElectricitySystem.service.ElectricityUsageService;
import electricity_24_7.com.OnlineElectricitySystem.service.MeterService;
import electricity_24_7.com.OnlineElectricitySystem.service.PaymentHistoryService;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class App {

    // Declare and initialize service objects
	private static final AdminService adminService = new AdminService();
    private static final CustomerService customerService = new CustomerService();
    private static final BillService billService = new BillService();
    private static final PaymentHistoryService paymentHistoryService = new PaymentHistoryService();
    private static final ElectricityUsageService electricityUsageService = new ElectricityUsageService();
    private static CustomerRegistration loggedInCustomer;


	public static void main(String[] args) {
		// Suppress Hibernate logs to reduce console clutter
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE); // Show only SEVERE logs
        Logger.getLogger("org.hibernate.validator").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.engine.jdbc.spi.SqlExceptionHelper").setLevel(Level.SEVERE);

        // Your application logic
        System.out.println("Application started!!!");
		
        Scanner scanner = new Scanner(System.in);
      
        while (true) {
        	 // Display main menu
        	System.out.println("==========================================");
            System.out.println("     Welcome to Online Electricity System");
            System.out.println("==========================================");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. New Customer Registration");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                	 handleAdminLogin(scanner,adminService);
                    break;
                case 2:
                	handleCustomerLogin(scanner);
                    break;

                case 3:
                    handleCustomerRegistration(scanner, customerService);
                    break;

                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
	//Handles the admin login functionality.
    private static void handleAdminLogin(Scanner scanner, AdminService adminService) {
    	System.out.println("==========================================");
        System.out.println("              Admin Login");
        System.out.println("==========================================");
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        // Validate admin credentials
        if (adminService.validateAdmin(username, password)) {
            System.out.println("Login Successful");
            System.out.println("Welcome, Admin!!!");
            displayAdminDashboard(scanner); // Call the admin dashboard
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    //Displays the admin dashboard and handles its options.
    private static void displayAdminDashboard(Scanner scanner) {
        while (true) {
        	
        	// Admin dashboard menu
            System.out.println("\n==========================================");
            System.out.println("             Admin Dashboard");
            System.out.println("==========================================");
            System.out.println("1. View All Customers");
            System.out.println("2. Add Bills");
            System.out.println("3. View All Bills");
            System.out.println("4. Generate Report");
            System.out.println("5. Add Electricity Usage");
            System.out.println("6. Logout");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                	 List<CustomerRegistration> customers = adminService.getAllCustomers();
                     adminService.displayCustomers(customers);
                    break;
                    
                case 2:
                	adminService.addBill();  // Add bill functionality
                    break;
                    
                case 3:
                	List<Bill> bills = adminService.getAllBills();
                    adminService.displayBills(bills);
                    break;
                case 4:
                    adminService.generateReport();
                    break;
                case 5:
                	System.out.print("Enter Customer Number: ");
                    String customerNumber = scanner.nextLine();

                    System.out.print("Enter Meter Reading: ");
                    double meterReading = scanner.nextDouble();

                    System.out.print("Enter Usage Date (yyyy-MM-dd): ");
                    scanner.nextLine(); // Consume newline
                    LocalDate usageDate = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter Units Consumed: ");
                    double unitsConsumed = scanner.nextDouble();

                    // Add electricity usage
                    String result = electricityUsageService.addElectricityUsage(customerNumber, meterReading, usageDate, unitsConsumed);
                    System.out.println(result);
                	break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }   
      //handle Customer Login
    private static void handleCustomerLogin(Scanner scanner) {
        System.out.println("\n==========================================");
        System.out.println("             Customer Login");
        System.out.println("==========================================");

        System.out.print("Enter Customer Number: ");
        String customerNumber = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Validate customer and log them in
        CustomerRegistration customer = customerService.validateCustomer(customerNumber, password);
        if (customer != null) {
            loggedInCustomer = customer; // Store the logged-in customer
            System.out.println("Login Successful! Welcome, " + customer.getFirstName() + ".");
            displayCustomerDashboard(scanner, customerNumber);
        } else {
            System.out.println("Invalid Customer Number or Password. Please try again.");
        }
    }
       
    private static void handleCustomerRegistration(Scanner scanner, CustomerService customerService) {
        System.out.println("\n==========================================");
        System.out.println("        New Customer Registration");
        System.out.println("==========================================");
        
        CustomerRegistration registration = new CustomerRegistration();

        System.out.print("Enter First Name: ");
        registration.setFirstName(scanner.nextLine());
        
        System.out.print("Enter Parent's Name: ");
        registration.setMiddleName(scanner.nextLine());
        
        System.out.print("Enter Surname: ");
        registration.setSurname(scanner.nextLine());
        
        System.out.println("Enter Address Details:");       
        System.out.print("  At Post: ");
        registration.setAtPost(scanner.nextLine());
        
        System.out.print("  Plot No: ");
        registration.setPlotNo(scanner.nextInt());
        
        scanner.nextLine(); //newline
        System.out.print("  Taluka: ");
        registration.setTaluka(scanner.nextLine());
        
        System.out.print("  District: ");
        registration.setDistrict(scanner.nextLine());
        
        System.out.print("  Pincode: ");
        registration.setPincode(scanner.nextInt());
        
        scanner.nextLine(); //newline
        System.out.print("  State: ");
        registration.setState(scanner.nextLine());
        
        System.out.print("  Area: ");
        registration.setArea(scanner.nextLine());

        System.out.print("  Enter Mobile Number: ");
        String mobileNumber = scanner.nextLine(); // Read as a String
        registration.setCustomerNumber(mobileNumber); // Assign mobile number to customerNumber

       // scanner.nextLine(); // Consume leftover newline
        System.out.print("  Enter Electricity Number: ");
        registration.setElectricityNo(scanner.nextLine()); 

        // Set Password
       System.out.print("Create Password: ");
       String password = scanner.nextLine();
       registration.setPassword(password);

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Error: Passwords do not match. Please try again.");
            return;
        }

        System.out.println("\nProcessing Registration...");

        boolean isSuccess = customerService.registerCustomer(registration);
        if (isSuccess) {
            System.out.println("Registration Successful! Your Customer Number is " + registration.getCustomerNumber());
            System.out.println("Please login to manage your account.");
        } else {
            System.out.println("Registration Failed. Please try again.");
        }
        
    }              
    private static void displayCustomerDashboard(Scanner scanner,String customerNumber) {
        if (loggedInCustomer == null) {
            System.out.println("No customer is currently logged in.");
            return;
        }
        
            while (true) {
                System.out.println("\n==========================================");
                System.out.println("             Customer Dashboard");
                System.out.println("==========================================");
                System.out.println("1. Add Meter Information");
                System.out.println("2. View Electricity Usage");
                System.out.println("3. Pay Bill");
                System.out.println("4. View Payment History");
                System.out.println("5. Update Account Details");
                System.out.println("6. Logout");
                System.out.print("Enter your choice (1-5): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                case 1:  
                	MeterService.addMeterInformation(scanner, customerNumber);  // Method to add meter
                       break;
                case 2:
                	 viewElectricityUsage();
                	 break;
                case 3:
                      handlePayBill(scanner, customerNumber);
                    break;
                case 4:
                     handleViewPaymentHistory();
                    break;
                case 5:
                    updateCustomerDetails(scanner, customerNumber);
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        
 
    private static void viewElectricityUsage() {
        if (loggedInCustomer == null) {
            System.out.println("No customer is currently logged in.");
            return;
        }

        List<ElectricityUsage> usageList = electricityUsageService.getUsage(loggedInCustomer.getCustomerNumber());

        if (usageList.isEmpty()) {
                System.out.println("No electricity usage records found for the given customer.");
            } else {
                System.out.println("==========================================");
                System.out.println("         Electricity Usage Summary        ");
                System.out.println("==========================================");
                System.out.printf("%-12s %-15s %-15s\n", "Date", "Meter Reading", "Units Used");
                System.out.println("------------------------------------------");
                
                for (ElectricityUsage usage : usageList) {
                    System.out.printf( "%-12s %-15.2f %-15.2f\n", usage.getUsageDate(), usage.getMeterReading(), usage.getUnitsConsumed());
                }
                System.out.println("==========================================");
            }
            System.out.println("\nPress Enter to return to the dashboard.");
            new Scanner(System.in).nextLine(); // Pause before showing the dashboard again.
        }
       

    private static void handlePayBill(Scanner scanner, String customerNumber) {
        if (customerNumber == null || customerNumber.isEmpty()) {
            System.out.println("Error: Customer number is not available. Cannot process payment.");
            return;
        }

        List<Bill> unpaidBills = billService.getUnpaidBills(customerNumber);

        if (unpaidBills.isEmpty()) {
            System.out.println("No unpaid bills available.");
        } else {
            System.out.println("\n==========================================");
            System.out.println("                 Pay Bill");
            System.out.println("==========================================");
            System.out.printf("%-10s %-15s %-12s\n", "Bill ID", "Amount", "Billing Date");
            System.out.println("------------------------------------------");

            for (Bill bill : unpaidBills) {
                System.out.printf(
                    "%-10d %-15.2f %-12s\n",
                    bill.getBillId(),
                    bill.getAmount(),
                    bill.getBillingDate()
                );
            }

            System.out.println("==========================================");
            System.out.print("\nEnter the Bill ID to pay: ");
            int billId = scanner.nextInt();

            // Confirm payment
            System.out.println("Are you sure you want to pay this bill? (yes/no): ");
            String confirmation = scanner.next();
            if (confirmation.equalsIgnoreCase("yes")) {
                billService.payBill(billId);
                System.out.println("Payment successful!");
            } else {
                System.out.println("Payment cancelled.");
            }
        }
        System.out.println("\nPress Enter to return to the dashboard.");
        scanner.nextLine(); // Pause before returning
        scanner.nextLine(); // Consume extra newline
    }

    private static void handleViewPaymentHistory() {
        if (loggedInCustomer == null) {
            System.out.println("Error: No customer is logged in. Cannot view payment history.");
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("          Payment History");
        System.out.println("==========================================");

        // Fetch payment history using the logged-in customer's customerNumber
        List<PaymentHistory> paymentHistoryList = paymentHistoryService.getHistory(loggedInCustomer.getCustomerNumber());

        // Check if there is any payment history
        if (paymentHistoryList == null || paymentHistoryList.isEmpty()) {
            System.out.println("No payment history found for your account.");
        } else {
            // Display payment history in a formatted table
            System.out.printf("%-20s %-15s\n", "Payment Date", "Amount Paid");
            System.out.println("------------------------------------------------------------");
            for (PaymentHistory payment : paymentHistoryList) {
                System.out.printf(
                    "%-20s %-15.2f\n",
                    payment.getPaymentDate(),
                    payment.getAmountPaid()
                );
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("\nPress Enter to return to the dashboard.");
        new Scanner(System.in).nextLine(); // Pause to allow the user to view the history
    }

    
    public static void updateCustomerDetails(Scanner scanner, String customerNumber) {
        System.out.println("\n==========================================");
        System.out.println("          Update Account Details");
        System.out.println("==========================================");

        try {
            // Fetch the existing customer details using customerNumber
            CustomerRegistration customer = CustomerDao.getCustomerByCustomerNumber(customerNumber);

            if (customer == null) {
                System.out.println("Error: Customer not found.");
                return;
            }

            // Display current details
            System.out.println("Current Details:");
            System.out.println("Customer Number: " + customer.getCustomerNumber());
            System.out.println("Name: " + customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getSurname());
            System.out.println("Address: " + customer.getAddress() + ", " + customer.getArea() + ", " + customer.getAtPost());
            System.out.println("Taluka: " + customer.getTaluka() + ", District: " + customer.getDistrict());
            System.out.println("State: " + customer.getState());
            System.out.println("==========================================\n");

            // Get updated details from the customer
            System.out.print("Enter New First Name (or press Enter to keep current): ");
            String newFirstName = scanner.nextLine();
            if (!newFirstName.isEmpty()) {
                customer.setFirstName(newFirstName);
            }

            System.out.print("Enter New Middle Name (or press Enter to keep current): ");
            String newMiddleName = scanner.nextLine();
            if (!newMiddleName.isEmpty()) {
                customer.setMiddleName(newMiddleName);
            }

            System.out.print("Enter New Surname (or press Enter to keep current): ");
            String newSurname = scanner.nextLine();
            if (!newSurname.isEmpty()) {
                customer.setSurname(newSurname);
            }

            System.out.print("Enter New Address (or press Enter to keep current): ");
            String newAddress = scanner.nextLine();
            if (!newAddress.isEmpty()) {
                customer.setAddress(newAddress);
            }

            System.out.print("Enter New Area (or press Enter to keep current): ");
            String newArea = scanner.nextLine();
            if (!newArea.isEmpty()) {
                customer.setArea(newArea);
            }

            System.out.print("Enter New At Post (or press Enter to keep current): ");
            String newAtPost = scanner.nextLine();
            if (!newAtPost.isEmpty()) {
                customer.setAtPost(newAtPost);
            }

            System.out.print("Enter New Taluka (or press Enter to keep current): ");
            String newTaluka = scanner.nextLine();
            if (!newTaluka.isEmpty()) {
                customer.setTaluka(newTaluka);
            }

            System.out.print("Enter New District (or press Enter to keep current): ");
            String newDistrict = scanner.nextLine();
            if (!newDistrict.isEmpty()) {
                customer.setDistrict(newDistrict);
            }

            System.out.print("Enter New State (or press Enter to keep current): ");
            String newState = scanner.nextLine();
            if (!newState.isEmpty()) {
                customer.setState(newState);
            }            

            // Save the updated details to the database
            boolean isUpdated = CustomerDao.updateCustomer(customer);
            if (isUpdated) {
                System.out.println("\nAccount details updated successfully!");
            } else {
                System.out.println("\nError: Unable to update account details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to update account details.");
        }
    }

}
  

