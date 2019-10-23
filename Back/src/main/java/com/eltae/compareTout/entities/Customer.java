package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("CUSTOMER")
@Table(name = Tables.CUSTOMER)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phoneNum;

    private String sexe;

    private Date birthday;

    public Customer clone() throws CloneNotSupportedException {
        return (Customer) super.clone();
    }
}
