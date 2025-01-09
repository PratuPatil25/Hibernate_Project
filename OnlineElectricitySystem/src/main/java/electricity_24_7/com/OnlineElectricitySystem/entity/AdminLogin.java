package electricity_24_7.com.OnlineElectricitySystem.entity;

import jakarta.persistence.*; // For JPA annotations like @Entity, @Table, @Column
import jakarta.validation.constraints.*; // For validation annotations like @Size and @NotBlank
//import lombok.Data;
import lombok.Getter; // For Getter annotation
import lombok.Setter; // For Setter annotation

@Getter
@Setter
@Entity // Mark AdminLogin class as an entity
@Table(name = "admin") // Indicate table name for admin class

public class AdminLogin {

    @Id // Indicate primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment for primary key
    
    // Column- adding length, null & unique constraints as per required
    @Column(length = 20, nullable = false) // Specifies the column details for id which cannot be null.
    @NotBlank(message="Admin id is required") // Validation to ensure that the Admin id is not blank.
    @Positive(message ="Id must be a positive number") // Ensures ID is positive
    @Size(min=2, max=20, message="Id must be 2 to 20 character only")
    private int id;
    
    // Column- adding length, null & unique constraints as per required
    @Column(length = 30, nullable = false) // Specifies the column details for username which cannot be null.
    @NotBlank(message="Admin name is required") // Validation to ensure that the Admin name is not blank.
    @Size(min=2, max=20, message="Name must be 2 to 20 character only")
    private String username;
    
    // Adding length, null & unique constraints as per required
    @Column(length = 60, nullable = false) // Specifies the column details for password which cannot be null.
    @NotBlank(message = "Password is required") // Validation to ensure that the Admin password is not blank.
    @Size(min=8, max=12, message="Password must be 8 to 12 character only")
    private String password;
    
}