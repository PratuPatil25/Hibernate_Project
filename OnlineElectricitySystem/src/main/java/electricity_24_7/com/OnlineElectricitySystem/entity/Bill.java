package electricity_24_7.com.OnlineElectricitySystem.entity;

import java.time.LocalDate; // Importing the LocalDate class from the java.time package.

import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column
import jakarta.validation.constraints.*; // For validation annotations
import lombok.Getter; // For Getter annotation
import lombok.Setter; // For Setter annotation

@Getter
@Setter
@Entity // Mark Bill class as an entity, meaning it's mapped to a database table.
@Table(name = "bill") // Indicate table name for Bill class
public class Bill {

    @Id // Indicate primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment for primary key
    
    // Column- Adding null & unique constraints as per required for bill id.
    @Column(name = "bill_id", nullable = false) // Specifies the column name for bill id which cannot be null.
    @Positive(message ="Bill id must be a positive number") // Ensures bill id is positive.
    @NotBlank(message = "Bill id cannot be blank") // Validation to ensure that the bill id is not blank.
    private Long billId;

    // Column- Adding null & unique constraints as per required for billingDate.
    @Column(name = "billing_date", nullable = false) // Specifies the column name for billing date which cannot be null.
    @NotBlank(message = "Billing date cannot be blank") // Validation to ensure that the billingDate is not blank.
    private LocalDate billingDate;

    // Column- Adding null & unique constraints as per required for amount field.
    @Column(name = "amount", nullable = false) // Specifies the column name for amount which cannot be null.
    @NotBlank(message = "Amount cannot be blank") // Validation to ensure amount is not blank.
    @Min(value = 0, message = "Amount must be greater than or equal to 0") // Ensures the amount is at least 0.
    private double amount;

    // Column- Adding null & unique constraints as per required for payment status field.
    @Column(name = "payment_status", nullable = false) // Specifies the column details for the payment status.
    @NotBlank(message = "Payment status cannot be blank") // Ensures the payment status is not blank.
    @Size(min = 3, max = 20, message = "Payment status must be between 3 and 20 characters") // Ensures the payment status is between 3 and 20 characters.
    private String paymentStatus;

    // Many-to-One relationship with CustomerRegistration.
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Specifies the foreign key relationship between the Bill and CustomerRegistration.
    @NotBlank(message = "Customer cannot be null") // Validation to ensure that the customer is not null.
    private CustomerRegistration customer;

    // Many-to-One relationship with Meter.
    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false) // Specifies the foreign key relationship between the Bill and Meter.
    @NotBlank(message = "Meter cannot be null") // Validation to ensure that the meter is not null.
    private Meter meter;
}
