package project.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import project.enums.Role;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest{
    @NotNull(message = "first name should not be null")
    private String firstName;
    @NotNull(message = "last name should not be null")
    private String lastName;
    @NotNull(message = "date of birth should not be null")
    private LocalDate dateOfBirth;
    @NotNull(message = "email should not be null")
    private String email;
    @NotNull(message = "password should not be null")
    @Size(min = 4,max = 8,message = "password should be more than 4 and 8 characters")
    private String password;
    @NotNull(message = "role should not be null")
   private   Role role;
   @NotNull(message = "phone number should not be null")
   @Pattern(regexp = "\\+996\\d{9}", message =  "Phone number should start with +996 and consist of 13 characters!")
   private String phoneNumber;
   @NotNull(message = "experience should not be null")
   private Integer experience;


}
