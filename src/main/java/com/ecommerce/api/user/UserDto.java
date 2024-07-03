package com.ecommerce.api.user;

import com.ecommerce.api.utility.EnumConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */


@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;

    @NotBlank(message = "User name shouldn't be blank")
    private String name;

    @NotBlank(message = "User must have a Mobile No")
    @Length(min = 11)
    private String mobileNo;

    @NotBlank(message = "User should have a password")
    private String password;

    @Email(message = "User should have a valid email")
    private String email;

    private boolean isActive;
    private String address;
    private EnumConstants.UserRole userRole;
}