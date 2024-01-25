package com.jahid.ecommerce.api.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException
{

    private Long userId;
    private String className;

    public NotFoundException(Long userId, String className) {
        super(className+ " not found with ID: " + userId);
        this.userId = userId;
        this.className = className;
    }

}
