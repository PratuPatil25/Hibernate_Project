package electricity_24_7.com.OnlineElectricitySystem.entity;

import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column
import jakarta.validation.constraints.*; // For validation annotations
import lombok.Getter; // For Getter annotation
import lombok.Setter; // For Setter annotation
import java.time.LocalDate; // Importing the LocalDate class from the java.time package.

@Getter
@Setter
@Entity // Marks ElectricityUsage class as an entity.
@Table(name = "electricity_usage") // Specifies the table name for the ElectricityUsage entity.
public class ElectricityUsage {

    @Id // Marks this field as the primary key.
    @Column(name = "usage_id") // Specifies the column name for the usage ID.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy for the primary key.
    private int usageId;

    @Column(name = "meter_reading") // Specifies the column name for the meter reading.
    @NotBlank(message = "Meter reading cannot be null") // Ensures this field is not null.
    @Min(value = 0, message = "Meter reading must be a positive number") // Enforces a minimum value.
    private double meterReading;

    @Column(name = "usage_date") // Specifies the column name for the usage date.
    @NotBlank(message = "Usage date cannot be null") // Ensures this field is not null.
    private LocalDate usageDate;

    @Column(name = "units_consumed") // Specifies the column name for the units consumed.
    @NotBlank(message = "Units consumed cannot be null") // Ensures this field is not null.
    @Min(value = 0, message = "Units consumed must be a positive number") // Enforces a minimum value.
    private double unitsConsumed;

    @ManyToOne(fetch = FetchType.LAZY) // Specifies a Many-to-One relationship with the Customer entity.
    @JoinColumn(name = "customer_id", nullable = false) // Defines the foreign key column for the Customer entity.
    private CustomerRegistration customer; // The associated Customer entity.
    
    // Overriding toString method
    @Override
    public String toString() {
        return "ElectricityUsage{" + "usageId=" + usageId + ", meterReading=" + meterReading + ", usageDate=" + usageDate + ", unitsConsumed=" + unitsConsumed + '}';
    }
}

