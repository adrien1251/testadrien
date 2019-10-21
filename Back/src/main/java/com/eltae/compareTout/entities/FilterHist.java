package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Tables.FILTERHIST)
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FilterHist implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("Customer_id")
 //   @JoinColumn(name = "CUSTOMER_ID")
    private Customer user;

    private Date date_recherche;

    private String recherche;




    public FilterHist clone() throws CloneNotSupportedException {
        return (FilterHist) super.clone();
    }


}
