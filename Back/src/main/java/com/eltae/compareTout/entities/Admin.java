package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
@Table(name = Tables.ADMIN)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phoneNum;

    public Admin clone() throws CloneNotSupportedException {
        return (Admin) super.clone();
    }

}
