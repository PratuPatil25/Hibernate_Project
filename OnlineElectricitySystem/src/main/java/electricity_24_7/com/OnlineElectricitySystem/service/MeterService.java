package electricity_24_7.com.OnlineElectricitySystem.service;

import java.util.Scanner;

import electricity_24_7.com.OnlineElectricitySystem.dao.MeterDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;

public class MeterService {

    private MeterDao meterDao = new MeterDao();

    //Assigns a meter to a customer by saving the meter information in the database
    public boolean assignMeterToCustomer(Meter meter) {
    	 // Save the meter using the MeterDao and return the result
        return meterDao.saveMeter(meter);
    }
    // Prompts the user to input meter details and adds the meter to the system.
    public static void addMeterInformation(Scanner scanner, String customerNumber) {
        System.out.println("\n==========================================");
        System.out.println("        Add Meter Information");
        System.out.println("==========================================");

        System.out.print("Enter Meter Number: ");
        String meterNumber = scanner.nextLine(); // Meter number input

        System.out.print("Enter Installation Date (yyyy-MM-dd): ");
        String installationDate = scanner.nextLine(); // Installation date input

        System.out.print("Enter Meter Status (ACTIVE/INACTIVE): ");
        String status = scanner.nextLine(); // Meter status input

        // Call the MeterDao to add the meter and pass the customerNumber directly
        String result = MeterDao.addMeterInfo(customerNumber, meterNumber, installationDate, status);

        // Display the result of adding the meter (full meter details)
        System.out.println("\nMeter Information Added Successfully:");
        System.out.println(result);
    }


    
}

