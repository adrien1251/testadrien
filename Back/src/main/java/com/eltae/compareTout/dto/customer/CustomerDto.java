package com.eltae.compareTout.dto.customer;

import com.eltae.compareTout.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDto extends UserDto implements Cloneable {


    private String phoneNum;
    private String sexe;
    private LocalDate birthday;


    public CustomerDto clone() throws CloneNotSupportedException {
        return (CustomerDto) super.clone();
    }
}
