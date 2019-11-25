package com.eltae.compareTout.dto.customer;

import com.eltae.compareTout.dto.user.UserInscriptionDto;
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
public class CustomerInscriptionDto extends UserInscriptionDto implements Cloneable {


    private String phoneNum;

    private String sexe;

    private LocalDate birthday;


    public CustomerInscriptionDto clone() throws CloneNotSupportedException {
        return (CustomerInscriptionDto) super.clone();
    }
}
