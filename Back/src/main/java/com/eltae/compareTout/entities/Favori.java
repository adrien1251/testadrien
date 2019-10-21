package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Tables.FAVORI)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Favori implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("Customer_id")
   // @JoinColumn(name = "CUSTOMER_ID")
    private Customer user;

    private String recherche;

    public Favori clone() throws CloneNotSupportedException {
        return (Favori) super.clone();
    }


}
