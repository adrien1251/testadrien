package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = Tables.COMPAREHIST)
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompareHist implements Cloneable{



    @EmbeddedId
    private CompareHistPK id;


    @ManyToOne
    @MapsId("Customer_id")
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer user;


    @ManyToOne
    @MapsId("Product_id")
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public CompareHist clone() throws CloneNotSupportedException {
        return (CompareHist) super.clone();
    }

}