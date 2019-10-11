package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Tables.CUSTOMER)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phoneNum;

    @OneToOne
    private User user;

    private String sexe;

    private Date birthday;

    public Customer clone() throws CloneNotSupportedException {
        return (Customer) super.clone();
    }
}
