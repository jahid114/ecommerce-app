package com.ecommerce.api.user.request;

import com.ecommerce.api.utility.EnumConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Data
public class UserRequest{

    @NotBlank(message = "User name shouldn't be blank")
    private String name;

    @NotBlank(message = "User must have a Mobile No")
    @Length(min = 11)
    private String mobileNo;

    @NotBlank(message = "User should have a password")
    private String password;

    @Email(message = "User should have a valid email")
    private String email;

    private String address;
    private Boolean isActive;
    private EnumConstants.UserRole userRole;
}