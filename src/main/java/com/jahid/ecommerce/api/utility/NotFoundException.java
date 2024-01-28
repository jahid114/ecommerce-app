package com.jahid.ecommerce.api.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException
{

    private Long Id;
    private String className;

    public NotFoundException(Long Id, String className) {
        super(className+ " not found with ID: " + Id);
        this.Id = Id;
        this.className = className;
    }

}
