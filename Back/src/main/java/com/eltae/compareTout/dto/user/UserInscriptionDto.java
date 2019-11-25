package com.eltae.compareTout.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public class UserInscriptionDto implements Cloneable {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public UserInscriptionDto(String firstName, String lastName, String email, String password){
            this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserInscriptionDto clone() throws CloneNotSupportedException {
        return (UserInscriptionDto) super.clone();
    }

}