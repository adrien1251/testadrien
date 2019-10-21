package com.eltae.compareTout.entities;


import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = Tables.IMAGE)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Image implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="id")
    private Product Product;

    private String url;

    public Image clone() throws CloneNotSupportedException {
        return (Image) super.clone();
    }
}
