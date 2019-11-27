package com.eltae.compareTout.dto.user;

import com.eltae.compareTout.dto.admin.AdminDto;
import com.eltae.compareTout.dto.customer.CustomerDto;
import com.eltae.compareTout.dto.supplier.SupplierDto;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SupplierDto.class, name = "supplier"),
        @JsonSubTypes.Type(value = AdminDto.class, name = "admin"),
        @JsonSubTypes.Type(value = CustomerDto.class, name = "customer")
})
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