package com.ecommerce.api.user.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String mobileNo;
    private Boolean isActive;
}