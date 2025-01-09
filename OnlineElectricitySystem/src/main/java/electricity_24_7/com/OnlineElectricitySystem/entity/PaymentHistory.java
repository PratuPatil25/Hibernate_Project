package electricity_24_7.com.OnlineElectricitySystem.entity;

import java.time.LocalDate;

import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column
import jakarta.validation.constraints.*; // For validation annotations like @Size and @NotBlank
import lombok.Getter; // For generating getters
import lombok.Setter; // For generating setters

@Getter
@Setter
@Entity // Marks PaymentHistory as an entity
@Table(name = "payment_history") // Indicate table name for PaymentHistory class.
public class PaymentHistory {

    @Id // Primary key annotation for id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment strategy for the primary key.
    private Long id;

    @Column(name = "payment_date", nullable = false) // Specifies the column name for firstName.
    @NotNull(message = "Payment date cannot be null") // Ensures the payment date is not null.
    private LocalDate paymentDate;

    @Column(name = "amount_paid", nullable = false) // Specifies the column name for amount paid.
    @NotNull(message = "Amount paid cannot be null") // Ensures the amount paid is not null.
    @Min(value = 0, message = "Amount paid must be a positive value") // Ensures the amount paid is positive.
    private double amountPaid;

//    @Column(name = "payment_method", nullable = false) // Specifies the column name for payment method.
//    @NotBlank(message = "Payment method cannot be blank") // Ensures a payment method is not null.
//    private String paymentMethod;

    @ManyToOne // Many PaymentHistory records can belong to one Customer
    @JoinColumn(name = "customer_number", nullable = false) // Foreign key to link PaymentHistory with Customer
    @NotNull(message = "Customer cannot be null") // Ensures a valid customer is not null.
    private CustomerRegistration customer; // Many-to-One relationship with CustomerRegistration
}
