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
public class UserDto implements  Cloneable {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String resetToken;

    private LocalDate creationDate;

    public UserDto(Long id, String firstName, String lastName, String email, String password, String resetToken, LocalDate creationDate){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.resetToken = resetToken;
        this.creationDate = creationDate;
    }

    public UserDto(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDto clone() throws CloneNotSupportedException {
        return (UserDto) super.clone();
    }

}