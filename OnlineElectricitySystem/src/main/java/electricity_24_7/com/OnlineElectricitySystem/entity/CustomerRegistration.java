package electricity_24_7.com.OnlineElectricitySystem.entity;

import java.util.List;

import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column
import jakarta.validation.constraints.*; // For validation annotations
import lombok.Getter; // For Getter annotation
import lombok.Setter; // For Setter annotation

@Getter
@Setter
@Entity // Mark Customer class as an entity
@Table(name = "customer") // Indicate table name for customer class
public class CustomerRegistration {

    @Id // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy for the primary key.
    
    @Column(name = "custId") // Specifies the column name for customer id.
    private int custId;

    @Column(name = "firstName") // Specifies the column name for firstName.
    @NotBlank(message = "First name cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") // Enforces size constraints.
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name can only contain letters and spaces") // Ensures only alphabets and spaces.
    private String firstName;

    @Column(name = "middleName") // Specifies the column name for middleName.
    @Size(max = 50, message = "Middle name cannot exceed 50 characters") // Optional field with a max size limit.
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Middle name can only contain letters and spaces") // Allows optional but valid input.
    private String middleName;

    @Column(name = "surname") // Specifies the column name for surname.
    @NotBlank(message = "Surname cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters") // Enforces size constraints.
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Surname can only contain letters and spaces") // Ensures only alphabets and spaces.
    private String surname;


    @Column(name = "address") // Specifies the column name for address.
    @NotBlank(message = "Address cannot be blank") // Ensures this field is not blank.
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters") // Enforces size constraints.
    private String address;

    @Column(name = "area") // Specifies the column name for area.
    @NotBlank(message = "Area cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "Area must be between 2 and 50 characters") // Enforces size constraints.
    private String area;

    @Column(name = "atPost") // Specifies the column name for atPost.
    @NotBlank(message = "At Post cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "At Post must be between 2 and 50 characters") // Enforces size constraints.
    private String atPost;

    @Column(name = "taluka") // Specifies the column name for taluka.
    @NotBlank(message = "Taluka cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "Taluka must be between 2 and 50 characters") // Enforces size constraints.
    private String taluka;

    @Column(name = "district") // Specifies the column name for district.
    @NotBlank(message = "District cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "District must be between 2 and 50 characters") // Enforces size constraints.
    private String district;

    @Column(name = "state") // Specifies the column name for state.
    @NotBlank(message = "State cannot be blank") // Ensures this field is not blank.
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters") // Enforces size constraints.
    private String state;

    @Column(name = "pincode") // Specifies the column name for pincode.
    @Pattern(
    	    regexp = "^[0-9]{6}$", // Ensures the pincode is exactly 6 digits.
    	    message = "Pincode must be a valid 6-digit number"
    	) // Enforces a pattern for the pincode to be exactly 6 digits.
    private int pincode;

    @Column(name = "plotNo") // Specifies the column name for plotNo.
    @Min(value = 1, message = "Plot number must be greater than 0") // Ensures the plot number is positive.
    private int plotNo;
    
    @Column(name = "mobileNumber") // Specifies the column name for mobileNumber.
    @Pattern(
        regexp = "^[0-9]{10}$", // Ensures the mobile number is exactly 10 digits.
        message = "Mobile number must be a valid 10-digit number"
    ) // Enforces a pattern for the mobile number to be exactly 10 digits.
    private String mobileNumber;


    @Column(name = "electricityNo") // Specifies the column name for electricityNo.
    @NotBlank(message = "Electricity number cannot be blank") // Ensures this field is not blank.
    @Size(min = 5, max = 20, message = "Electricity number must be between 5 and 20 characters") // Enforces size constraints.
    @Pattern(
    	    regexp = "^[A-Za-z0-9-]+$", // Allows alphanumeric characters and optional hyphens.
    	    message = "Electricity number must be alphanumeric and may include hyphens"
    	) // Ensures the electricity number matches a specific format.
    private String electricityNo;


    @Column(name = "password") // Specifies the column name for password.
    @NotBlank(message = "Password cannot be blank") // Ensures this field is not blank.
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters") // Enforces size constraints.
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Current password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        ) // Enforces a strong password policy for password
    private String password;

    @Column(name = "confirmPassword") // Specifies the column name for confirmPassword.
    @NotBlank(message = "Confirm password cannot be blank") // Ensures this field is not blank.
    @Size(min = 8, max = 20, message = "Confirm password must be between 8 and 20 characters") // Enforces size constraints.
    private String confirmPassword;

    @Column(name = "customer_number", nullable = false, unique = true) // Specifies the column name for customer number.
    @NotBlank(message = "Customer number cannot be blank") // Ensures this field is not blank.
    @Pattern(regexp="[6789]{1}[0=9]{9}", message="Customer number must be a valid 10-digit mobile number starting with 6, 7, 8, or 9")
    @Size(min = 5, max = 20, message = "Customer number must be between 5 and 20 characters") // Enforces size constraints.
    private String customerNumber;

    // Relationships

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentHistory> paymentHistoryList; // One-to-Many relationship with PaymentHistory.

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElectricityUsage> electricityUsageList; // One-to-Many relationship with ElectricityUsage.

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Meter meter; // One-to-One relationship with Meter.

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bill> bills; // One-to-Many relationship with Bill.
}
