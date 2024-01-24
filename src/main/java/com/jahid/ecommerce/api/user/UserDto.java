package com.jahid.ecommerce.api.user;

import com.jahid.ecommerce.api.utility.EnumConstants;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.jahid.ecommerce.api.user.User}
 */


@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;
    private String name;
    private String mobileNo;
    private String password;
    private boolean isActive;
    private String email;
    private String address;
    private EnumConstants.UserRole userRole;
}