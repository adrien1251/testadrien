package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.ADMIN)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String phoneNum;

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="id")
    private User user;

    public Admin clone() throws CloneNotSupportedException {
        return (Admin) super.clone();
    }

}
