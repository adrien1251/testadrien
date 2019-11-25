package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@SuperBuilder
@DiscriminatorValue("CUSTOMER")
@Table(name = Tables.CUSTOMER)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User implements Cloneable{

    private String phoneNum;

    private String sexe;

    private LocalDate birthday;

    public Customer clone() throws CloneNotSupportedException {
        return (Customer) super.clone();
    }
}
