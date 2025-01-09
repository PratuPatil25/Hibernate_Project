package electricity_24_7.com.OnlineElectricitySystem.entity;

import java.util.List; // Importing List for managing collections.
import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column.
import jakarta.validation.constraints.*; // For validation annotations.
import lombok.Getter; // For Getter annotation.
import lombok.Setter; // For Setter annotation.

@Getter
@Setter
@Entity // Marks Meter class as an entity.
@Table(name = "meter") // Indicate table name for Meter class.
public class Meter {

    @Id // Marks this field as the primary key of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment strategy for the primary key.
    
    @Column(name = "id") // Maps this field to the "id" column in the table.
    private Long id;

    @Column(name = "meter_number", nullable = false, unique = true) // Maps to the "meter_number" column, making it mandatory and unique.
    @NotBlank(message = "Meter number cannot be blank") // Ensures this field is not blank.
    @Size(min = 5, max = 20, message = "Meter number must be between 5 and 20 characters") // Enforces size constraints for the meter number.
    private String meterNumber;

    @ManyToOne
    @JoinColumn(
        name = "customer_number", // Foreign key in meter table.
        referencedColumnName = "customer_number", // Primary key column in customer_registration table.
        nullable = false)
    private CustomerRegistration customer;// Links the meter to a specific customer.
    
    // Defines a One-to-Many relationship with the Bill entity. Cascade operations and orphan removal are enabled.
    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Bill> bills; // Stores a list of bills associated with this meter.

    @Column(name = "installation_date", nullable = false) // Maps to the installation date column.
    @NotNull(message = "Installation date cannot be null") // Ensures this field is not null.
    private String installationDate;

    @Column(name = "status", nullable = false) // Maps to the status column.
    @NotBlank(message = "Status cannot be blank") // Ensures this field is not blank.
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be either 'ACTIVE' or 'INACTIVE'") // Restricts status to specific values.
    private String status;
    
    
}
