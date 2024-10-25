package com.ecommerce.api.user.request;

import com.ecommerce.api.annotation.ValidEmail;
import com.ecommerce.api.utility.EnumConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Data
public class UserRequest{

    @NotBlank( message = "User name shouldn't be blank" )
    private String name;

    @NotBlank( message = "User must have a Mobile No" )
    @Length( min = 11 )
    private String mobileNo;

    @NotBlank( message = "User should have a password" )
    private String password;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    private String address;
    private Boolean isActive;
    private EnumConstants.UserRole userRole;
}