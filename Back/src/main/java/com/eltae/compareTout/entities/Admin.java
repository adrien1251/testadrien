package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@SuperBuilder
@DiscriminatorValue("ADMIN")
@Table(name = Tables.ADMIN)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User implements Cloneable{

    private String phoneNum;

    public Admin clone() throws CloneNotSupportedException {
        return (Admin) super.clone();
    }


}
